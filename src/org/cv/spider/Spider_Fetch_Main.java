/*package org.cv.spider;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.util.MakePic;
import org.cv.core.util.PropertiesUtil;
import org.cv.core.util.SyncManager;
import org.cv.parsing.thread.AndroidReadThread;
import org.cv.parsing.thread.IpadReadThread;
import org.cv.parsing.thread.IphoneReadThread;
import org.cv.spider.thread.AndroidThread;
import org.cv.spider.thread.IpadThread;
import org.cv.spider.thread.IphoneThread;

public class Spider_Fetch_Main {
	List<Runnable> runList = new ArrayList<Runnable>();
	Factory factory = new ConcreteFactory();
	PropertiesUtil propertiesUtil = factory.getAllPerPropertiesUtil();
	Logger logger = Logger.getLogger(Spider_Fetch_Main.class);

	public static void main(String[] args) {
		new Spider_Fetch_Main().spider();
	}
	public  void spider() {
        PropertyConfigurator.configure(propertiesUtil.getValue("LOG4J_PATH"));
		String date = factory.getCurrentDate();
		//下载
		runList.add(new AndroidThread());
		runList.add(new IphoneThread());
		runList.add(new IpadThread());
		new SyncManager(runList).run();
		runList.clear();
		
		//解析
		runList.add(new IpadReadThread(propertiesUtil.getValue("HTML_PATH")+"ipad/list/" + date));
		runList.add(new IphoneReadThread(propertiesUtil.getValue("HTML_PATH")+"iphone/list/" + date));
		runList.add(new AndroidReadThread(propertiesUtil.getValue("HTML_PATH")+"android/list/" + date));
		runList.add(new MakePic()); // 最后执行生成图片
		new SyncManager(runList).run();
		runList.clear();
	}
}
*/