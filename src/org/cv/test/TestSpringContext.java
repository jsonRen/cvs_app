package org.cv.test;

import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.spring.SpringUtils;
import org.cv.model.ConfigInfo;
import org.cv.services.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestSpringContext{
	private static ApplicationContext applicationContext = null;
	private static SpringUtils springUtils = null;
	private static ConfigInfo configInfo = null;
	private static Factory factory = new ConcreteFactory();
	@Autowired
	private AppInfoService appInfoService;
	public static void main(String[] args) {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"classpath:spring.xml", "classpath:spring-mybatis.xml" });
		springUtils = new SpringUtils();
		springUtils.setApplicationContext(applicationContext);
		new TestService().test();
	}
}
