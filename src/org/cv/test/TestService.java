package org.cv.test;

import org.cv.core.spring.SpringBaseService;
import org.cv.core.spring.SpringUtils;
import org.cv.model.AppInfo;
import org.cv.services.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class TestService extends SpringBaseService{
	private AppInfoService appInfoService;
	public TestService() {
		this.appInfoService = SpringUtils.getBean(AppInfoService.class);
	}
	public void test() {
		AppInfo appInfo = appInfoService.selectByPrimaryKey(Long.parseLong("27706"));
		System.out.println(appInfo.getAppName());
	}
}
