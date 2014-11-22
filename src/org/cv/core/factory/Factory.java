package org.cv.core.factory;

import org.cv.core.util.CommonFetch;
import org.cv.core.util.DataBaseCheckUtils;
import org.cv.core.util.GetUrlUtils;
import org.cv.core.util.PropertiesUtil;
import org.cv.core.util.ReadInputStreamUtil;
import org.cv.core.util.file.FileUtils;
import org.cv.core.util.file.FtpUtils;
import org.cv.model.IPBean;


/**
 * @classDescription: 一个大工厂负责所有对象的创建接口
 * @author:Lambda
 */
public interface Factory {
	public IPBean getIp(String filePath);
	public PropertiesUtil getIpProperties(String ipProPath);
	public CommonFetch getCommonFetch();
	public PropertiesUtil getAllPerPropertiesUtil();
	public PropertiesUtil getPropertiesUtil(String filePath);
	public DataBaseCheckUtils getDataBaseCheckUtils();
	public GetUrlUtils getUrlUtils();
	public FileUtils getFileUtils();
	public ReadInputStreamUtil getInputStreamUtil();
	public FtpUtils getFtpUtils();
	public String getCurrentDate();
}
