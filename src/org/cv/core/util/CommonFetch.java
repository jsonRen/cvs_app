package org.cv.core.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.spring.SpringUtils;
import org.cv.model.ConfigInfo;
import org.cv.model.IPBean;
import org.cv.model.PageBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @classDescription:分析请求类
 * @author:Lambda
 * @createTime:2014-7-11 下午01:09:57
 */
@SuppressWarnings("deprecation")
public class CommonFetch {
	ConfigInfo configInfo = (ConfigInfo) SpringUtils.getBean("configInfo");
	Factory factory = new ConcreteFactory();// 工厂
	PropertiesUtil propertiesUtil = factory.getAllPerPropertiesUtil();
	Logger logger = Logger.getLogger(CommonFetch.class.getName());

	// 将字符串转换成Document对象
	public Document getPageDocument(String content) {
		return Jsoup.parse(content);
	}

	public String getHtmlContent(String url, String host) {
		if ("".equals(url) || null == url) {
			return "";
		}
		String htmlContent = "";
		try {
			Thread.sleep(3000);
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 90 * 1000);
			HttpConnectionParams.setSoTimeout(params, 90 * 1000);
			HttpClient httpClient = new DefaultHttpClient(params);
			HttpHost httpHost = new HttpHost(host);
			// 实例化验证
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			((DefaultHttpClient) httpClient)
					.setCredentialsProvider(credsProvider);
			// setProxyInfo((DefaultHttpClient) httpClient);
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
			HttpResponse response = httpClient.execute(httpHost, httpget);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				// 请求成功
				// 取得请求内容
				HttpEntity entity = response.getEntity();
				// 显示内容
				if (entity != null
						&& response.getStatusLine().getStatusCode() == 200) {
					// 转化为文本信息, 设置爬取网页的字符集，防止乱码
					htmlContent = EntityUtils.toString(entity, "UTF-8");
				}
				if (entity != null) {
					entity.consumeContent();
				}
				if (response.getStatusLine().getStatusCode() == 500
						|| response.getStatusLine().getStatusCode() == 502) {
					getHtmlContent(url, host);
				}
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("IOException，正在替换新IP");
			changeIP();
			getHtmlContent(url, host);
			logger.error("error" + e);
			logger.debug("debug");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("InterruptedException，正在替换新IP");
			changeIP();
			getHtmlContent(url, host);
			logger.error("error" + e);
			logger.debug("debug");
		}
		return htmlContent;
	}

	public PageBean getContentFromUrl(String url, String host, String sheBei) {
		boolean b = false;
		PageBean pageBean = null;
		if ("".equals(url) || null == url) {
			return null;
		}
		if ("ios".equals(sheBei)) {
			b = chechItunesLoadingbox(url, host);
			if (b) {
				pageBean = volidatePageBean(url, host, sheBei);
			} else {
				return null;
			}
		} else {
			pageBean = volidatePageBean(url, host, sheBei);
		}
		return pageBean;
	}

	/**
	 * 
	 * @param url
	 * @param host
	 *            目标域
	 * @return PageBean
	 * @see org.com.spider.bean.PageBean
	 */
	public PageBean volidatePageBean(String url, String host, String sheBei) {
		String content = null;
		Document contentDoc = null;
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 90 * 1000);
		HttpConnectionParams.setSoTimeout(params, 90 * 1000);
		// 实例化一个HttpClient
		HttpClient httpClient = new DefaultHttpClient(params);
		// 设定目标站点
		HttpHost httpHost = new HttpHost(host);
		// 实例化验证
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		((DefaultHttpClient) httpClient).setCredentialsProvider(credsProvider);
		// setProxyInfo((DefaultHttpClient) httpClient);
		// 目标地址
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
		// 执行
		try {
			Thread.sleep(3000);
			HttpResponse response = httpClient.execute(httpHost, httpget);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				// 请求成功
				// 取得请求内容
				HttpEntity entity = response.getEntity();
				// 显示内容
				if (entity != null
						&& response.getStatusLine().getStatusCode() == 200) {
					// 转化为文本信息, 设置爬取网页的字符集，防止乱码
					content = EntityUtils.toString(entity, "UTF-8");
					// 将content字符串转换成Document对象
					contentDoc = getPageDocument(content);
				}
				if (entity != null) {
					entity.consumeContent();
				}
				if (response.getStatusLine().getStatusCode() == 500) {
					getContentFromUrl(url, host, sheBei);
				}
			}
		} catch (SocketTimeoutException e) {
			// 处理超时，和请求忙相同
			System.out.println("Lam--->请求超时或者URL非法，正在替换新IP");
			changeIP();
			getContentFromUrl(url, host, sheBei);
			logger.error("error" + e);
			logger.debug("debug");
		} catch (HttpHostConnectException e) {
			// TODO: handle exception
			System.out.println("Lam--->服务器拒绝连接，正在替换新IP");
			changeIP();
			getContentFromUrl(url, host, sheBei);
			logger.error("error" + e);
			logger.debug("debug");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Lam--->ParseException，正在替换新IP");
			changeIP();
			getContentFromUrl(url, host, sheBei);
			logger.error("error" + e);
			logger.debug("debug");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Lam--->IO异常，正在替换新IP");
			changeIP();
			getContentFromUrl(url, host, sheBei);
			logger.error("error" + e);
			logger.debug("debug");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error" + e);
			logger.debug("debug");
		}
		if (null == content && null == contentDoc) {
			System.out.println("PageBean==Null，正在替换新IP");
			changeIP();
			getContentFromUrl(url, host, sheBei);
		}
		return new PageBean(content, contentDoc);
	}

	private boolean chechItunesLoadingbox(String url, String host) {
		// TODO Auto-generated method stub
		boolean b = false;
		String htmlContent = getHtmlContent(url, host);
		Document document = getPageDocument(htmlContent);
		if (document.getAllElements().hasClass("loadingbox")) {
			return b;
		} else {
			b = true;
		}
		return b;
	}

	/**
	 * 更换IP
	 */
	public void changeIP() {
		System.out.println("CommonFetch正在更换IP.....");
		String ipConfigPath = configInfo.getIp_Properties_Path();
		propertiesUtil = factory.getIpProperties(ipConfigPath);
		IPBean ipBean = factory.getIp(configInfo.getFile_ip_path());
		propertiesUtil.setValue("ip", ipBean.getAddress());
		propertiesUtil.setValue("post", ipBean.getHost());
		propertiesUtil.saveFile(ipConfigPath, "Lambda");
	}

	/**
	 * 设置代理
	 * 
	 * @param httpClient
	 */
	@SuppressWarnings("unused")
	private void setProxyInfo(DefaultHttpClient httpClient) {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		httpClient.setCredentialsProvider(credsProvider);
		HttpHost proxy = new HttpHost("127.0.0.1", 8087);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
	}

	/**
	 * 下载图片
	 * 
	 * @param imageUrl
	 *            图片地址
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static byte[] getBytesByURL(String imageUrl) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedInputStream bis = null;
		HttpURLConnection urlconnection = null;
		URL url = null;
		byte[] buf = new byte[2048];
		if ("".equals(imageUrl) || null == imageUrl) {
			return null;
		}
		try {
			Thread.sleep(3000);
			url = new URL(imageUrl);
			urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.connect();
			bis = new BufferedInputStream(urlconnection.getInputStream());
			for (int len = 0; (len = bis.read(buf)) != -1;) {
				baos.write(buf, 0, len);
			}
			baos.flush();
		} catch (InterruptedException e) { // TODO Auto-generated catch block
			getBytesByURL(imageUrl);
			e.printStackTrace();

		} catch (MalformedURLException e) { // TODO Auto-generated catch block
			getBytesByURL(imageUrl);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block getBytesByURL(imageUrl);
			e.printStackTrace();
		} finally {
			urlconnection = null;
			bis = null;
		}
		if (baos.toByteArray() == null) {
			return null;
		}
		return baos.toByteArray();
	}
}
