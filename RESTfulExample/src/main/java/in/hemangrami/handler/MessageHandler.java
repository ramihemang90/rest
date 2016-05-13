package in.hemangrami.handler;

import java.util.List;
import java.util.Properties;

import in.hemangrami.model.Message;

public interface MessageHandler {

	public void deliverMessage(Message message,List<String> receipentList,Properties messageProperties);
	
}
