package com.sitech.sessionshare.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * <p>Title: 		SpringBean 工厂类</p>
 * <p>Description: 	</p>
 * <p>Copyright: 	Copyright (c) 2012</p>
 * <p>Company: 		SI-TECH </p>
 * @author 			tanyanga
 * @version 		1.0
 */
public class SpringBeanFactory {

	private static ApplicationContext context;

	/**
	 * 在应用程序启动时配置spring框架
	 *
	 * @param filePath
	 */
	public static void init() {

		if (context == null) {
			context = new ClassPathXmlApplicationContext("spring/application-global.xml");
		}
	}

	public static ApplicationContext getContext() {
		return context;
	}

	/**
	 * 方法用于获取业务对象。
	 *
	 * @param beanName
	 * @return
	 */
	public static Object getBusinessOjbect(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * 在应用程序关闭时，清空spring框架配置信息。
	 */
	public static void clear() {
		if (context != null) {
			context = null;
		}
	}
}