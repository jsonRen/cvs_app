package org.cv.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;
import org.cv.core.spring.SpringUtils;
import org.cv.model.AppInfo;
import org.cv.model.ConfigInfo;

/**
 * @classDescription:操作I/O流
 * @author:Lambda
 */
public class ReadInputStreamUtil {
	ConfigInfo configInfo = null;
	Factory factory = null;
	private List<String> txt = new ArrayList<String>();
	Logger logger = Logger.getLogger(ReadInputStreamUtil.class);

	public ReadInputStreamUtil() {
		configInfo = (ConfigInfo) SpringUtils.getBean("configInfo");
		factory = new ConcreteFactory();
	}
	/**
	 * 读取整个文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 */
	public String readFileContent(String filePath) {
		File file = new File(filePath);
		BufferedReader bf;
		StringBuilder sb = new StringBuilder();
		try {
			bf = new BufferedReader(new FileReader(file));
			String content = "";
			while (content != null) {
				content = bf.readLine();
				if (content == null) {
					break;
				}
				sb.append(content.trim());
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("error"+e);
			logger.debug("debug");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error"+e);
			logger.debug("debug");
		}
		return sb.toString();
	}

	/**
	 * 按行读取放入List，例如读取一行：192.168.0.1:8080
	 * 
	 * @param filePath
	 * @return
	 */
	public List<String> readTxt(String filePath) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					txt.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			logger.error("error"+e);
			logger.debug("debug");
			e.printStackTrace();
		}
		return txt;
	}

	/**
	 * 写入文件
	 * 
	 * @param path
	 */
	public void write(String path) {
		String encoding = "UTF-8";
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(path), encoding));
			for (int i = 0; i < txt.size(); i++) {
				pw.println(txt.get(i));
				pw.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("error"+e);
			logger.debug("debug");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("error"+e);
			logger.debug("debug");
			e.printStackTrace();
		}
	}

	public void remove(int index) {
		txt.remove(index);
	}

	public List<String> getStringInputStream(String str) {
		List<String> _list = new ArrayList<String>();
		try {
			if (str != null && !str.equals("")) {
				InputStreamReader reader = new InputStreamReader(
						new ByteArrayInputStream(str.getBytes()));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					_list.add(lineTxt);
				}
				reader.close();
				return _list;
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("IO异常");
		}
		return null;
	}

	public boolean create(String content, String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			System.out.println("文件已存在！"+file.getAbsolutePath());
			return false;
		}
		if (filePath.endsWith(File.separator)) {
			System.out.println("文件不能为目录！");
			return false;
		}
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");
				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				System.out.println("创建文件" + filePath + "成功！");
				fetch(filePath, content);
				System.out.println("写入文件成功");
				return true;
			} else {
				System.out.println("创建文件" + filePath + "失败！");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建文件" + filePath + "失败！" + e);
			logger.error("error"+e);
			logger.debug("debug");
			return false;
		}
	}

	private void fetch(String path, String content) {
		// TODO Auto-generated method stub
		String encoding = "UTF-8";
		try {
			OutputStreamWriter outs = new OutputStreamWriter(
					new FileOutputStream(path, true), encoding);
			outs.write(content);
			outs.close();  
		} catch (IOException e) {
			logger.error("error"+e);
			logger.debug("debug");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param img
	 *            图片流
	 * @param _map
	 *            定位文件存储路径
	 * @return 图片实际存储路径
	 */
	public String writeImageToDis(byte[] img, AppInfo appInfo) {
		if(null==img || img.length<0) {
			return "";
		}
		String geShi = appInfo.getLogoPath();
		String appName = System.currentTimeMillis()+"";
		if(!"".equals(geShi)) {
			geShi = geShi.substring(geShi.lastIndexOf("."), geShi.length());
		}
		File file = null;
		String localPath =configInfo.getPic_path() + appInfo.getSysType() + "/"+appInfo.getAppType()+ "/" + appName + geShi;
		System.out.println(localPath);
		try {
			file = new File(localPath);
			if (file.exists()) {
				System.out.println("文件已存在");
			}
			if (!file.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建父目录
				if (!file.getParentFile().mkdirs()) {
					System.out.println("创建目标文件所在目录失败！");
				}
			}
			if (file.createNewFile()) {
				System.out.println("创建文件" + file.getAbsolutePath() + "成功！");
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(img);
				fileOutputStream.flush();
				fileOutputStream.close();
				System.out.println("写入文件成功");
			} else {
				System.out.println("创建文件" + file.getAbsolutePath() + "失败！");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建文件" + file.getAbsolutePath() + "失败！"
					+ e);
		}
		System.out.println("文件已成功下载，路径为：" + file.getAbsolutePath());
		localPath = StringUtil.getPicPath(localPath);
		return localPath;
	}
	
	
	
	
	public String writeIpadImageToDis(byte[] img, String logoName,String appType) {
		if(null==img || img.length<0) {
			return "";
		}
		String appName = logoName;
		File file = null;
		String localPath = configInfo.getPic_path()+appType+ "/" + appName;
		System.out.println(localPath);
		try {
			file = new File(localPath);
			if (file.exists()) {
				System.out.println("文件已存在");
			}
			if (!file.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建父目录
				if (!file.getParentFile().mkdirs()) {
					System.out.println("创建目标文件所在目录失败！");
				}
			}
			if (file.createNewFile()) {
				System.out.println("创建文件" + file.getAbsolutePath() + "成功！");
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(img);
				fileOutputStream.flush();
				fileOutputStream.close();
				System.out.println("写入文件成功");
			} else {
				System.out.println("创建文件" + file.getAbsolutePath() + "失败！");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建文件" + file.getAbsolutePath() + "失败！"
					+ e);
			logger.error("error"+e);
			logger.debug("debug");
		}
		System.out.println("文件已成功下载，路径为：" + file.getAbsolutePath());
		localPath = StringUtil.getPicPath(localPath);
		return localPath;
	}
	
	
	
	
	/**
	 * 
	 * @param path 服务器上相对地址
	 * @param filename 文件名+后缀 Map<String,String>参数
	 * @param input +inputStream流
	 * @return
	 */
	public String uploadFile( byte[] img,Map<String,String> map) {
		InputStream input = new ByteArrayInputStream(img);  
		String path = "/home/app/UploadImages/appLogo/picture";//服务器相对地址
		String geShi = map.get("logoUrl");
		String appName = System.currentTimeMillis()+"";
		if(!"".equals(geShi)) {
			geShi = geShi.substring(geShi.lastIndexOf("."), geShi.length());
		}
		path = path + "/"+map.get("shebei") + "/"+map.get("appType")+"/" + appName + geShi;
		geShi = geShi.substring(geShi.lastIndexOf("."), geShi.length());
		String imgFile = appName+geShi;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect("192.168.2.254");//连接FTP服务器
			ftp.login("root", "123456");//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return path;
			}
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.makeDirectory(path);
			ftp.changeWorkingDirectory(path);
			ftp.storeFile(imgFile, input);
			input.close();
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return path;
	}
	
	public static void main(String[] args) {
		
	}
}
