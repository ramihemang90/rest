package in.hemangrami.rest;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

public abstract class BaseService {

	
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
}
