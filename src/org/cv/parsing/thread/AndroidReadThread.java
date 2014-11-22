package org.cv.parsing.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.spring.SpringUtils;
import org.cv.core.util.CommonFetch;
import org.cv.core.util.GetUrlUtils;
import org.cv.core.util.ReadInputStreamUtil;
import org.cv.core.util.file.FileUtils;
import org.cv.model.AppInfo;
import org.cv.services.AppInfoService;
import org.cv.services.AppRankService;

/**
 * @classDescription: Android版解析线程
 * @author:Lambda
 */
public class AndroidReadThread implements Runnable {
	Factory factory = null;
	GetUrlUtils getListUrlUtils = null;
	FileUtils fileUtils = null;
	ReadInputStreamUtil inputStreamUtil = null;
	String filePath = "";
	Logger logger = Logger.getLogger(AndroidReadThread.class);

	private AppInfo appInfo;

	private AppInfoService appInfoService;

	private AppRankService appRankService;

	public AndroidReadThread(String filePath) {
		this.filePath = filePath;
		factory = new ConcreteFactory();
		getListUrlUtils= factory.getUrlUtils();
		fileUtils = factory.getFileUtils();
		inputStreamUtil = factory.getInputStreamUtil();
		appInfoService = SpringUtils.getBean(AppInfoService.class);
		appRankService = SpringUtils.getBean(AppRankService.class);
		appInfo = SpringUtils.getBean(AppInfo.class);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<String> _listQuanBu = initQuanBu(filePath);
		List<String> _listOther = initOther(filePath);
		fenLeiFetch(_listOther);// 先遍历然后解析android版本其他分类
		fenLeiFetch(_listQuanBu);// 再遍历然后解析android版本全部
		_listQuanBu.clear();
		_listOther.clear();
	}

