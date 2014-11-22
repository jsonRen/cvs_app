package org.cv.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

/**
 * @classDescription:负责处理字符串
 * @author:Lambda
 */
public class StringUtil {

	/**
	 * 
	 * @param str
	 * @return 返回中文
	 */
	public static String getChinese(String str) {
		String regex = "([\u4e00-\u9fa5]+)";
		Matcher matcher = Pattern.compile(regex).matcher(str);
		if (matcher.find()) {
			str = matcher.group();
		}
		return str;
	}

	public static String getNum(String str) {
		Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll("");
		return str;
	}

	public static String beijing_retunN_A(String source) {
		if (source.equals(" ") || source == null) {
			return "N/A";
		} else {
			return source;
		}
	}


	/**
	 * 把字符串转换成K/V
	 * 
	 * @param str
	 * @return
	 */
	public static List<Map<String, String>> getListMapString(String str) {
		List<Map<String, String>> _list = new ArrayList<Map<String, String>>();
		Map<String, String> _map = new HashMap<String, String>();
		String[] s = str.split(" ");
		if (s.length % 2 == 0) {
			_list.add(getResultMap(s));
		} else {
			String[] newArr = Arrays.copyOfRange(s, 0, s.length - 1);
			_map = getResultMap(newArr);
			_map.put(s[s.length - 1], " ");
		}
		return _list;
	}

	/**
	 * @param strArr
	 * @return 返回K/V
	 */
	public static Map<String, String> getResultMap(String[] strArr) {
		Map<String, String> _map = new HashMap<String, String>();
		for (int i = 0; i < strArr.length; i++) {
			_map.put(strArr[i], strArr[i + 1]);
			++i;
		}
		return _map;
	}

	/**
	 * 把中文转换成URLEncode编码
	 * @param str
	 * @return
	 */
	public static String returnURLEncode(String str) {
		String result = "";
		try {
			if(!str.equals("") && null !=str) {
				result = URLEncoder.encode(str, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param list
	 * @return 格式式List<Map<String,String>>
	 */
	public static String returnJsonString(List<Map<String, String>> list) {
		String jsonStr = JSONArray.fromObject(list).toString();
		return jsonStr;
	}

	/**
	 * 数据分组
	 * @param list
	 * @param param 分成几个组
	 * @return
	 */
	public static List<String> returnListGroup(List<String> list,int param) {
		List<String> _list = new ArrayList<String>();
		int len = list.size();
		int count = len/param;
		for(int i = 0;i<count;i++) {
			for(int j  = 0;j<param;j++) {
				_list.add(list.get(j+i*param));
			}
		}
		return _list;
	}

	/**
	 *  /var/www/task.cvsource.com.cn/UploadImages/appLogo/picture/ipad/1017/1414394574235.png
	  * getPicPath 方法
	  * <p>方法说明:根据线上服务器地址获得相对地址</p>
	  * @param str
	  * @return  picture/ipad/1017/1414394574235.png
	  * @author Lambda
	  * @date 2014-10-27
	 */
	public static String getPicPath(String str) {
		String regex = "picture.*";
		Matcher matcher = Pattern.compile(regex).matcher(str);
		if (matcher.find()) {
			str = matcher.group();
		}
		return str;
	}
}
