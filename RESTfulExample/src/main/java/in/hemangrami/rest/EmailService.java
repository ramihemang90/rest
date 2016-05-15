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
import in.hemangrami.model.Message;
import in.hemangrami.model.UserToken;

@Path("/emailservice")
public class EmailService extends BaseService implements IWSService {

	private MessageHandler messageHandler = new EmailHandler(getServiceConfig());

	@POST
	@Path("/sendmsg")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response sendMessage(UserToken message) {

		// Authenticating and Authorizing request
		if (authorizeUser(message.getUsertoken()) != 200) {
			return Response.status(403).entity("Invalid user session. Please login to use service.").build();
		}
		// Do logic
		Message orgMsg = DBUtils.prepareOrgEmailMessage(false, getUserId());
		if (orgMsg != null) {
			List<String> mobileNumbers = DBUtils.getAssociatedCustomerEmails(getUserId());

			messageHandler.deliverMessage(orgMsg, mobileNumbers, new Properties());
		}
		String result = "{\"response\":0,\"message\":\"message  sent successfully.\"}";
		return Response.status(201).entity(result).build();
	}

	@POST
	@Path("/cancelmsg")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response cancelMessage(UserToken message) {

		// Authenticating and Authorizing request
		if (authorizeUser(message.getUsertoken()) != 200) {
			return Response.status(403).entity("Invalid user session. Please login to use service.").build();
		}
		// Do logic

		Message orgMsg = DBUtils.prepareOrgEmailMessage(true, getUserId());
		if (orgMsg != null) {
			List<String> mobileNumbers = DBUtils.getAssociatedCustomerEmails(getUserId());

			messageHandler.deliverMessage(orgMsg, mobileNumbers, new Properties());
		}
		String result = "{\"response\":0,\"message\":\"message  sent successfully.\"}";
		return Response.status(201).entity(result).build();
	}

}