	/**
	 * insertList 方法
	 * <p>
	 * 方法说明:如果解析不到详细页，则把列表页的APP的一些信息加入到APP基础表中
	 * </p>
	 * 
	 * @param _map
	 * @return void
	 * @author Lambda
	 */
	private void insertList(AppInfo appInfo) {
		try {
			if ("".equals(appInfo.getAppId())) { // 数据库中不存在
				insertDataBaseInfo(appInfo, "bucunzai");
			} else {
				insertDataBaseInfo(appInfo, "cunzai");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error" + e);
			logger.debug("debug");
		}
	}

	private void fenLeiFetch(List<String> _list) {
		// TODO Auto-generated method stub
		List<AppInfo> appInfoList = new ArrayList<AppInfo>();// 保存本地解析出的信息
		for (String listUrl : _list) {
			String appType = returnLeiBie(listUrl);
			String rankAppType = returnAppRankType(listUrl);
			appInfoList = getListUrlUtils.getAndroidListContent(listUrl);// 解析本地网页获得列表页的信息
			String appId = "";
			for (int i = 0; i < appInfoList.size(); i++) {
				appId = appInfoService.checkDateBase(appInfoList.get(i));
				appInfo.getAppRank().setRankAppType(rankAppType);
				appInfo.setAppType(appType);
				appInfo.setAppId(appId);
				appInfo.setDeviceType("android");
				appInfo.setSysType("android");
				boolean b = checkDataBaseValue(appInfo);
				if (!b) {
					continue;
				}
			}
		}
		appInfoList.clear();
	}

	/**
	 * 
	 * checkDataBaseValue 方法
	 * <p>
	 * 方法说明:把得到的APP_ID插入重新插入数据库，如果存在则更新rank表，不存在则自动生成APP_ID
	 * </p>
	 * 
	 * @param appId
	 * @param _map
	 * @return void
	 * @author Lambda
	 * @date 2014-10-18
	 */
	private boolean checkDataBaseValue(AppInfo appInfo) {
		// TODO Auto-generated method stub
		if ("".equals(appInfo.getAppId())) { // 不存在，解析详细页
			appInfo = getListUrlUtils.getAndroidDetailsConent(appInfo);
			if ("".equals(appInfo.getPublisher())||appInfo.getPublisher()==null) {//解析不到详细页时就把列表页存入库中
				insertList(appInfo);
				return false;
			}
			// 不存在时重新初始化
			insertDataBaseInfo(appInfo, "bucunzai");
		} else { // 更新rank
			insertDataBaseInfo(appInfo, "cunzai");
		}
		return true;
	}

	private void insertDataBaseInfo(AppInfo appInfo, String tag) {
		// TODO Auto-generated method stub
		try {
			String date = factory.getCurrentDate();
			appInfo.getAppRank().setRankDate(date);
			if (tag.equals("bucunzai")) { // 数据库中不存在时插入
				byte[] imgByte = CommonFetch.getBytesByURL(appInfo
						.getLogoPath());
				String imgPath = inputStreamUtil.writeImageToDis(imgByte, appInfo);
				appInfo.setImgLogoPath(imgPath);
				appInfo.setPublisher(appInfo.getPublisher());
				String appId = appInfoService.insertAndGetAppId(appInfo);
				appInfo.setAppId(appId);
				boolean b = checkRank(appInfo); // 不存在返回false
				if (!b) {
					appRankService.insertRank(appInfo);
				}
			} else { // 数据库中存在时
				appInfo.setAppId(appInfo.getAppId());
				boolean b = checkRank(appInfo); // 不存在返回false
				if (!b) {
					appRankService.insertRank(appInfo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error" + e);
			logger.debug("debug");
		}
	}


	/**
	 * 
	 * checkRank 方法
	 * <p>
	 * 方法说明: im
	 * </p>
	 * 
	 * @param map
	 * @return
	 * @return boolean
	 * @author Lambda
	 * @date 2014-10-17
	 */
	private boolean checkRank(AppInfo appInfo) {
		boolean tag = false;
		if (appInfo != null) {
			tag = appInfoService.checkRank(appInfo);
		}
		return tag;
	}

	private String returnAppRankType(String listUrl) {
		// TODO Auto-generated method stub
		listUrl = listUrl.substring(
				listUrl.lastIndexOf(System.getProperty("file.separator")) + 1,
				listUrl.indexOf("."));
		return listUrl;
	}

	/**
	 * 
	 * @param filePath
	 */
	private List<String> initQuanBu(String filePath) {
		// TODO Auto-generated method stub
		List<String> _list = fileUtils.getFileList(filePath);
		List<String> _listQuanBu = new ArrayList<String>();
		for (int i = 0; i < _list.size(); i++) {
			String leiBei = returnLeiBie(_list.get(i));
			if ("2001".equals(leiBei)) {
				_listQuanBu.add(_list.get(i));
			}
		}
		_list.clear();
		return _listQuanBu;
	}

	private List<String> initOther(String filePath) {
		// TODO Auto-generated method stub
		List<String> _list = fileUtils.getFileList(filePath);
		List<String> _listOther = new ArrayList<String>();
		for (int i = 0; i < _list.size(); i++) {
			String leiBei = returnLeiBie(_list.get(i));
			if (!"2001".equals(leiBei)) {
				_listOther.add(_list.get(i));
			}
		}
		_list.clear();
		return _listOther;
	}

	private String returnLeiBie(String str) {
		str = str.substring(
				str.lastIndexOf(System.getProperty("file.separator")) + 1,
				str.length());
		str = str.substring(str.indexOf(".") + 1, str.lastIndexOf("."));
		str = str.substring(0, str.indexOf("."));
		if (str.equals("1")) {
			return "2001";
		}
		if (str.equals("11")) {
			return "2002";
		}
		if (str.equals("12")) {
			return "2003";
		}
		if (str.equals("14")) {
			return "2004";
		}
		if (str.equals("15")) {
			return "2005";
		}
		if (str.equals("16")) {
			return "2006";
		}
		if (str.equals("18")) {
			return "2007";
		}
		if (str.equals("17")) {
			return "2008";
		}
		if (str.equals("102228")) {
			return "2009";
		}
		if (str.equals("102230")) {
			return "2010";
		}
		if (str.equals("102231")) {
			return "2011";
		}
		if (str.equals("102232")) {
			return "2012";
		}
		if (str.equals("102139")) {
			return "2013";
		}
		if (str.equals("102233")) {
			return "2014";
		}
		return "";
	}
}
