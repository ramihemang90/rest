package in.hemangrami.rest;

import javax.ws.rs.core.Response;

import in.hemangrami.model.UserToken;

public interface IWSService {

	
	public Response sendMessage(UserToken usertoken);
	
	public Response cancelMessage(UserToken usertoken);
	
}
