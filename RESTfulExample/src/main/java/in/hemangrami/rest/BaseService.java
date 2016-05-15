package in.hemangrami.rest;

import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

public abstract class BaseService {

	private static Properties serivceConfig = new Properties();
	
	static{
		loadConfigurations();
	}
	
	@Context
	private HttpServletRequest request;
	
	public BaseService() {
		// TODO Auto-generated constructor stub
	}
	
	public void registerSession(String username,int id){
		HttpSession session=request.getSession(true);
		session.setAttribute("USER_NAME", username);
		session.setAttribute("USER_ID", Integer.valueOf(id));
		String token = UUID.randomUUID().toString();
		session.setAttribute("USER_TOKEN",token );
	}
	
	public int authorizeUser(String usertoken){
		if(usertoken==null || (!usertoken.equals(getUserToken()))){
			return 403;
		}else{
			return 200;
		}
	}
	
	
	
	private static void loadConfigurations(){
		try{
			
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
			if(is!=null){
				Properties prop= new Properties();
				prop.load(is);
				serivceConfig= prop;
			}
			
		}catch(Exception ex){
			System.out.println("Exception occured while loading configuration properties . Possible reason  : "+ ex.getMessage());
		}
	}
	
	public String getUserToken(){
		return (String) request.getSession().getAttribute("USER_TOKEN");
	}
	public int getUserId(){
		return (Integer)(request.getSession().getAttribute("USER_ID"));
	}
	
	public String getUsername(){
		return (String) request.getSession().getAttribute("USER_NAME");
	}
	
	public void doLogout(){
		request.getSession().invalidate();
	}
	
	
	public String getProperty(String key){
		return serivceConfig.getProperty(key);
	}
	
	public Properties getServiceConfig(){
		return serivceConfig;
	}
	
}
