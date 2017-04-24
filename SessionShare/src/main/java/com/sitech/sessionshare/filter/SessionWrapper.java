package com.sitech.sessionshare.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.sitech.sessionshare.session.SharedHttpSession;

/**
 * <p>Title: 	封装HttpServletRequest，重写getSession方法	</p>
 * <p>Description: 	　1. 使用一个HttpServletRequestWrapper的实现类，重写getSession()方法，然后使用filter，来过滤每个请求，使request变为requestWrapper。</p>
 * <p>Copyright: 	Copyright (c) 2017</p>
 * <p>Company: 		SI-TECH </p>
 * @date 2017-4-12 下午4:11:01 
 * @author 		gt
 * @version 		1.0
 */
public class SessionWrapper extends HttpServletRequestWrapper{

	  private HttpServletResponse response;
	  private SharedHttpSession httpSession;
	  private SessionSharedManager sessionManager;

	  public SessionWrapper(HttpServletRequest request, HttpServletResponse response, SessionSharedManager sessionManager)
	  {
		   super(request);
		
		   request.getSession();
		   
		   this.response = response;
		
		   this.sessionManager = sessionManager;
		
	  }

	  
	  /**
	 * <p>Description: 	重写获取session方法</p>
	 */
	public HttpSession getSession()
	  {
		   //如果这次请求已经获取过session了，那么该请求其他地方直接返回该session
		   if ((this.httpSession != null) ) {
			   return this.httpSession;
		   }
		   
		   /**如果没获取过session，则走cookie获取sessionid，然后从redis中获取session，如果不存在则创建个新的返回 */
		   String sessionId = getSessionIdFromCookie(this);
			
		   //如果存在sessionid，通过sessionid从redis获取session,	否则创建个新的
		   if (StringUtils.isNotEmpty(sessionId)) {
			    httpSession= sessionManager.getSessionFromRedis(sessionId);
		   }
		   
		   
		   if (httpSession==null) {
			   httpSession=  sessionManager.createEmptySession(this, response);
		   }
		   return httpSession;
	  }

	  
	  /**
	 * <p>Description: 	把session的属性值修改回填给redis</p>
	 * @param servletRequest
	 * @param response
	 */
	public void completed(HttpServletRequest servletRequest, HttpServletResponse response)
      {

			//没操作过
	        if (httpSession==null || !httpSession.isDirty()) {
	        	return;
	        }
	
	
	        sessionManager.saveSession(httpSession);
      }
		  
	 /**
	 * <p>Description: 	
	 * 从cookie获取SessionID
	 * <li>
	 * <li>
	 * </p>
	 * @param request
	 * @return
	 */
	 private String getSessionIdFromCookie(HttpServletRequestWrapper request)
	 {
		    Cookie[] cookies = request.getCookies();
	
		    if ((cookies == null) || (cookies.length == 0)) {
		      return null;
		    }
		    for (Cookie cookie : cookies) {
		    	if (sessionManager.getSession_id_cookie_name().equals(cookie.getName())) {
		    		return cookie.getValue();
		    	}
		    }
		    return null;
	  }
}
