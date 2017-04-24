package com.sitech.sessionshare.filter;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;

import com.sitech.sessionshare.session.SharedHttpSession;
import com.sitech.sessionshare.session.SharedHttpSessionClient;
import com.sitech.sessionshare.util.Log4JUtil;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Company: SI-TECH
 * </p>
 * 
 * @date 2017-4-12 下午5:13:10
 * @author gt
 * @version 1.0
 */
/**
 * <p>Title: 		</p>
 * <p>Description: 	</p>
 * <p>Copyright: 	Copyright (c) 2017</p>
 * <p>Company: 		SI-TECH </p>
 * @date 2017-4-12 下午6:12:15 
 * @author 		gt
 * @version 		1.0
 */
/**
 * <p>Title: 		</p>
 * <p>Description: 	</p>
 * <p>Copyright: 	Copyright (c) 2017</p>
 * <p>Company: 		SI-TECH </p>
 * @date 2017-4-12 下午6:12:18 
 * @author 		gt
 * @version 		1.0
 */
public class SessionSharedManager {

	private String session_id_cookie_name ;//cookie中保存sessionid的名字
	private String session_id_redis_prefix ; //redis中保存的sessionid前缀 （appcode+SHARED_JSID_）
	
	private SharedHttpSessionClient cacheClient;
	


	  /**
	 * <p>Description: 通过sessionid获取session	</p>
	 * @param sessionId
	 * @return
	 */
	public SharedHttpSession getSessionFromRedis(String sessionId)
	  {
		 SharedHttpSession session =null;
	      try {
	    	  session = this.cacheClient.getSession(sessionId);
	      }catch (Exception e)
	      {
		      Log4JUtil.errorLog("exception loadSession [Id=" + sessionId + "]", e);
		  }
	      
	
	      Log4JUtil.debugLog("SharedHttpSession Load [ID=" + sessionId + ",exist=" + ( session != null) + "]");
	      
	      
	      if(session!=null){
	    	  session.setDirty(false);
	      }
	      return session;
	    
	  }
	
	
	/**
	 * <p>Description: 创建session，没有属性值	</p>
	 * @param request
	 * @param response
	 * @return
	 */
	public SharedHttpSession createEmptySession(SessionWrapper request, HttpServletResponse response)
	  {
	    SharedHttpSession session = new SharedHttpSession();
	    session.setId(createSessionId());
	    session.setDirty(true);
	    
	    Log4JUtil.debugLog("SharedHttpSession Create [ID=" + session.getId() + "]");
	    
	    saveCookie(session, request, response);
	    return session;
	  }

	
		  /**
		 * <p>Description: 保存session到redis	</p>
		 * @param session
		 */
		public void saveSession(SharedHttpSession session)
		{
		    try {
		    	Log4JUtil.debugLog("SharedHttpSession saveSession [ID=" + session.getId() +  ",isDiry=" +  session.isDirty() + "]");
		        
	
		      	this.cacheClient.saveSession(session.getId(), session);
		    }
		    catch (Exception e) {
		    	e.printStackTrace();
		    	throw new RuntimeException(e);
		    }
	   }
	  
	  /**
	 * <p>Description: sessionid保存到cookis 	</p>
	 * @param session
	 * @param request
	 * @param response
	 */
	private void saveCookie(SharedHttpSession session, HttpServletRequestWrapper request, HttpServletResponse response)
	  {

	    Cookie cookie = new Cookie(getSession_id_cookie_name(), null);
	    String cookiePath = request.getContextPath() == "" ? "/" : request .getContextPath();
	    cookie.setPath(cookiePath);
	    
	    cookie.setMaxAge(-1);
	    cookie.setValue(session.getId());
	    response.addCookie(cookie);

	    Log4JUtil.debugLog("SharedHttpSession saveCookie [ID=" + session.getId() + "]");
	  }
	
	private String createSessionId() {
		return session_id_redis_prefix.concat(UUID.randomUUID().toString().replace("-", "") .toUpperCase());
	}


	public String getSession_id_cookie_name() {
		return session_id_cookie_name;
	}

	public void setSession_id_cookie_name(String session_id_cookie_name) {
		this.session_id_cookie_name = session_id_cookie_name;
	}

	public String getSession_id_redis_prefix() {
		return session_id_redis_prefix;
	}

	public void setSession_id_redis_prefix(String session_id_redis_prefix) {
		this.session_id_redis_prefix = session_id_redis_prefix;
	}

	public SharedHttpSessionClient getCacheClient() {
		return cacheClient;
	}

	public void setCacheClient(SharedHttpSessionClient cacheClient) {
		this.cacheClient = cacheClient;
	}



	
}
