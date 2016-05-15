package in.hemangrami.handler;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import in.hemangrami.model.Message;

public class EmailHandler implements MessageHandler {

	private String from = "someuser";
	private String username = "someser";
	private String password = "<password>";
	private Properties props = new Properties();

	public EmailHandler(Properties serviceconfig) {
		// TODO Auto-generated constructor stub
// Via TLS
		/*props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");*/
		/*Via SSL**/
		
		this.from = serviceconfig.getProperty("email.from");
		this.username  = serviceconfig.getProperty("email.username");
		this.password  = serviceconfig.getProperty("email.password");
		StringBuilder logBuilder =new StringBuilder();
		logBuilder.append("from:"+this.from+"\n").append("username:"+this.username);
		System.out.println(logBuilder.toString());
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	@Override
	public void deliverMessage(Message message, List<String> receipentList, Properties messageProperties) {

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			for (String to : receipentList) {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to));
				msg.setSubject(message.getSubject());
				msg.setText(message.getMessage());
				Transport.send(msg);
				System.out.println("Message sent to : "+ to);
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
