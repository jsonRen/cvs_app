package org.cv.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.spring.SpringUtils;
import org.cv.model.AppInfo;
import org.cv.model.ConfigInfo;
import org.cv.model.PageBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @classDescription: 获得URL工具类
 * @author:Lambda
 */
public class GetUrlUtils {
	ConfigInfo configInfo = (ConfigInfo) SpringUtils.getBean("configInfo");
	Factory factory = new ConcreteFactory();
	CommonFetch commonFetch = factory.getCommonFetch();
	ReadInputStreamUtil inputStreamUtil = factory.getInputStreamUtil();

	Logger logger = Logger.getLogger(GetUrlUtils.class.getName());

	private AppInfo appInfo;
	
	public GetUrlUtils() {
		appInfo = SpringUtils.getBean(AppInfo.class);
	}
	
	/**
	 * 
	 * @param listUrl
	 *            android版列表
	 * @param androidHost
	 *            android版host
	 * @param shebei
	 *            设备标识，android
	 * @param bangDan
	 *            榜单
	 * @param fenLei
	 *            分类
	 * @param pageNum
	 *            页码
	 */
	public void downAndroidListPage(String listUrl, String androidHost,
			String shebei, String bangDan, String fenLei, String pageNum) {
		// TODO Auto-generated method stub
		String htmlContent = "";
		String date = factory.getCurrentDate();
		try {
			Thread.sleep(3000);
			PageBean pageBean = commonFetch.getContentFromUrl(listUrl,
					androidHost, "android");
			if (null == pageBean) {
				downAndroidListPage(listUrl, androidHost, shebei, bangDan,
						fenLei, pageNum);
			}
			htmlContent = pageBean.getContent();
			String temp = "";
			temp = configInfo.getHtml_root_path() + "android/" + "list/"
					+ date + "/" + bangDan + "." + fenLei + "." + pageNum
					+ ".html";
			if (htmlContent == null || "".equals(htmlContent)) {
				downAndroidListPage(listUrl, androidHost, shebei, bangDan,
						fenLei, pageNum);
			}
			boolean b = false;
			if (!"".equals(htmlContent) && null != htmlContent) {
				b = inputStreamUtil.create(htmlContent, temp);
			}
			if (b) {
				System.out.println("OK");
			}
		} catch (Exception e) {
			// TODO: handle exception
			downAndroidListPage(listUrl, androidHost, shebei, bangDan, fenLei,
					pageNum);
			logger.error("error" + e);
			logger.debug("debug");
		}
	}

	/**
	 * 获得本地列表页信息
	 * 
	 * @param listUrl
	 * @return
	 */
	public List<AppInfo> getAndroidListContent(String listUrl) {
		// TODO Auto-generated method stub
		List<AppInfo> appInfoList = new ArrayList<AppInfo>();
		String temp = listUrl.substring(listUrl.lastIndexOf(".") - 1);
		temp = temp.substring(0, temp.indexOf("."));
		int pageCount = Integer.valueOf(temp);
		String content = inputStreamUtil.readFileContent(listUrl);
		if (null == content || "".equals(content)) {
			return null;
		}
		Document document = getPageDocument(content);
		Elements elements = document.select("ul#iconList>li");
		String appName = "";
		String detailUrl = "";
		String downLoadNumer = "";
		String logoUrl = "";
		int downLoadUnit = 0;
		if (pageCount <= 4) {
			for (int i = 0; i < elements.size(); i++) {
				int rank = (pageCount - 1) * 49 + i + 1;
				appName = elements.get(i).select("h3").text();
				detailUrl = elements.get(i).select("h3>a").attr("href");
				logoUrl = elements.get(i).select("img").attr("_src");
				String downLoadTemp = elements.get(i).select("span").text();
				downLoadNumer = StringUtil.getNum(downLoadTemp);
				downLoadUnit = gerDownUnit(StringUtil.getChinese(downLoadTemp));
				appInfo.getAppRank().setDownloadUnit(downLoadUnit+"");
				appInfo.getAppRank().setDownloadNum(downLoadNumer);
				appInfo.setLogoPath(logoUrl);
				appInfo.setAppName(appName);
				appInfo.setDetailUrl("http://zhushou.360.cn" + detailUrl);
				appInfo.getAppRank().setRank(rank+"");
				appInfoList.add(appInfo);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				int rank = (pageCount - 1) * 49 + i + 1;
				appName = elements.get(i).select("h3").text();
				detailUrl = elements.get(i).select("h3>a").attr("href");
				String downLoadTemp = elements.get(i).select("span").text();
				downLoadNumer = StringUtil.getNum(downLoadTemp);
				downLoadUnit = gerDownUnit(StringUtil.getChinese(downLoadTemp));
				appInfo.getAppRank().setDownloadUnit(downLoadUnit+"");
				appInfo.getAppRank().setDownloadNum(downLoadNumer);
				appInfo.setLogoPath(logoUrl);
				appInfo.setAppName(appName);
				appInfo.setDetailUrl("http://zhushou.360.cn" + detailUrl);
				appInfo.getAppRank().setRank(rank+"");
				appInfoList.add(appInfo);
			}
		}
		return appInfoList;
	}

