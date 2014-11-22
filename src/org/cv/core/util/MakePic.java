package org.cv.core.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;



/**
 * @classDescription:
 * @author:Lambda
 */
public class MakePic  implements Runnable {
	private static GetConnection jdbcUtilSingle = GetConnection.getInitJDBCUtil();
	private static Connection conn =null;
	private static PreparedStatement pst = null;
	private static ResultSet rs = null;
	private static final String RANK_PIC_PATH = "/home/app/UploadImages/rankPic/";
	private static final String REAL_RANK_PIC_PATH = "/rankPic/";
	Logger logger = Logger.getLogger(GetConnection.class.getName());

	@Override
	public void run() {
		// TODO Auto-generated method stub
		makePic();
	}
	/**
	 * 
	 * @param deviceType 设备标识
	 * @param map 解析到的数据和数据库匹配
	 * @return
	 */
	public static List<Map<String, Object>>  getAllAppID() {
		// TODO Auto-generated method stub
		String sql = "SELECT a.App_Id, a.Sys_Type, a.App_Type FROM tbl_app_info a";
		List<Map<String, Object>> App_ID_list = new ArrayList<Map<String,Object>>();
		Map<String,Object> _map =null;
		try {
			conn = jdbcUtilSingle.getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			String appid = "";
			String sys_type= "";
			String app_type = "";
			while(rs.next()) {
				_map = new HashMap<String,Object>();
				appid = rs.getString("App_ID");
				sys_type = rs.getString("Sys_Type");
				app_type = rs.getString("App_Type");
				_map.put("App_Id", appid);
				_map.put("Sys_Type", sys_type);
				_map.put("App_Type", app_type);
				App_ID_list.add(_map);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			jdbcUtilSingle.closeConnection(rs, pst, conn);
		}
		return App_ID_list;
	}
	
	public void updateRankPicPath(Map<String,Object> _map) {
		String sql = "UPDATE tbl_app_info a SET a.Rank_Pic_Path = ?  WHERE App_Id = ?";
		try {
			conn = jdbcUtilSingle.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, _map.get("rankPicPath")+"");
			pst.setString(2, _map.get("appId")+"");
			int i = pst.executeUpdate();
			if (i > 0) {
				System.out.println("成功更新数据");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error"+e);
			logger.debug("debug");
		} finally {
			jdbcUtilSingle.closeConnection(rs, pst, conn);
		}
	}
	
	public static List<Map<String, Object>>  getListAppsNearMonthAppRank(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String sql = "SELECT a.Id, a.Rank, a.Rank_Date, (SELECT b.Name FROM tbl_app_type_dic b WHERE b.Code = a.Rank_App_Type ) AS App_Type, a.Rank_App_Type, a.App_ID FROM tbl_app_rank a WHERE a.Rank_Date BETWEEN DATE_ADD(NOW(), INTERVAL - 30 DAY) AND NOW() AND a.Rank_App_Type = ? AND a.App_ID = ?  ORDER BY a.Rank_Date ASC";
		List<Map<String, Object>> App_ID_list = new ArrayList<Map<String,Object>>();
		Map<String,Object> _map = null;
		try {
			conn = jdbcUtilSingle.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, params.get("rankAppType")+"");
			pst.setString(2, params.get("appId")+"");
			rs = pst.executeQuery();
			String id = "";
			String rank= "";
			String rank_Date = "";
			String app_type = "";
			String rank_app_type = "";
			String app_id = "";
			while(rs.next()) {
				 _map = new HashMap<String,Object>();
				id = rs.getString("Id");
				rank = rs.getString("Rank");
				rank_Date = rs.getString("Rank_Date");
				app_type = rs.getString("App_Type");
				rank_app_type = rs.getString("Rank_App_Type");
				app_id = rs.getString("App_ID");
				_map.put("Id", id);
				_map.put("Rank", rank);
				_map.put("Rank_Date", rank_Date);
				_map.put("App_Type", app_type);
				_map.put("Rank_App_Type", rank_app_type);
				_map.put("App_ID", app_id);
				App_ID_list.add(_map);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			jdbcUtilSingle.closeConnection(rs, pst, conn);
		}
		return App_ID_list;
	}
	
	
	public void getAllApp() throws Exception {
		// 统计之前先删上周的
		File file = new File(RANK_PIC_PATH);
		this.deleteAllFilesOfDir(file);

		/*
		 * SELECT a.App_Id, a.Sys_Type, a.App_Type FROM tbl_app_info a
		 */
		List<Map<String, Object>> App_ID_list = getAllAppID();

		Map<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < App_ID_list.size(); i++) {
			params.put("appId", App_ID_list.get(i).get("App_Id"));
			params.put("rankAppType", App_ID_list.get(i).get("App_Type"));
			// if(App_ID_list.get(i).get("App_Type").equals("1000") ||
			// App_ID_list.get(i).get("App_Type").equals("2001")){
			// //过滤掉全部类别
			// continue;
			// }
			// 该类别近一个月排名集合
			/*
			 * SELECT a.Id, a.Rank, a.Rank_Date, (SELECT b.Name FROM
			 * tbl_app_type_dic b WHERE b.Code = a.Rank_App_Type ) AS App_Type,
			 * a.Rank_App_Type, a.App_ID FROM tbl_app_rank a WHERE a.Rank_Date
			 * BETWEEN DATE_ADD(NOW(), INTERVAL - 30 DAY) AND NOW() AND
			 * a.Rank_App_Type = #{rankAppType} AND a.App_ID = #{appId} ORDER BY
			 * a.Rank_Date DESC
			 */
			List<Map<String, Object>> app_Rank = null;
			List<Map<String, Object>> all_Rank = null;

			if (App_ID_list.get(i).get("App_Type").equals("1000")
					|| App_ID_list.get(i).get("App_Type").equals("2001")) {
				all_Rank = getListAppsNearMonthAppRank(params);
			} else {
				app_Rank = getListAppsNearMonthAppRank(params);
				params.clear();
				// 全部类别
				params.put("appId", App_ID_list.get(i).get("App_Id"));
				if (App_ID_list.get(i).get("Sys_Type").equals("ios")) {
					// IOS全部类别
					params.put("rankAppType", "1000");
				} else {
					// ANDROID全部类别
					params.put("rankAppType", "2001");
				}
				// 如果该APP有全部类别返回集合
				all_Rank = getListAppsNearMonthAppRank(params);
				params.clear();
			}

			if ((null != app_Rank && app_Rank.size() > 0)
					|| (null != all_Rank && all_Rank.size() > 0)) {
				// 生成图片
				this.createChart(app_Rank, all_Rank);
			}
		}

	}

