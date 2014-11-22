package org.cv.test;

import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.util.PropertiesUtil;
import org.cv.model.AppInfo;
import org.cv.model.ConfigInfo;
import org.cv.model.IPBean;
import org.cv.services.AppInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml" })
public class SpringTest {
	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	public ConfigInfo configInfo;

	Factory factory = new ConcreteFactory();// 工厂
	PropertiesUtil propertiesUtil = null;
	
	@Test
	public void testDao() {
		AppInfo appInfo = appInfoService.selectByPrimaryKey(Long.parseLong("27706"));
		System.out.println(appInfo.getAppName());
	}

	public void testGetIp() {
		String ipConfigPath = configInfo.getIp_Properties_Path();
		propertiesUtil = factory.getIpProperties(ipConfigPath);
		IPBean ipBean = factory.getIp(configInfo.getFile_ip_path());
		propertiesUtil.setValue("ip", ipBean.getAddress());
		propertiesUtil.setValue("post", ipBean.getHost());
		propertiesUtil.saveFile(ipConfigPath, "Lambda");

		System.out.println(configInfo.getIp_Properties_Path());
		System.out.println(ipBean.getAddress());
		System.out.println(ipBean.getHost());
	}
}
