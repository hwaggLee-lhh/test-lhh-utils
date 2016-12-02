package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class UtilsSpringContext {
    private static final Logger log = LoggerFactory.getLogger(UtilsSpringContext.class);

	private static ApplicationContext applicationContext; // Spring应用上下文环境
	
	private static final UtilsSpringContext instance = new UtilsSpringContext();
	private UtilsSpringContext(){
	}
	public static UtilsSpringContext getInstance(){
		return instance;
	}

	public static void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		log.info("init applicationContext success....");
		UtilsSpringContext.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

}
