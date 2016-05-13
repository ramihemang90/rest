package in.hemangrami.rest;

import javax.ws.rs.core.Response;

import in.hemangrami.model.Message;

public interface IWSService {

	
	public Response sendMessage(Message message);
	
	public Response cancelMessage(Message message);
	
}
