package org.cv.core.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @classDescription: 数据库校验
 * @author:Lambda
 */
public class DataBaseCheckUtils {
	private static GetConnection connection = GetConnection.getInitJDBCUtil();
	private static Connection conn = null;
	private static PreparedStatement pst = null;
	private static ResultSet rs = null;

	Logger logger = Logger.getLogger(DataBaseCheckUtils.class.getName());

	
	/**
	 * 
	 * @param deviceType
	 *            设备标识
	 * @param map
	 *            解析到的数据和数据库匹配
	 * @return
	 */
	public String getDataBaseValue(String deviceType, Map<String, String> map) {
		// TODO Auto-generated method stub
		String app_id = "";
		if (null == map || map.isEmpty()) {
			return app_id;
		}

		// deviceType map.get("appName")
		try {
			String sql = "select App_ID from tbl_app_info where Device_Type= ? and ( Remark = ?  OR App_Name = ?  ) limit 1";
			conn = connection.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, deviceType);
			pst.setString(2, map.get("appName"));
			pst.setString(3, map.get("appName"));
			rs = pst.executeQuery();
			while (rs.next()) {
				app_id = rs.getString("App_ID");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			logger.error("error"+e);
			logger.debug("debug");
		} finally {
			connection.closeConnection(rs, pst, conn);
		}
		return app_id;
	}

	/**
	 * 
	 * checkRank 方法 存在返回true，不存在返回false
	 * <p>
	 * 方法说明:
	 * </p>
	 * 
	 * @param map
	 * @return
	 * @return boolean
	 * @author Lambda
	 * @date 2014-10-19
	 */
	public boolean checkRank(Map<String, String> map) {
		// TODO Auto-generated method stub
		// deviceType map.get("appName")
		if (map.get("appId").equals("") || map.get("appType").equals("")
				|| map.get("rankData").equals("")) {
			return false;
		}
		try {
			String sql = "select App_ID from tbl_app_rank where App_ID= ? and Rank_Date=? and Rank_App_Type=?";
			conn = connection.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, map.get("appId"));
			pst.setString(2, map.get("rankData"));
			pst.setString(3, map.get("rankAppType"));
			rs = pst.executeQuery();
			String app_ID = "";
			while (rs.next()) {
				app_ID = rs.getString("App_ID");
			}
			if (!app_ID.equals("") && app_ID != null) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// TODO: handle exception
			logger.error("error"+e);
			logger.debug("debug");
		} finally {
			connection.closeConnection(rs, pst, conn);
		}
		return true;
	}

	public boolean checkAndroidRank(Map<String, String> map) {
		// TODO Auto-generated method stub
		// deviceType map.get("appName")
		if (map.get("appId").equals("") || map.get("appType").equals("")
				|| map.get("rankData").equals("")
				|| map.get("rankAppType").equals("")) {
			return false;
		}
		try {
			String sql = "select App_ID from tbl_app_rank where App_ID= ? and Rank_Date=? and Rank_App_Type=? and App_List_Type=?";
			conn=connection.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, map.get("appId"));
			pst.setString(2, map.get("rankData"));
			pst.setString(3, map.get("appType"));
			pst.setString(4, map.get("rankAppType"));
			rs = pst.executeQuery();
			String app_ID = "";
			while (rs.next()) {
				app_ID = rs.getString("App_ID");
			}
			if (!app_ID.equals("") && app_ID != null) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			logger.error("error"+e);
			logger.debug("debug");
		} finally {
			connection.closeConnection(rs, pst, conn);
		}
		return true;
	}

	public static String getLogoPath(String appId) {
		// TODO Auto-generated method stub
		String logoPath = "";
		try {
			String sql = "select Logo_Path from tbl_app_info where App_ID= ?";
			conn= connection.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, appId);
			rs = pst.executeQuery();
			while (rs.next()) {
				logoPath = rs.getString("Logo_Path");
			}
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			connection.closeConnection(rs, pst, conn);
		}
		return logoPath;
	}
}
