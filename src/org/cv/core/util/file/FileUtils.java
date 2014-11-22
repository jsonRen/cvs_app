package org.cv.core.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * @classDescription:
 * @author:Lambda
 */
public class FileUtils {
	List<String> fileList = new ArrayList<String>();
	Logger logger = Logger.getLogger(FileUtils.class.getName());

	/**
	 * 获得文件路径列表
	 * @param filePath
	 * @return 
	 */
	public List<String> getFileList(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		try {
			for(File file : files) {
				if(file.isDirectory()) { 
					getFileList(file.getAbsolutePath());//递归调用
				} else {
					fileList.add(file.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error"+e);
			logger.debug("debug");
		}
		return fileList;
	}
	
	public static void main(String[] args) {
		List<String> list = new FileUtils().getFileList("D:\\App\\android\\list\\2014-10-10");
		for(int i =0;i<list.size();i++) {
			System.out.println(list.get(i)+"     "+i);
		}
	}
}