	/**
	 * 解析html
	 * 
	 * @param url
	 * @return
	 */
	public AppInfo getAndroidDetailsConent(AppInfo appInfo) {
		// TODO Auto-generated method stub
		String host = "zhushou.360.cn";
		String content = commonFetch.getHtmlContent(appInfo.getDetailUrl(), host);
		String publisher = "";// 发行商
		String TempdownLoadNum = "";
		if (null != content || "".equals(content)) {
			Document document = getPageDocument(content);
			// appName =
			// document.select("div.product>dl.clearfix>dd>h2#app-name").text();
			Elements eleDownNum = document
					.select("div.product>dl.clearfix>dd>div.pf>span.s-3");
			for (int i = 0; i < eleDownNum.size(); i++) {
				TempdownLoadNum = eleDownNum.get(0).text();
				TempdownLoadNum = TempdownLoadNum.substring(TempdownLoadNum
						.indexOf("：") + 1, TempdownLoadNum.length());
			}
			/*int downLoadUnit = gerDownUnit(StringUtil
					.getChinese(TempdownLoadNum));// 下载单位
			String downLoadNumber = !StringUtil.getNum(TempdownLoadNum).equals(
					"") ? StringUtil.getNum(TempdownLoadNum) : "0";// 下载次数
*/			Elements elements = document.select("div.base-info>table>tbody>tr");
			for (int i = 0; i < elements.size(); i++) {
				Element elementTr = elements.get(0);
				Elements elementTd = elementTr.select("td");
				for (int j = 0; j < elementTd.size(); j++) {
					publisher = elementTd.get(0).text();
					publisher = publisher.substring(publisher.indexOf("：") + 1, publisher
							.length());
				}
			}
			appInfo.setPublisher(publisher);
		} else {
			return null;
		}
		return appInfo;
	}

	private int gerDownUnit(String downUnit) {
		// TODO Auto-generated method stub
		int i = 0;
		if ("次下载".equals(downUnit)) {
			i = 1;
		}
		if ("万次下载".equals(downUnit)) {
			i = 2;
		}
		if ("亿次下载".equals(downUnit)) {
			i = 3;
		}
		return i;
	}

	// 将字符串转换成Document对象
	private Document getPageDocument(String content) {
		return Jsoup.parse(content);
	}

	public void downIosListPage(String lieBiaoUrl, String listHost,
			String sheBei, String fenLei) {
		// TODO Auto-generated method stub
		String htmlContent = "";
		String date = factory.getCurrentDate();
		try {
			Thread.sleep(3000);
			PageBean pageBean = commonFetch.getContentFromUrl(lieBiaoUrl,
					listHost, "ios");
			if (null == pageBean) {
				downIosListPage(lieBiaoUrl, listHost, sheBei, fenLei);
			}
			htmlContent = pageBean.getContent();
			String temp = "";
			temp = configInfo.getHtml_root_path() + sheBei + "/"
					+ "list/" + date + "/" + fenLei + ".html";
			if (htmlContent == null || "".equals(htmlContent)) {
				downIosListPage(lieBiaoUrl, listHost, sheBei, fenLei);
			}
			boolean b = false;
			if (!"".equals(htmlContent) && null != htmlContent) {
				b = inputStreamUtil.create(htmlContent, temp);
			}
			if (b) {
				System.out.println("OK");
			}
		} catch (Exception e) {
			// TODO: handle exception
			downIosListPage(lieBiaoUrl, listHost, sheBei, fenLei);
			logger.debug("debug");
			logger.error("error" + e);
		}
	}

	public Map<String, String> getItunesConent(String url, String appType,
			String host, String sheBei) {
		// TODO Auto-generated method stub
		Map<String, String> _map = new HashMap<String, String>();
		PageBean pageBean = commonFetch.getContentFromUrl(url, host, sheBei);
		if (pageBean == null) {
			return null;
		}
		Document document = pageBean.getContentDoc();
		Elements elements = document.select("div#content");
		for (Element element : elements) {
			String appName = element.select("div.padder>div#title>div.left>h1")
					.text();
			String issuer = element.select("div.padder>div#title>div.left>h2")
					.text();
			issuer = issuer.substring(issuer.indexOf("：") + 1, issuer.length());
			if (!"".equals(appName) && !"".equals(issuer)) {
				_map.put("appName", appName);// AppName
				_map.put("issuser", issuer);// 发行商名称
			}
		}
		return _map;
	}

