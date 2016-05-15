package in.hemangrami.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import in.hemangrami.model.Message;
import in.hemangrami.model.User;

public class DBUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDBConnection() {
		Connection connection =null;
		try {
			connection=DBUtils.getConnection();
		} catch (Exception e) {
			Assert.fail("Unexpected exception occured.");	
		}finally{
			try{
			if(connection!=null && !connection.isClosed()){
				connection.close();
			}
			}catch(SQLException ex){
				Assert.fail("Unexpected exception occured.");
			}
		}
	}
	
	@Test
	public void testAuthenticateUser(){
		try{
			User user= new User();
			user.setUsername("admin");
			user.setPassword("admin");
			boolean isvalid=DBUtils.authenticateUser(user);
			Assert.assertEquals("User must be authenticated.",true, isvalid);
			Assert.assertNotNull("User id should not be null", user.getId());
			
		}catch(Exception ex){
			Assert.fail("Unexpected exception occured."+ ex.getMessage());
		}
	}
	
	@Test
	public void testEmailList(){
		try{
			User user= new User();
			user.setUsername("admin");
			user.setPassword("admin");
			boolean isvalid=DBUtils.authenticateUser(user);
			Assert.assertEquals("User must be authenticated.",true, isvalid);
			Assert.assertNotNull("User id should not be null", user.getId());
			
			List<String> emails= DBUtils.getAssociatedCustomerEmails(user.getId());
			Assert.assertTrue("Email list should be > 0.",emails.size()>0);
			
		}catch(Exception ex){
			Assert.fail("Unexpected exception occured."+ ex.getMessage());
		}
	}

	@Test
	public void testMobileList(){
		try{
			User user= new User();
			user.setUsername("admin");
			user.setPassword("admin");
			boolean isvalid=DBUtils.authenticateUser(user);
			Assert.assertEquals("User must be authenticated.",true, isvalid);
			Assert.assertNotNull("User id should not be null", user.getId());
			
			List<String> emails= DBUtils.getAssociatedCustomerMobiles(user.getId());
			Assert.assertTrue("Mobile list should be > 0.",emails.size()>0);
			
		}catch(Exception ex){
			Assert.fail("Unexpected exception occured."+ ex.getMessage());
		}
	}
	
	
	@Test
	public void testOrgTemplatePrepation(){
		try{
			User user= new User();
			user.setUsername("admin");
			user.setPassword("admin");
			boolean isvalid=DBUtils.authenticateUser(user);
			Assert.assertEquals("User must be authenticated.",true, isvalid);
			Assert.assertNotNull("User id should not be null", user.getId());
			
			Message message = DBUtils.prepareOrgEmailMessage(false,user.getId());
			Assert.assertNotNull("Message should not be null", message);
			Assert.assertNotNull("Subject should not be null.", message.getSubject());
			Assert.assertNotNull("Message content should not be null.", message.getMessage());
			
			message = DBUtils.prepareOrgEmailMessage(true,user.getId());
			
			Assert.assertNotNull("Message should not be null", message);
			Assert.assertNotNull("Subject should not be null.", message.getSubject());
			Assert.assertNotNull("Message content should not be null.", message.getMessage());
	
			message = DBUtils.prepareOrgSMSMessage(false,user.getId());
			
			Assert.assertNotNull("Message should not be null", message);
			Assert.assertNull("Subject should be null.", message.getSubject());
			Assert.assertNotNull("Message content should not be null.", message.getMessage());
	
			message = DBUtils.prepareOrgSMSMessage(true,user.getId());
			
			Assert.assertNotNull("Message should not be null", message);
			Assert.assertNull("Subject should be null.", message.getSubject());
			Assert.assertNotNull("Message content should not be null.", message.getMessage());
	
			
		}catch(Exception ex){
			Assert.fail("Unexpected exception occured."+ ex.getMessage());
		}
	
	}
}
