package in.hemangrami.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import in.hemangrami.model.User;

@Path("/user")
public class UserService extends BaseService {

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
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout(String usertoken) {

		if (usertoken != null) {

			// authentication logic
			doLogout();
			return Response.status(200).header("content-type", "application/json")
					.entity("{\"message\":\"Successfully logged out.\"}").build();
			
		} else {
			return Response.status(404).entity("Invalid session.").build();
		}
	}

}