	/**
	 * 
	 * @param url
	 *            本地ipad版本路径
	 * @return paiming appname detailsUrl
	 */
	public List<Map<String, String>> getIOSListContent(String url) {
		// TODO Auto-generated method stub
		String content = inputStreamUtil.readFileContent(url);
		List<Map<String, String>> _listMap = new ArrayList<Map<String, String>>();
		Map<String, String> _map = null;
		if (null == content || "".equals(content)) {
			return null;
		}
		Document document = getPageDocument(content);
		Elements elements = document
				.select("div.charts_frame>table>tbody>tr>td.free");
		String appName = "";
		String detailUrl = "";
		String appType = "";
		String logoUrl = "";
		try {
			for (int i = 0; i < elements.size(); i++) {
				int rank = i + 1;
				_map = new HashMap<String, String>();
				appName = elements.get(i).select(
						"div.app_block>div.app_info>h4").text();
				logoUrl = elements.get(i).select("img").attr("src");
				detailUrl = elements.get(i).select(
						"div.app_block>div.app_info>h4>a").attr("href");
				appType = elements.get(i)
						.select("div.app_block>div.app_info>p").first().text();
				if (!"".equals(appType) && appType.indexOf("：") != -1) {
					appType = appType.substring(appType.indexOf("：") + 1,
							appType.length());
				}
				String appKey = "";
				if (!"".equals(detailUrl) && detailUrl != null
						&& detailUrl.indexOf("/") > 0) {
					appKey = detailUrl.substring(
							detailUrl.lastIndexOf("/") + 1, detailUrl.length());
				}
				_map.put("appKey", appKey);
				_map.put("logoUrl", logoUrl);
				_map.put("appName", appName);
				_map.put("detailUrl", detailUrl);
				_map.put("rank", rank + "");
				_map.put("appType", appType);
				_listMap.add(_map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("debug");
		}
		return _listMap;
	}

	/**
	 * @param url
	 *            本地iphone列表路径
	 * @return paiming appname detailsUrl
	 */
	public List<Map<String, String>> getIphoneListContent(String url) {
		String content = inputStreamUtil.readFileContent(url);
		List<Map<String, String>> _listMap = new ArrayList<Map<String, String>>();
		Map<String, String> _map = null;
		if (null == content || "".equals(content)) {
			return null;
		}
		Document document = getPageDocument(content);
		Elements elements = document.select("div.al_appinfo>table>tbody");
		String appName = "";
		String detailUrl = "";
		String appType = "";
		for (int i = 0; i < elements.size(); i++) {
			int rank = i + 1;
			_map = new HashMap<String, String>();
			appName = elements.get(i).select("tr>td.al_appname").text();
			detailUrl = elements.get(i).select("td>a").attr("href");
			appType = elements.get(i).select("tr").get(1).text();
			_map.put("appType", appType);
			_map.put("appName", appName);
			_map.put("detailUrl", detailUrl);
			_map.put("rank", rank + "");
			_listMap.add(_map);
		}
		return _listMap;
	}

	public String getitunesUrl(String url, String host, String sheBei) {
		// TODO Auto-generated method stub
		String itunesUrl = "";
		try {
			Thread.sleep(1000);
			PageBean pageBean = commonFetch
					.getContentFromUrl(url, host, sheBei);
			if (pageBean == null) {
				return "";
			}
			Document document = pageBean.getContentDoc();
			if (document.getAllElements().hasClass("main_frame")) {
				itunesUrl = document.select(
						"div.main_frame>iframe#contentFrame").attr("src");
			} else {
				itunesUrl = document.select("div.redeem>a>img").attr("src");
				itunesUrl = itunesUrl.substring(itunesUrl.indexOf("&") + 1,
						itunesUrl.lastIndexOf("?"));
				itunesUrl = itunesUrl.substring(itunesUrl.indexOf("=") + 1,
						itunesUrl.length());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("debug");
			logger.error("error" + e);
		}
		return itunesUrl;
	}

	public void downIphoneListPage(String lieBiaoUrl, String listHost,
			String string, String fenLei) {
		// TODO Auto-generated method stub
		String htmlContent = "";
		String date = factory.getCurrentDate();
		try {
			Thread.sleep(3000);
			htmlContent = commonFetch.getHtmlContent(lieBiaoUrl, listHost);
			if ("".equals(htmlContent) || null == htmlContent) {
				downIphoneListPage(lieBiaoUrl, listHost, string, fenLei);
			}
			String temp = "";
			temp = configInfo.getHtml_root_path()  + "iphone/" + "list/"
					+ date + "/" + fenLei + ".html";
			boolean b = false;
			if (!"".equals(htmlContent) && null != htmlContent) {
				b = inputStreamUtil.create(htmlContent, temp);
			}
			if (b) {
				System.out.println("OK");
			}
		} catch (Exception e) {
			// TODO: handle exception
			downIphoneListPage(lieBiaoUrl, listHost, string, fenLei);
			logger.debug("debug");
			logger.error("error" + e);
		}
	}
}
