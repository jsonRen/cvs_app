package org.cv.spider.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.util.GetUrlUtils;

/**
 * @classDescription:
 * @author:Lambda
 */
public class AndroidThread implements Runnable {
	String android_host = "zhushou.360.cn";
	String ZHONGHE = "order/weekdownload/?page=";
	String ZONGBANG = "order/download/?page=";
	String HAOPING = "order/poll/?page=";
	List<String> _list = new ArrayList<String>();
	Factory factory = new ConcreteFactory();
	GetUrlUtils getListUrlUtils = factory.getUrlUtils();
	Logger logger = Logger.getLogger(AndroidThread.class);

	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		for (int i = 0; i < _list.size(); i++) {
			System.out.println(_list.get(i));
			String bangDan = returnBanDan(_list.get(i).substring(0,
					_list.get(i).indexOf("/")));// 根据请求URL获得榜单信息
			String fenLei = _list.get(i).substring(
					_list.get(i).indexOf("/") + 1, _list.get(i).length());
			fenLei = fenLei.substring(0, fenLei.indexOf("/"));// 根据请求URL获得列表页的分类
			String listUrl = _list.get(i).substring(
					_list.get(i).indexOf("/") + 1, _list.get(i).length());
			listUrl = listUrl.substring(listUrl.indexOf("/") + 1, listUrl
					.length());// 具体的列表URL
			String pageNum = returnPageNum(_list.get(i));
			getListUrlUtils.downAndroidListPage(listUrl, android_host,
					"android", bangDan, fenLei, pageNum);
		}
	}

	private String returnPageNum(String pageNum) {
		// TODO Auto-generated method stub
		pageNum = pageNum.substring(pageNum.lastIndexOf("=") + 1, pageNum
				.length());
		return pageNum;
	}

	private String returnBanDan(String bangDan) {
		// TODO Auto-generated method stub
		if ("总榜".equals(bangDan)) {
			return "0";
		}
		if ("综合".equals(bangDan)) {
			return "1";
		}
		if ("好评榜".equals(bangDan)) {
			return "2";
		}
		return "0";
	}

	public void init() {
		// TODO Auto-generated method stub
		Map<String, String> _map = new HashMap<String, String>();
		_map.put("1", "http://zhushou.360.cn/list/index/cid/1/");//全部
		_map.put("11", "http://zhushou.360.cn/list/index/cid/11/");//系统安全
		_map.put("12", "http://zhushou.360.cn/list/index/cid/12/");//通讯社交
		_map.put("14", "http://zhushou.360.cn/list/index/cid/14/");//影音视听
		_map.put("15", "http://zhushou.360.cn/list/index/cid/15/");//新闻阅读
		_map.put("16", "http://zhushou.360.cn/list/index/cid/16/");//生活休闲
		_map.put("18", "http://zhushou.360.cn/list/index/cid/18/");//主题壁纸
		_map.put("17", "http://zhushou.360.cn/list/index/cid/17/");//办公商务
		_map.put("102228", "http://zhushou.360.cn/list/index/cid/102228/");//摄影摄像
		_map.put("102230", "http://zhushou.360.cn/list/index/cid/102230/");//购物优惠
		_map.put("102231", "http://zhushou.360.cn/list/index/cid/102231/");//地图旅游
		_map.put("102232", "http://zhushou.360.cn/list/index/cid/102232/");//教育学习
		_map.put("102139", "http://zhushou.360.cn/list/index/cid/102139/");//金融理财
		_map.put("102233", "http://zhushou.360.cn/list/index/cid/102233/");//健康医疗
		String tempUrl = "";
		for (String key : _map.keySet()) {
			for (int i = 1; i <= 5; i++) {
				tempUrl = _map.get(key) + ZONGBANG + i;
				fenLei("总榜", key, tempUrl);
				tempUrl = _map.get(key) + ZHONGHE + i;
				fenLei("综合", key, tempUrl);
				tempUrl = _map.get(key) + HAOPING + i;
				fenLei("好评榜", key, tempUrl);
			}
		}
	}

	private void fenLei(String bangDan, String fenlei, String url) {
		// TODO Auto-generated method stub
		String urlTemp = bangDan + "/" + fenlei + "/" + url;
		_list.add(urlTemp);
	}
}
