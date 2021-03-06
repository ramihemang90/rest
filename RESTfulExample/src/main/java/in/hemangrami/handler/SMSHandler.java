package in.hemangrami.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;

/*
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
*/
import in.hemangrami.model.Message;

public class SMSHandler implements MessageHandler {

	public static final String ACCOUNT_SID = "ACb689fa1f490e2a9dbd07dc8b82a69d18";
	public static final String AUTH_TOKEN = "f908bc7fe5b2375b555c54d9f579dc88";

	private String from ="92992929";
	public SMSHandler(Properties serviceConfig) {
		from =serviceConfig.getProperty("sms.from");
	}

	@Override
	public void deliverMessage(Message message, List<String> receipentList, Properties messageProperties) {

		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		for (String mobile : receipentList) {
			// Build a filter for the MessageList
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Body", message.getMessage()));
			params.add(new BasicNameValuePair("To", mobile));
			params.add(new BasicNameValuePair("From", from));

			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			try {
				com.twilio.sdk.resource.instance.Message msg = messageFactory.create(params);
			} catch (TwilioRestException e) {
				System.out.println(e.getErrorMessage());
			}
		}
	}

}
