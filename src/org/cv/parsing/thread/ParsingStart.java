/*package org.cv.parsing.thread;

import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.spring.SpringUtils;
import org.cv.model.ConfigInfo;

public class ParsingStart implements Runnable{
	Factory factory = new ConcreteFactory();
	ConfigInfo configInfo = (ConfigInfo) SpringUtils.getBean("configInfo");
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String date = factory.getCurrentDate();
		new IpadReadThread(configInfo.getHtml_root_path()+"ipad/list/" + date);
		new IphoneReadThread(configInfo.getHtml_root_path()+"iphone/list/" + date);
		new AndroidReadThread(configInfo.getHtml_root_path()+"android/list/" + date);
	}
}
*/