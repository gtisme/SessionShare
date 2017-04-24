package com.sitech.sessionshare.session;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;


/**
 * <p>Title: 	自定义共享session类	</p>
 * <p>Description: 	</p>
 * <p>Copyright: 	Copyright (c) 2017</p>
 * <p>Company: 		SI-TECH </p>
 * @date 2017-4-12 下午3:17:19 
 * @author 		gt
 * @version 		1.0
 */
public class SharedHttpSession  implements  HttpSession,Serializable{
	

	private static final long serialVersionUID = 1L;

	//属性集合（key：字段 value：值）
	private Map<String, Object> data = new HashMap<String, Object>();
	
	//sessionid
	protected String id;
	
	//本次读取是否操作过
	protected boolean isDirty; 
	
	  
	/**
	 * <p>Description:获取属性值 	</p>
	 * @param key
	 * @return
	 */
	public Object getAttribute(String key)
	{
		return this.data.get(key);
	}
	
	/**
	 * <p>Description:设置属性值 	</p>
	 * @param s
	 * @param o
	 */
	public void setAttribute(String key, Object value)
	{
		this.isDirty = true;
	    this.data.put(key, value);
	}
	
	
	/**
	 * <p>Description:删除属性值 	</p>
	 * @param s
	 */
	public void removeAttribute(String key)
	{
	   this.isDirty = true;
	   this.data.remove(key);
	}
	

	/**
	 * <p>Description:获取sessionID	</p>
	 * @param s
	 */
	public String getId() {
		// TODO Auto-generated method stub
	    return this.id;
	}


	public void setId(String id) {
		this.id = id;
	}

	
	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	@Override
	@Deprecated
	public long getCreationTime() {
		throw new RuntimeException("此方法未实现");
	}

	@Override
	@Deprecated
	public ServletContext getServletContext() {
		throw new RuntimeException("此方法未实现");
	}

	@Override
	@Deprecated
	public void setMaxInactiveInterval(int interval) {
		throw new RuntimeException("此方法未实现");
	}

	@Override
	@Deprecated
	public int getMaxInactiveInterval() {
		throw new RuntimeException("此方法未实现");
	}

	@Override
	@Deprecated
	public HttpSessionContext getSessionContext() {
		throw new RuntimeException("此方法未实现");
	}

	@Override
	@Deprecated
	public Object getValue(String name) {
		throw new RuntimeException("此方法未实现");
	}

	@Override
	@Deprecated
	public Enumeration<String> getAttributeNames() {
		throw new RuntimeException("此方法未实现");
	}

	@Override
	@Deprecated
	public String[] getValueNames() {
		throw new RuntimeException("此方法未实现");
	}

	@Override
	@Deprecated
	public void putValue(String name, Object value) {
		throw new RuntimeException("此方法未实现");
		
	}

	@Override
	@Deprecated
	public void removeValue(String name) {
		throw new RuntimeException("此方法未实现");
		
	}

	@Override
	@Deprecated
	public void invalidate() {
		throw new RuntimeException("此方法未实现");
		
	}

	@Override
	@Deprecated
	public boolean isNew() {
		throw new RuntimeException("此方法未实现");
	}

	@Override
	@Deprecated
	public long getLastAccessedTime() {
		throw new RuntimeException("此方法未实现");
	}




}
