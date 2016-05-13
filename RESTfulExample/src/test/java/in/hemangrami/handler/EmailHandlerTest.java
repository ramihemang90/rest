package in.hemangrami.handler;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import in.hemangrami.model.Message;

public class EmailHandlerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		EmailHandler handler= new EmailHandler();
		Message message= new Message();
		message.setMessage("Test message");
		message.setSubject("hello");
		List<String> receipentList = new ArrayList<String>();
		receipentList.add("test@test.com");
		
		try{
		handler.deliverMessage(message, receipentList, null);
		}catch(RuntimeException ex){
			Assert.fail(ex.getMessage());
		}
	}

}
