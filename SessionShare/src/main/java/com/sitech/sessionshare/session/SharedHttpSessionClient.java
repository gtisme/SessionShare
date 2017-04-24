package com.sitech.sessionshare.session;


import java.io.UnsupportedEncodingException;

import com.sitech.sessionshare.redis3.JedisClusterClient;
import com.sitech.sessionshare.redis3.SerializeUtils;

/**
 * <p>Title:  对SharedHttpSession操作类</p>
 * <p>Description: 	对SharedHttpSession操作类</p>
 * <p>Copyright: 	Copyright (c) 2017</p>
 * <p>Company: 		SI-TECH </p>
 * @date 2017-4-12 下午2:48:53 
 * @author 		gt
 * @version 		1.0
 */
public class SharedHttpSessionClient {
	
	private JedisClusterClient jedisCluster;

	
	private String encode;
	
	
	/**
	 * <p>Description: 	获取session</p>
	 * @param sessionid
	 * @param session
	 * @throws Exception 
	 */
	public SharedHttpSession getSession(String sessionId) throws Exception {
	    if (sessionId == null) {
	      return null;
	    }
	    
	    //DM_SHARED_JSID_1FB5A8C4D8144EC1864ED8D6F849B59B
	    String value=jedisCluster.get(sessionId);

	        
	    SharedHttpSession s= (SharedHttpSession)SerializeUtils.deserialize(value.getBytes(this.encode));
		
	    return s;
	}
	
	/**
	 * <p>Description: 	保存session</p>
	 * @param sessionid
	 * @param session
	 */
	public void saveSession(String sessionid, SharedHttpSession session) {
		byte[] value = SerializeUtils.serialize(session);
		try {
			jedisCluster.set(sessionid, new String(value, this.encode));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete(String sessionid) {
		jedisCluster.del(sessionid);
	}
	  
	  
	public JedisClusterClient getJedisCluster() {
		return jedisCluster;
	}
	public void setJedisCluster(JedisClusterClient jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	
	
}
