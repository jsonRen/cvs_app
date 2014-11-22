/*package org.cv.parsing.thread;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.cv.bean.AndroidBean;
import org.cv.bean.IosBean;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.util.CommonFetch;
import org.cv.core.util.DataBaseCheckUtils;
import org.cv.core.util.GetUrlUtils;
import org.cv.core.util.ReadInputStreamUtil;
import org.cv.core.util.file.FileUtils;
import org.cv.dao_old.IosDao;

*//**
 * @classDescription: Ipad版解析线程
 * @author:Lambda
 *//*
public class IpadReadThread implements Runnable {
	Factory factory = new ConcreteFactory();
	IosDao<IosBean> dao = factory.getIosDao();
	IosBean iosBean = factory.getIosBean();
	AndroidBean androidBean = factory.getAndroidBean();
	DataBaseCheckUtils baseCheckUtils = factory.getDataBaseCheckUtils();
	GetUrlUtils getListUrlUtils = factory.getUrlUtils();
	FileUtils fileUtils = factory.getFileUtils();
	ReadInputStreamUtil inputStreamUtil = factory.getInputStreamUtil();
	private String filePath = "";

	Logger logger = Logger.getLogger(IpadReadThread.class.getName());

	public IpadReadThread(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		assign(filePath);
	}

	*//**
	 * 
	 * insertList 方法
	 * <p>
	 * 方法说明:如果解析不到详细页，则把列表页的APP的一些信息加入到APP基础表中
	 * </p>
	 * 
	 * @param _map
	 * @return void
	 * @author Lambda
	 * @date 2014-10-16
	 *//*
	private void insertList(Map<String, String> _map) {
		try {
			if ("".equals(_map.get("appId"))) { // 数据库中不存在
				insertDataBaseInfo(_map, "bucunzai");
			} else {
				insertDataBaseInfo(_map, "cunzai");
			}
			_map.clear();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error"+e);
			logger.debug("debug");
		}
	}

	*//**
	 * 给IosBean赋值
	 * 
	 * @param filePath
	 *//*
	private void assign(String filePath) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, String>> _listMap = null;
			List<String> _list = fileUtils.getFileList(filePath); // 遍历本地html文件
			for (String url : _list) {
				String appId = "";// APP_ID
				String rankAppType = "";// 具体排名类型
				String appType = "";// 本身类型
				_listMap = getListUrlUtils.getIOSListContent(url);// 解析本地网页获得
				for (int i = 0; i < _listMap.size(); i++) {
					if (null == _listMap.get(i)) {
						continue;
					}
					// 初始化
					appId = checkDataBse(_listMap.get(i), "ipad");
					rankAppType = returnRankApptype(url);
					appType = returnLeiBie(_listMap.get(i).get("appType"));
					_listMap.get(i).put("appId", appId);
					_listMap.get(i).put("appType", appType);
					_listMap.get(i).put("rankAppType", rankAppType);
					_listMap.get(i).put("shebei", "ipad");
					boolean b = checkDataBaseValue(_listMap.get(i));
					if (!b) {
						continue;
					}
				}
			}
			_list.clear();
			_listMap.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error" + e);
			logger.debug("debug");
		}

	}

	private boolean checkDataBaseValue(Map<String, String> map) {
		// TODO Auto-generated method stub
		Map<String, String> lieBiao = map;
		Map<String, String> iosDetailsContentMap = null;
		if (map.isEmpty() || map.size() <= 0 || map == null) {
			return false;
		}
		if ("".equals(map.get("appId"))) { // 不存在，解析itunes，插入新数据
			String itunesUrl = getListUrlUtils.getitunesUrl(map.get("detailUrl"),
					"top.ipadown.com", "ios");// 通过分析列表页得到itunesURl
			iosDetailsContentMap = getListUrlUtils.getItunesConent(itunesUrl, map
					.get("appType"), "itunes.apple.com", "ios");
			if ("".equals(itunesUrl) || iosDetailsContentMap == null) {// 如果map为空则APP基础信息表的数据为列表中解析到的数据
				insertList(lieBiao);
				return false;
			}
			// 数据库无值时再次初始化
			iosDetailsContentMap.put("appId", map.get("appId"));
			iosDetailsContentMap.put("logoUrl", map.get("logoUrl"));
			iosDetailsContentMap.put("rank", map.get("rank"));
			iosDetailsContentMap.put("shebei", map.get("shebei"));
			iosDetailsContentMap.put("appName", map.get("appName"));
			iosDetailsContentMap.put("appType", map.get("appType"));
			iosDetailsContentMap.put("rankAppType", map.get("rankAppType"));
			insertDataBaseInfo(iosDetailsContentMap, "bucunzai");
			iosDetailsContentMap.clear();
		} else { // 存在，更新rank
			insertDataBaseInfo(map, "cunzai");
		}
		map.clear();
		return true;
	}

	private void insertDataBaseInfo(Map<String, String> map, String tag) {
		// TODO Auto-generated method stub
		try {
			IosBean iosBean = new IosBean();
			String date = factory.getCurrentDate();
			iosBean.setApp_id(map.get("appId"));
			iosBean.setAppName(map.get("appName"));
			iosBean.setAppType(map.get("appType"));
			iosBean.setDevice_type(map.get("shebei"));
			iosBean.setRank(map.get("rank"));
			iosBean.setLogoPath(map.get("logoUrl"));
			iosBean.setSys_type("ios");
			iosBean.setPublisher(map.get("issuser"));
			iosBean.setRankDate(date);
			iosBean.setRankType(map.get("rankAppType"));
			map.put("rankData", date); // map单独插入排名时间
			if (tag.equals("bucunzai")) { // 数据库不存在时插入
				byte[] imgByte = CommonFetch.getBytesByURL(iosBean
						.getLogoPath());
				String imgPath = inputStreamUtil.writeImageToDis(imgByte, map);
				imgPath = checkImgPath(imgPath);
				iosBean.setImgLogoPath(imgPath);
				String appId = dao.insertBasInfo(iosBean);
				iosBean.setApp_id(appId);
				boolean b = checkRank(map);// 如果存在，返回true
				if (!b) {
					dao.insertRank(iosBean);
				}
			} else if (tag.equals("cunzai")) { // 存在时更新rank
				iosBean.setApp_id(map.get("appId"));
				boolean b = checkRank(map);// 存在返回true，不存在返回false
				if (!b) {
					dao.insertRank(iosBean);
				}
			}
			map.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error" + e);
			logger.debug("debug");
		}
	}

	private boolean checkRank(Map<String, String> map) {
		boolean tag = false;
		if (map != null) {
			tag = baseCheckUtils.checkRank(map);
		}
		return tag;
	}

	private String checkDataBse(Map<String, String> map, String deviceType) {
		// TODO Auto-generated method stub
		String appId = "";
		if (map != null) {
			appId = baseCheckUtils.getDataBaseValue(deviceType, map);
		} else {
			appId = "";
		}
		return appId;
	}

	@SuppressWarnings("null")
	private String returnRankApptype(String url) {
		// TODO Auto-generated method stub
		if (null == url && url.indexOf(".") == -1 && "".equals(url)) {
			return "";
		}
		String temp = url.substring(url.lastIndexOf(System
				.getProperty("file.separator")) + 1, url.indexOf("."));
		if ("25129".equals(temp)) { // 全部
			return "1000";
		} else if ("6014".equals(temp)) {// 游戏
			return "1001";
		} else if ("6021".equals(temp)) {// 报刊杂志
			return "1002";
		} else if ("6015".equals(temp)) {// 财务
			return "1003";
		} else if ("6006".equals(temp)) {// 参考
			return "1004";
		} else if ("6010".equals(temp)) {// 导航
			return "1005";
		} else if ("6002".equals(temp)) {// 工具
			return "1006";
		} else if ("6013".equals(temp)) {// 健康健美
			return "1007";
		} else if ("6017".equals(temp)) {// 教育
			return "1008";
		} else if ("6003".equals(temp)) {// 旅行
			return "1009";
		} else if ("6000".equals(temp)) {// 商业
			return "1010";
		} else if ("6022".equals(temp)) {// 商品指南
			return "1011";
		} else if ("6005".equals(temp)) {// 社交
			return "1012";
		} else if ("6008".equals(temp)) {// 摄影与录像
			return "1013";
		} else if ("6012".equals(temp)) {// 生活
			return "1014";
		} else if ("6004".equals(temp)) {// 体育
			return "1015";
		} else if ("6001".equals(temp)) {// 天气
			return "1016";
		} else if ("6018".equals(temp)) {// 图书
			return "1017";
		} else if ("6007".equals(temp)) {// 效率
			return "1018";
		} else if ("6009".equals(temp)) {// 新闻
			return "1019";
		} else if ("6020".equals(temp)) {// 医疗
			return "1020";
		} else if ("6011".equals(temp)) {// 音乐
			return "1021";
		} else if ("6016".equals(temp)) {// 娱乐
			return "1022";
		} else if ("6023".equals(temp)) {// 美食佳饮
			return "1023";
		} else {
			return "";
		}
	}

	private String checkImgPath(String imgPath) {
		String regex = "downloadKey";
		Matcher matcher = Pattern.compile(regex).matcher(imgPath);
		if (matcher.find()) {
			imgPath = "";
		}
		return imgPath;
	}

	private String returnLeiBie(String str) {
		String leiBie = "";
		if ("25129".equals(str)) { // 全部
			return "1000";
		}
		if ("游戏".equals(str)) {// 游戏
			return "1001";
		}
		if ("报刊杂志".equals(str)) {// 报刊杂志
			return "1002";
		}
		if ("财务".equals(str)) {// 财务
			return "1003";
		}
		if ("参考".equals(str)) {// 参考
			return "1004";
		}
		if ("导航".equals(str)) {// 导航
			return "1005";
		}
		if ("工具".equals(str)) {// 工具
			return "1006";
		}
		if ("健康健美".equals(str)) {// 健康健美
			return "1007";
		}
		if ("教育".equals(str)) {// 教育
			return "1008";
		}
		if ("旅游".equals(str)) {// 旅游
			return "1009";
		}
		if ("商务".equals(str)) {// 商业
			return "1010";
		}
		if ("商品指南".equals(str)) {// 商品指南
			return "1011";
		}
		if ("社交".equals(str)) {// 社交
			return "1012";
		}
		if ("摄影与录像".equals(str)) {// 摄影与录像
			return "1013";
		}
		if ("生活".equals(str)) {// 生活
			return "1014";
		}
		if ("体育".equals(str)) {// 体育
			return "1015";
		}
		if ("天气".equals(str)) {// 天气
			return "1016";
		}
		if ("图书".equals(str)) {// 图书
			return "1017";
		}
		if ("效率".equals(str)) {// 效率
			return "1018";
		}
		if ("新闻".equals(str)) {// 新闻
			return "1019";
		}
		if ("医疗".equals(str)) {// 医疗
			return "1020";
		}
		if ("音乐".equals(str)) {// 音乐
			return "1021";
		}
		if ("娱乐".equals(str)) {// 娱乐
			return "1022";
		}
		if ("美食佳饮".equals(str)) {// 美食佳饮
			return "1023";
		}
		return leiBie;
	}
}
*/