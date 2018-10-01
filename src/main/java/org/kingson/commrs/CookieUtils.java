/**
 * 
 */
package org.kingson.commrs;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**alt+shift+j
 * @author kingson
 * 2018年7月30日
   org.kingson.Ims.commrs
   Imsn
   @version 1.0
 */
public class CookieUtils {

	/**
	 * @param loginCookieName
	 * @param i
	 * @param userId
	 * @param passWord
	 * @param request
	 * @param response
	 */
	public static void addCookie(String cookieName, int age, String userId, String passWord,
			HttpServletRequest request, HttpServletResponse response) {
		//根据cookie名字获取cookie信息
	Cookie cookie	=getCookieByName(cookieName,request);
	if(cookie ==null) {
		
		cookie =new Cookie(cookieName, userId+"#"+passWord);
		//设置cookei的有效时间
		
		cookie.setMaxAge(age);
		
		cookie.setPath(request.getContextPath());
		
		response.addCookie(cookie);
		
	}
	
	}

	/**
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public static Cookie getCookieByName(String cookieName, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		Cookie[] cookies =request.getCookies();
		if(cookies !=null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					return cookies[i];
				}
			}
		}
		return null;
	}
	public static void removeCookies(String cookieName,HttpServletRequest request,HttpServletResponse response)
	{
		Cookie cookie =getCookieByName(cookieName, request);
		
		if(cookie !=null) {
			
			
			cookie.setMaxAge(0);
			
			cookie.setPath(request.getContextPath());
			
			//将cookie响应到客户端
			response.addCookie(cookie);
		}
		
		
	}	
	

}
