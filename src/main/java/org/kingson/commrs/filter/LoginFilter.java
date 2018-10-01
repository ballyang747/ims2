/**
 * 
 */
package org.kingson.commrs.filter;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kingson.Ims.identity.domain.User;
import org.kingson.Ims.identity.service.IndentityService;
import org.kingson.commrs.ConstantUtil;
import org.kingson.commrs.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.commrs.filter
   Imsn
   @version 1.0

  @email 1606656167@qq.com
  @tel 15768607477
   
 */
public class LoginFilter extends HandlerInterceptorAdapter {
    /**
     * 该方法是监听网页是否登录
     * 1.用户正常登录(session中有信息)可以通过
     * 2.cookie中存在用户信息可以通过
     * 
     * */
	@Autowired
	private IndentityService indentityService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler,WebRequest request2)
			throws Exception {
		// TODO Auto-generated method stub
		
	User user =	(User) request.getSession().getAttribute(ConstantUtil.SESSION_USER);
	
	if(user ==null) {
		       //如果session中没有用户信息，继续判断cookie中是否有用户信息，如果cookie中有通过没有返回flase
	Cookie cookie =	CookieUtils.getCookieByName(ConstantUtil.LOGIN_COOKIE_NAME,request);
		 if(cookie ==null) {
			 request.setAttribute("message", "您输入的信息已经失效，请重新登录");
			 
			 request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		
		 return false;
		 }else {
			//从cookie中获取用户的账号以及密码
		String value=	cookie.getValue();
		
	 String [] userInfos=	value.split("#");
	 
	     User u =    indentityService.getUserById(userInfos[0]);
	     
	     if(u!=null &&u.getPassWord().equals(userInfos[1])) {
	    	 
	    	 request.getSession().setAttribute(ConstantUtil.SESSION_USER, u);
	    	 
	    	 //判断session中是否还存在用户权限信息，如果不存在则重新查询数据库存放在session中
	    	List<String> userPageOperas= (List<String>) request.getSession().getAttribute("session_option");
	    	
	          if(userPageOperas==null ||userPageOperas.size()==0) {
	        	  userPageOperas=indentityService.findLeftOptions(request2);
	        	  request2.setAttribute("session_option", userPageOperas, WebRequest.SCOPE_SESSION);
	        	  
	          }	
	    	 return true;
	    	 
	     }else {
			request.setAttribute("message", "验证信息已经失效，请重新登录!");
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return false;
	     }
		 }
		}else {
		      return true ;	
		}
	}
		
		
}
		
	
 

