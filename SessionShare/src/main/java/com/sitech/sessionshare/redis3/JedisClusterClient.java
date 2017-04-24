package com.sitech.sessionshare.redis3;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * <p>Title: 	Redis操作工厂类	</p>
 * <p>Description: 	</p>
 * <p>Copyright: 	Copyright (c) 2017</p>
 * <p>Company: 		SI-TECH </p>
 * @date 2017-4-12 下午2:06:46 
 * @author 		gt
 * @version 		1.0
 */
public class JedisClusterClient implements InitializingBean {

	private Resource addressConfig; //redis服务器地址
	private JedisCluster jedisCluster; //redis 实例
	private Integer timeout; // redis的连接超时时间
	private Integer expire; // redis的连接超时时间
	private Integer maxRedirections; //redis的节点调转次数（实际可以看做是重试次数）
	private GenericObjectPoolConfig genericObjectPoolConfig; //连接池 
	
	/**
	 * <p>Description: 通过key获取value	</p>
	 * @param key
	 * @return
	 */
	public String get(String key) {
        String value = this.jedisCluster.get(key);
        updateExpire(key);
        
        if (isNull(value)) {
          return null;
        }
        return value;
	}
	

	/**
	 * <p>Description: 设置value值	</p>
	 * @param key
	 * @param value
	 */
	public void set( String key,String value) {
		this.jedisCluster.set(key,value);
		updateExpire(key);
	}
	
	/**
	 * <p>Description: 	删除key </p>
	 * @param key
	 */
	public void del(String key){
		this.jedisCluster.del(key);
	}
	
    //每次操作都要更新redis值的过期时间
	private void updateExpire(String key){
        this.jedisCluster.expire(key, this.expire);
	}
	 
//	/**
//	 * <p>Description: 设置value值，同时加上超时时间	</p>
//	 * @param key
//	 * @param value
//	 * @param expire 超时时间
//	 */
//	public void set( String key,String value, int expire) {
//		 this.jedisCluster.set(key,value);
//		 if(expire!=0)
//			 this.jedisCluster.expire(key, expire);
//	}
	 


	//解析配置获取redis连接地址
	private String addressKeyPrefix="address" ;
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			Properties prop = new Properties();
			prop.load(this.addressConfig.getInputStream());

			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			for (Object key : prop.keySet()) {

				if (!((String) key).startsWith(addressKeyPrefix)) {
					continue;
				}

				String val = (String) prop.get(key);

				boolean isIpPort = p.matcher(val).matches();

				if (!isIpPort) {
					throw new IllegalArgumentException("ip 或 port 不合法");
				}
				String[] ipAndPort = val.split(":");
				
				//System.out.println(ipAndPort[0]);
				
				HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				haps.add(hap);
			}

			return haps;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}
	
	private static boolean isNull(String redisValue)
	{
	   return ("nil".equals(redisValue)) || (redisValue == null);
	}
	  
	//aop切面
	@Override
	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		
		jedisCluster = new JedisCluster(haps, timeout, maxRedirections,genericObjectPoolConfig);
		
	}
	
	public void setAddressConfig(Resource addressConfig) {
		this.addressConfig = addressConfig;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}

	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}

	public void setExpire(Integer expire) {
		this.expire = expire;
	}

}
