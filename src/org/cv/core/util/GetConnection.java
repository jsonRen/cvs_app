package org.cv.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.cv.core.factory.ConcreteFactory;
import org.cv.core.factory.Factory;

/**
 * @classDescription:
 * @author:Lambda
 */
public final class GetConnection {
	Factory factory = new ConcreteFactory();
	PropertiesUtil propertiesUtil = factory.getAllPerPropertiesUtil();

/*	private String name = "root";
	private String password = "root";
	private String url = "jdbc:mysql://localhost:3306/testapp?useUnicode=true&amp;characterEncoding=UTF-8";
	
	static String url = "jdbc:mysql://192.168.2.253:3306/cvsource?useUnicode=true&amp;characterEncoding=UTF-8";
	static String name = "root";
	static String password = "12345";*/
	
	
/*	static String url = "jdbc:mysql://119.254.11.249:3306/cvsource?useUnicode=true&amp;characterEncoding=UTF-8";
	static String name = "sjgx";
	static String password = "kVgxE6JzbdwyFqT7";*/
	static Connection conn = null;
	private static GetConnection jdbcUtilSingle = null;
	Logger logger = Logger.getLogger(GetConnection.class.getName());

	public static GetConnection getInitJDBCUtil() {
		if (jdbcUtilSingle == null) {
			// 给类加锁 防止线程并发
			synchronized (GetConnection.class) {
				if (jdbcUtilSingle == null) {
					jdbcUtilSingle = new GetConnection();
				}
			}
		}
		return jdbcUtilSingle;
	}

	private GetConnection() {
	}

	// 通过静态代码块注册数据库驱动，保证注册只执行一次
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");// 推荐使用方式
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 获得连接
	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(propertiesUtil.getValue("DB_URL"), propertiesUtil.getValue("DB_USER"), propertiesUtil.getValue("DB_PASS"));
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("error"+e);
			logger.debug("debug");
		}
		return conn;

	}

	// 关闭连接
	public void closeConnection(ResultSet rs, PreparedStatement pst,
			Connection con) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}