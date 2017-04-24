package com.sitech.sessionshare;

import org.apache.log4j.Logger;

import com.sitech.sessionshare.redis3.JedisClusterClient;
import com.sitech.sessionshare.util.Log4JUtil;
import com.sitech.sessionshare.util.SpringBeanFactory;

import redis.clients.jedis.JedisCluster;





//参考 http://xyqck163.iteye.com/blog/2211108
//api http://javadox.com/redis.clients/jedis/2.5.2/redis/clients/jedis/JedisCluster.html
public class Main {

	public static void main(String[] args) {
		
//		Log4JUtil.debugLog("11");
		
//	    if ((FileLog.getParent().isDebugEnabled()))
//	    	log.info("SharedHttpSession Create [ID=" + 11 + "]");
	    
	    
		SpringBeanFactory.init();
		
		JedisClusterClient jedisCluster = (JedisClusterClient) SpringBeanFactory.getBusinessOjbect("jedisCluster");
		
		jedisCluster.set("2017413", "haha");
		
		for(int i=0;i<10;i++)
		{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(jedisCluster.get("2017413"));
		}
		
//		System.out.println(jedisCluster.get("sex1"));
//		System.out.println(jedisCluster.get("sex2"));
//		System.out.println(jedisCluster.get("sex3"));
//		System.out.println(jedisCluster.get("sex4"));
//		System.out.println(jedisCluster.get("name"));
//		System.out.println(jedisCluster.get("age"));
	}
	
	
	
}
