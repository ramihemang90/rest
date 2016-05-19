package in.hemangrami.rest;

import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import in.hemangrami.db.DBUtils;
import in.hemangrami.handler.EmailHandler;
import in.hemangrami.handler.MessageHandler;
import in.hemangrami.handler.SMSHandler;
import in.hemangrami.model.Message;
import in.hemangrami.model.User;
import in.hemangrami.model.UserToken;

@Path("/service/notify")
public class UserService extends BaseService {

	private MessageHandler emailHandler = new EmailHandler(getServiceConfig());
	private MessageHandler smsHandler = new SMSHandler(getServiceConfig());
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(User user) {
		if (user != null && user.getUsername() != null && user.getPassword() != null) {

			// authentication logic
			registerSession(user.getUsername(), 1);

			return Response.status(200).header("content-type", "application/json")
					.entity("{\"usertoken\":\"" + getUserToken() + "\"}").build();
		} else {
			return Response.status(404).entity("Username/Password invalid.").build();
		}

	}
	

	@POST
	@Path("/sendmsg")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendMessage(UserToken message) {

		// Authenticating and Authorizing request
		if (authorizeUser(message.getUsertoken()) != 200) {
			return Response.status(403).header("content-type", "application/json").entity("{\"message\":\"Invalid user session. Please login to use service.\"}").build();
		}
		// Do logic
		Message orgMsg = DBUtils.prepareOrgEmailMessage(false, getUserId());
		if (orgMsg != null) {
			List<String> mobileNumbers = DBUtils.getAssociatedCustomerEmails(getUserId());
			emailHandler.deliverMessage(orgMsg, mobileNumbers, new Properties());
		}
		
		Message smsMsg = DBUtils.prepareOrgSMSMessage(false, getUserId());
		if(smsMsg!=null){
			List<String> mobileNumbers =DBUtils.getAssociatedCustomerMobiles(getUserId());
			smsHandler.deliverMessage(smsMsg, mobileNumbers, new Properties());
		}
		
		
		String result = "{\"response\":0,\"message\":\"message  sent successfully.\"}";
		return Response.status(200).header("content-type", "application/json").entity(result).build();
	}
	
	
	@POST
	@Path("/cancelmsg")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancelMessage(UserToken message) {

		// Authenticating and Authorizing request
		if (authorizeUser(message.getUsertoken()) != 200) {
			return Response.status(403).entity("Invalid user session. Please login to use service.").build();
		}
		// Do logic

		Message orgMsg = DBUtils.prepareOrgEmailMessage(true, getUserId());
		if (orgMsg != null) {
			List<String> mobileNumbers = DBUtils.getAssociatedCustomerEmails(getUserId());
			emailHandler.deliverMessage(orgMsg, mobileNumbers, new Properties());
		}
		
		Message smsMsg = DBUtils.prepareOrgSMSMessage(true, getUserId());
		if(smsMsg!=null){
			List<String> mobileNumbers =DBUtils.getAssociatedCustomerMobiles(getUserId());
			smsHandler.deliverMessage(smsMsg, mobileNumbers, new Properties());
		}
		String result = "{\"response\":0,\"message\":\"cancel message  sent successfully.\"}";
		return Response.status(200).header("content-type", "application/json").entity(result).build();
	}
	

	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout(UserToken userToken) {
		if (userToken != null && userToken.getUsertoken()!=null) {
			// authentication logic
			doLogout();
			return Response.status(200).header("content-type", "application/json")
					.entity("{\"message\":\"Successfully logged out.\"}").build();
		} else {
			return Response.status(404).header("content-type", "application/json").entity("{\"message\":\"Invalid session.\"}").build();
		}
	}

}
