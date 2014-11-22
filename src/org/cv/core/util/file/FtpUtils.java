package org.cv.core.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @classDescription:
 * @author:Lambda
 */
public class FtpUtils {
	private FTPClient ftp;

	public FtpUtils() throws Exception {
		connect("/usr/local/tomcat6/webapps/cvs_enterprise/images/appLogo/",
				"192.168.2.254", "root", "123456");
	}
	/**
	 * 
	 * @param path
	 *            上传到ftp服务器哪个路径下
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	private boolean connect(String path, String addr, String username,
			String password) throws Exception {
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr);
		ftp.login(username, password);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}
		ftp.changeWorkingDirectory(path);
		result = true;
		return result;
	}

	/**
	 * 
	 * @param file
	 *            上传的文件或文件夹
	 * @throws Exception
	 */
	public void upload(File file) {
		try {
			if (file.isDirectory()) {
				ftp.makeDirectory(file.getName());
				ftp.changeWorkingDirectory(file.getName());
				String[] files = file.list();
				for (int i = 0; i < files.length; i++) {
					File file1 = new File(file.getPath() + "\\" + files[i]);
					if (file1.isDirectory()) {
						upload(file1);
						ftp.changeToParentDirectory();
					} else {
						File file2 = new File(file.getPath() + "\\" + files[i]);
						FileInputStream input = new FileInputStream(file2);
						ftp.storeFile(file2.getName(), input);
						input.close();
					}
				}
			} else {
				File file2 = new File(file.getPath());
				FileInputStream input = new FileInputStream(file2);
				ftp.storeFile(file2.getName(), input);
				input.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
