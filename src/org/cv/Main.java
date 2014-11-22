/*package org.cv;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.spring.SpringUtils;
import org.cv.core.util.MakePic;
import org.cv.core.util.PropertiesUtil;
import org.cv.core.util.SyncManager;
import org.cv.parsing.thread.ParsingStart;
import org.cv.spider.Spider_Fetch_Main;
import org.cv.spider.thread.SpiderStart;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
sdfsdf
public class Main {
	List<Runnable> runList = new ArrayList<Runnable>();
	Factory factory = new ConcreteFactory();
	PropertiesUtil propertiesUtil = factory.getAllPerPropertiesUtil();
	Logger logger = Logger.getLogger(Spider_Fetch_Main.class);

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				new String[] { "classpath:spring-mybatis.xml",
						"classpath:spring.xml" });
		SpringUtils contextHolder = new SpringUtils();
		contextHolder.setApplicationContext(applicationContext);
		new Main().begin();
		contextHolder.destroy();//清理
	}

	private void begin() {
		// TODO Auto-generated method stub
		//下载html
		runList.add(new SpiderStart());
		new SyncManager(runList).run();
		runList.clear();

		//解析html
		runList.add(new ParsingStart());
		new SyncManager(runList).run();
		runList.clear();
		
		// 最后执行生成图片
		runList.add(new MakePic()); 
		new SyncManager(runList).run();
		runList.clear();
	}
}
*/