/*package org.cv.parsing;

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

*//**
 * @classDescription: 解析硬盘中的html文件
 * @author:Lambda
 *//*
public class Fetch_Main {
	static Factory factory = new ConcreteFactory();
	List<Runnable> runList = new ArrayList<Runnable>();
	static PropertiesUtil propertiesUtil = factory.getAllPerPropertiesUtil();
	Logger logger = Logger.getLogger(Fetch_Main.class.getName());

	public static void main(String[] args) {
		PropertyConfigurator.configure(propertiesUtil.getValue("LOG4J_PATH"));
		new Fetch_Main().fetch();
	}

	public void fetch() {
		String date = factory.getCurrentDate();
		System.out.println(propertiesUtil.getValue("HTML_PATH")+"android/list/" + date);
		//解析
		System.out.println(propertiesUtil.getValue("HTML_PATH")+"android/list/" + date);
		runList.add(new AndroidReadThread(propertiesUtil.getValue("HTML_PATH")+"android/list/" + date));
		runList.add(new IpadReadThread(propertiesUtil.getValue("HTML_PATH")+"ipad/list/" + date));
		runList.add(new IphoneReadThread(propertiesUtil.getValue("HTML_PATH")+"iphone/list/" + date));
		runList.add(new MakePic()); // 最后执行生成图片
		new SyncManager(runList).run();
		runList.clear();
	}
}
*/