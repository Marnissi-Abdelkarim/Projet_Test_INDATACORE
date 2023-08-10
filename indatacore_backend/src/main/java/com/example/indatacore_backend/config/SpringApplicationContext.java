package com.example.indatacore_backend.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
public class SpringApplicationContext implements ApplicationContextAware {
	static ApplicationContext CONTEXT;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CONTEXT=applicationContext;
	}
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}
}