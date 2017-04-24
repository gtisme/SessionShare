package com.sitech.sessionshare.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitech.sessionshare.redis3.JedisClusterClient;

/**
 * <p>Title: 	过滤器	</p>
 * <p>Description: 	</p>
 * <p>Copyright: 	Copyright (c) 2017</p>
 * <p>Company: 		SI-TECH </p>
 * @date 2017-4-12 下午4:11:06 
 * @author 		gt
 * @version 		1.0
 */
public class SessionSharedFilter implements Filter{
	//过滤不需要session共享的请求后缀
	public static final String[] IGNORE_SUFFIX = { ".png", ".jpg",  ".jpeg", ".gif", ".css", ".js", ".html", ".htm" };
	
	//这里是为了给SessionWrapper引入spring的bean
	private SessionSharedManager sessionManager;



	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException
	{
	    HttpServletRequest request = (HttpServletRequest)servletRequest;
	
	    //过滤不需要session共享的请求
	    if (!shouldFilter(request)) {
	      filterChain.doFilter(servletRequest, servletResponse);
	      return;
	    }
	    
	    HttpServletResponse response = (HttpServletResponse)servletResponse;
	    
	    //SessionWrapper类封装HttpServletRequest，重写getSession方法
	    SessionWrapper requestWrapper = new SessionWrapper(request, response, this.sessionManager);
	    
	    try {
	    	filterChain.doFilter(requestWrapper, servletResponse);
	    }
	    finally
	    {
	    	//结束最后，SharedHttpSession1：里面属性的修改，要回填到redis 2：过期会把redis中的删除
	    	requestWrapper.completed(request, response);
	    }
	 }

	  /**
	 * <p>Description: 过滤不需要session共享的请求，如图片等	</p>
	 * @param request
	 * @return
	 */
	private boolean shouldFilter(HttpServletRequest request) {
	    String uri = request.getRequestURI().toLowerCase();
	    for (String suffix : IGNORE_SUFFIX) {
	      if (uri.endsWith(suffix)) {
	        return false;
	      }
	      if (uri.startsWith("/birt/")) {
	        return false;
	      }
	    }
	    return true;
	  }

	  public void destroy()
	  {
	  }
	  
	  public void init(FilterConfig filterConfig)throws ServletException
	  {
			
	  }
	  
	  public void setSessionManager(SessionSharedManager sessionManager)
	  {
	    this.sessionManager = sessionManager;
	  }
}
