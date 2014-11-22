package org.cv.spider.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.util.GetUrlUtils;

/**
 * @classDescription:
 * @author:Lambda
 */
public class IphoneThread implements Runnable {
	Factory factory = new ConcreteFactory();
	String list_host = "top.ipadown.com";// 列表页Host
	GetUrlUtils getListUrlUtils = factory.getUrlUtils();
	private static List<String> _lieBiaoUrlList = new ArrayList<String>();
	Logger logger = Logger.getLogger(IphoneThread.class.getName());

	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		for (String lieBiaoUrl :_lieBiaoUrlList) {
			String fenLei = getFenLei(lieBiaoUrl);
			getListUrlUtils.downIosListPage(lieBiaoUrl, list_host, "iphone", fenLei);// 下载每个列表页
		}
	}

	private String getFenLei(String lieBiaoUrl) {
		// TODO Auto-generated method stub
		lieBiaoUrl = lieBiaoUrl.substring(lieBiaoUrl.lastIndexOf("=") + 1,
				lieBiaoUrl.length());
		return lieBiaoUrl;
	}

	public List<String> init() {
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=25129");// 全部
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6014");// 游戏
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6002");// 工具
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6016");// 娱乐
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6012");// 生活
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6011");// 音乐
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6018");// 书籍
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6004");// 体育
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6013");// 健康
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6020");// 医疗
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6006");// 参考
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6000");// 商业
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6001");// 天气
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6010");// 导航
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6008");// 摄影
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6007");// 效率
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6017");// 教育
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6009");// 新闻
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6003");// 旅行
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6005");// 社交
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6015");// 财务
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6021");// 报刊杂志
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6022");// 商品指南
		_lieBiaoUrlList
				.add("http://top.ipadown.com/?device=&store=cn&subcate=25180&limit=200&cate=6023");// 美食佳饮
		return _lieBiaoUrlList;
	}

	public static void main(String[] args) {
		new Thread(new IphoneThread()).start();
	}
}
