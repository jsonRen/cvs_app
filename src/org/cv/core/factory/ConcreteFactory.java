package org.cv.core.factory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.cv.core.util.CommonFetch;
import org.cv.core.util.DataBaseCheckUtils;
import org.cv.core.util.GetUrlUtils;
import org.cv.core.util.PropertiesUtil;
import org.cv.core.util.ReadInputStreamUtil;
import org.cv.core.util.file.FileUtils;
import org.cv.core.util.file.FtpUtils;
import org.cv.model.IPBean;


/**
 * @classDescription: 具体工厂类
 * @author:Lambda
 */
public class ConcreteFactory implements Factory {

	//读取ip配置文件
	@Override
	public PropertiesUtil getIpProperties(String ipProPath) {
		return new PropertiesUtil(ipProPath);
	}


	@Override
	public DataBaseCheckUtils getDataBaseCheckUtils() {
		// TODO Auto-generated method stub
		return new DataBaseCheckUtils();
	}

	@Override
	public GetUrlUtils getUrlUtils() {
		// TODO Auto-generated method stub
		return new GetUrlUtils();
	}

	@Override
	public FileUtils getFileUtils() {
		// TODO Auto-generated method stub
		return new FileUtils();
	}

	@Override
	public ReadInputStreamUtil getInputStreamUtil() {
		// TODO Auto-generated method stub
		return new ReadInputStreamUtil();
	}



	@Override
	public FtpUtils getFtpUtils() {
		// TODO Auto-generated method stub
		FtpUtils ftpUtils = null;
		try { 
			ftpUtils =  new FtpUtils();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ftpUtils;
	}

	@Override
	public PropertiesUtil getAllPerPropertiesUtil() {
		// TODO Auto-generated method stub
		//String allPropertiesPath = "/home/app/resources/quanJu.properties";
		String allPropertiesPath = "D:/App/run/resources/quanJu.properties";
		return new PropertiesUtil(allPropertiesPath);
	}

	@Override
	public CommonFetch getCommonFetch() {
		// TODO Auto-generated method stub
		return new CommonFetch();
	}

	@Override
	public String getCurrentDate() {
		// TODO Auto-generated method stub
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return date;
	}


	@Override
	public IPBean getIp(String filePath) {
		// TODO Auto-generated method stub
		IPBean ipBean = new IPBean();
		ReadInputStreamUtil inputStreamUtil = new ReadInputStreamUtil();
		// TODO Auto-generated method stub
		List<String> list = inputStreamUtil.readTxt(filePath);
		Random random = new Random();
		String str = list.get(random.nextInt(list.size()));
		String ad = str.substring(0, str.indexOf(":"));
		String host = str.substring(str.indexOf(":") + 1, str.length());
		ipBean.setAddress(ad);
		ipBean.setHost(host);
		return ipBean;
	}


	@Override
	public PropertiesUtil getPropertiesUtil(String filePath) {
		// TODO Auto-generated method stub
		return null;
	}


}
