package org.cv.test;

import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.spring.SpringUtils;
import org.cv.model.ConfigInfo;
import org.cv.parsing.thread.AndroidReadThread;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	private static ApplicationContext applicationContext = null;
	private static SpringUtils springUtils = null;
	private static ConfigInfo configInfo = null;
	private static Factory factory = new ConcreteFactory();

	public Test() {
		configInfo = SpringUtils.getBean(ConfigInfo.class);
	}
	
	public static void main(String[] args) throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"classpath:spring.xml", "classpath:spring-mybatis.xml" });
		springUtils = new SpringUtils();
		/*springUtils.setApplicationContext(applicationContext);
		new Test().begin();*/
		springUtils.destroy();
	}

	public void begin() {
		String date = factory.getCurrentDate();
		new Thread(new AndroidReadThread(configInfo.getHtml_root_path()
				+ "android/list/" + date)).start();
	}
}