	public DefaultCategoryDataset createDataset(
			List<Map<String, Object>> appRankList,
			List<Map<String, Object>> allRankList) {

		DefaultCategoryDataset linedataset = new DefaultCategoryDataset();

		// 曲线名称
		String series_All = "所有类别名称"; // series指的就是报表里的那条数据线

		if (null != appRankList && appRankList.size() > 0) {

			String series_Type = String.valueOf(appRankList.get(0).get(
					"App_Type"))
					+ "类别名称"; // series指的就是报表里的那条数据线
			for (int i = 0; i < appRankList.size(); i++) {

				linedataset.addValue(Integer.parseInt(String
						.valueOf(appRankList.get(i).get("Rank"))), // 值
						series_Type, // 哪条数据线
						String.valueOf(appRankList.get(i).get("Rank_Date"))); // 对应的横轴
			}

		}
		if (null != allRankList && allRankList.size() > 0) {

			for (int i = 0; i < allRankList.size(); i++) {

				linedataset.addValue(Integer.parseInt(String
						.valueOf(allRankList.get(i).get("Rank"))), // 值

						series_All, // 哪条数据线

						String.valueOf(allRankList.get(i).get("Rank_Date"))); // 对应的横轴

			}
		}
		return linedataset;

	}

	public void createChart(List<Map<String, Object>> appRankList,
			List<Map<String, Object>> allRankList) throws Exception {
		try {
			// 定义图标对象
			JFreeChart chart = ChartFactory.createLineChart(null,// 报表题目，字符串类型
					"时间", // 横轴

					"排名", // 纵轴

					this.createDataset(appRankList, allRankList), // 获得数据集

					PlotOrientation.VERTICAL, // 图标方向垂直

					true, // 显示图例

					false, // 不用生成工具

					false // 不用生成URL地址

					);

			// 整个大的框架属于chart 可以设置chart的背景颜色

			// 生成图形

			CategoryPlot plot = chart.getCategoryPlot();

			// 图像属性部分

			plot.setBackgroundPaint(Color.white);

			plot.setDomainGridlinesVisible(true); // 设置背景网格线是否可见

			plot.setDomainGridlinePaint(Color.BLACK); // 设置背景网格线颜色

			plot.setRangeGridlinePaint(Color.GRAY);

			plot.setNoDataMessage("没有数据");// 没有数据时显示的文字说明。

			// 数据轴属性部分

			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

			rangeAxis.setAutoRangeIncludesZero(true); // 自动生成

			rangeAxis.setUpperMargin(0.03D);

			rangeAxis.setLabelAngle(Math.PI / 2.0);

			rangeAxis.setAutoRange(false);

			rangeAxis.setInverted(true); // y轴翻转

			rangeAxis.setLowerBound(-20);

			rangeAxis.setTickUnit(new NumberTickUnit(100));

			ValueAxis valueAxis = plot.getRangeAxis();

			valueAxis.setUpperMargin(0.20D);
			valueAxis.setUpperBound(200D);

			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
					.getRenderer();

			renderer.setBaseItemLabelsVisible(true);

			renderer.setSeriesPaint(0, Color.blue); // 设置折线的颜色

			renderer.setBaseShapesFilled(true);

			renderer.setBaseItemLabelsVisible(true);

			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(

			ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));

			renderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

			renderer.setBaseItemLabelFont(new Font("Dialog", 1, 14)); // 设置提示折点数据形状

			plot.setRenderer(renderer);

			String path = "";
			String realPath ="";
			// 创建文件输出流
			if(null==appRankList && null==allRankList){
				return;
			}else{
				if(null!=appRankList && appRankList.size()>0){
					path = RANK_PIC_PATH+appRankList.get(0).get("Rank_App_Type")+"/"+
					appRankList.get(0).get("App_ID")+"_"+appRankList.get(0).get("Rank_App_Type")
					+"_"+appRankList.get(0).get("Rank_Date")+"rankPic.jpg";
					realPath = REAL_RANK_PIC_PATH +appRankList.get(0).get("Rank_App_Type")+"/"+
					appRankList.get(0).get("App_ID")+"_"+appRankList.get(0).get("Rank_App_Type")
					+"_"+appRankList.get(0).get("Rank_Date")+"rankPic.jpg";
					
					File f = new File(RANK_PIC_PATH);
					if(!f.exists()){
						f.mkdirs();
					}
					
					File appType_Path = new File(RANK_PIC_PATH+appRankList.get(0).get("Rank_App_Type"));
					if(!appType_Path.exists()){
						appType_Path.mkdir();
					}
					
					File fos_jpg = new File(path);


					// 输出到哪个输出流

					ChartUtilities.saveChartAsJPEG(fos_jpg, chart, // 统计图表对象

							700, // 宽

							500 // 高

							);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("appId", appRankList.get(0).get("App_ID"));
					params.put("rankPicPath",realPath);
					//根据appId更新APPINFO表
					/*
					    UPDATE 
						    tbl_app_info a 
						SET 
							a.Rank_Pic_Path = #{rankPicPath}
						WHERE 
							App_Id = #{appId}	
					 */
					updateRankPicPath(params);
					
				}else{
					if(null!=allRankList && allRankList.size()>0){
						path = RANK_PIC_PATH+allRankList.get(0).get("Rank_App_Type")+"/"+
						allRankList.get(0).get("App_ID")+"_"+allRankList.get(0).get("Rank_App_Type")
						+"_"+allRankList.get(0).get("Rank_Date")+"rankPic.jpg";
						
						realPath = REAL_RANK_PIC_PATH+allRankList.get(0).get("Rank_App_Type")+"/"+
						allRankList.get(0).get("App_ID")+"_"+allRankList.get(0).get("Rank_App_Type")
						+"_"+allRankList.get(0).get("Rank_Date")+"rankPic.jpg";
						
						File f = new File(RANK_PIC_PATH);
						if(!f.exists()){
							f.mkdirs();
						}
						
						File appType_Path = new File(RANK_PIC_PATH+allRankList.get(0).get("Rank_App_Type"));
						if(!appType_Path.exists()){
							appType_Path.mkdir();
						}
						
						
						File fos_jpg = new File(path);
	
						// 输出到哪个输出流
	
						ChartUtilities.saveChartAsJPEG(fos_jpg, chart, // 统计图表对象
	
								700, // 宽
	
								500 // 高
								);
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("appId", allRankList.get(0).get("App_ID"));
						params.put("rankPicPath", realPath);
						//根据appId更新APPINFO表
						updateRankPicPath(params);
					}else{
						return;
					}
				}
				
			
			}


		} catch (IOException e) {
			logger.error("error"+e);
			logger.debug("debug");
			e.printStackTrace();

		}

	}

	private void deleteAllFilesOfDir(File path) {
		if (!path.exists())
			return;
		if (path.isFile()) {
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteAllFilesOfDir(files[i]);
		}
		path.delete();
	}
	
	public void deleteApp_rank_temp() {
		String sql = "delete from tbl_app_rank_temp";
		try {
			conn = jdbcUtilSingle.getConnection();
			pst = conn.prepareStatement(sql);
			int i = pst.executeUpdate();
			if(i>0) {
				System.out.println("tbl_app_rank_temp------------>成功删除");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error"+e);
			logger.debug("debug");
		} finally {
			jdbcUtilSingle.closeConnection(rs, pst, conn);
		}
	}
	
	
	//飙升榜
	public void biaoShengPaiMing() {
		String sql = "INSERT INTO tbl_app_rank_temp(App_ID,App_Name,This_Rank,Week_Ago_Rank,Min_Rank,Logo_Path,Rank_App_Type,App_Type_Name,"
			+
			 "Sys_Type,Device_Type,Publisher,Create_Time,Update_Time,Rank_Date,Rank_Type)SELECT	y.App_ID,y.App_Name,z.Rank AS this_Rank,"
			+
			 "w.App_Rank AS Week_Ago_Rank,(w.App_Rank - z.Rank)AS MIN_RANK,y.Logo_Path,z.Rank_App_Type,(SELECT x. NAME FROM "
			+
			 "tbl_app_type_dic x WHERE x. CODE = y.App_Type )AS App_Type_Name,y.Sys_Type,y.Device_Type,y.Publisher,y.Create_Time,"
			+
			 "y.Update_Time,z.Rank_Date,'1' FROM tbl_app_rank z INNER JOIN tbl_app_info y ON z.App_ID = y.App_ID INNER " 
			+
			 "JOIN(SELECT c.App_ID,c.App_Name,c.Logo_Path,c.Enterprise_ID AS id,a.Rank_App_Type,c.App_Type,"
			+
			 "(SELECT b. NAME FROM tbl_app_type_dic b WHERE b. CODE = c.App_Type )AS App_Type_Name,c.Sys_Type,c.Device_Type,c.Publisher"
			+
			 ",c.Is_Enable,c.Create_Time,c.Update_Time,c.Behavior,c.Remark,a.Rank AS App_Rank,a.Rank_Date FROM tbl_app_rank a INNER JOIN "
			+
			  "tbl_app_info c ON a.App_ID = c.App_ID WHERE 1 = 1 AND a.Rank_Date = (SELECT DISTINCT(a.Rank_Date) FROM tbl_app_rank a ORDER BY a.Rank_Date DESC limit 1,1)  " 
			+
			  " ORDER BY a.Rank ASC)w ON z.App_ID = w.App_ID AND z.Rank_App_Type = w.Rank_App_Type "
			+
			  "WHERE 1 = 1 AND z.Rank_Date = (SELECT DISTINCT(a.Rank_Date) FROM tbl_app_rank a ORDER BY a.Rank_Date DESC limit 0,1) ORDER BY z.App_ID ASC ";
			try {
				conn = jdbcUtilSingle.getConnection();
				pst = conn.prepareStatement(sql);
				int i = pst.executeUpdate();
				if(i>1) {
					System.out.println("bangsheng----->成功");
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("error"+e);
				logger.debug("debug");
			}
    }
	public void xinruiBang() {
		String sql = "INSERT INTO tbl_app_rank_temp(App_ID,App_Name,This_Rank,Week_Ago_Rank,Min_Rank,Logo_Path,Rank_App_Type,App_Type_Name,"
			+
			"Sys_Type,Device_Type,Publisher,Create_Time,Update_Time,Rank_Date,Rank_Type)SELECT y.App_ID,y.App_Name,z.Rank AS App_Rank,"
			+
			"NULL,NULL,y.Logo_Path,z.Rank_App_Type,(SELECT x. NAME FROM tbl_app_type_dic x WHERE x.CODE = y.App_Type) AS App_Type_Name,"
			+
			"y.Sys_Type,y.Device_Type,y.Publisher,y.Create_Time,y.Update_Time,z.Rank_Date,'2'FROM tbl_app_rank z INNER JOIN "
			+
			 "tbl_app_info y ON z.App_ID = y.App_ID WHERE 1=1 AND z.Rank_Date = (SELECT DISTINCT(a.Rank_Date) FROM tbl_app_rank a ORDER BY a.Rank_Date DESC limit 0,1) " 
			+
			 "AND z.App_ID NOT IN(SELECT  t.App_ID FROM (SELECT c.App_ID FROM tbl_app_rank a INNER JOIN tbl_app_info c ON " 
			+
			 "a.App_ID = c.App_ID WHERE 1 = 1 AND a.Rank_Date = (SELECT DISTINCT(a.Rank_Date) FROM tbl_app_rank a ORDER BY a.Rank_Date DESC limit 1,1) "  
			+
			 " ORDER BY a.Rank ASC)t)ORDER BY z.Rank ASC";
		try {
			conn = jdbcUtilSingle.getConnection();
			pst = conn.prepareStatement(sql);
			int i = pst.executeUpdate();
			if(i>0) {
				System.out.println(" xinrui---->成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error"+e);
			logger.debug("debug");
		} finally {
			jdbcUtilSingle.closeConnection(rs, pst, conn);
		}
	}
	
	
	public static void makePic() {
		MakePic makePic = new MakePic();		
		try {
			makePic.deleteApp_rank_temp();
			makePic.biaoShengPaiMing();
			makePic.xinruiBang();
			makePic.getAllApp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String arg[]){
		new Thread(new MakePic()).start();
	}
}
