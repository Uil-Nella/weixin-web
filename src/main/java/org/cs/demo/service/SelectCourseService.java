package org.cs.demo.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.cs.demo.pojo.Course;
import org.cs.demo.util.WeekNumUtil;

import com.google.gson.Gson;


public class SelectCourseService {
	/**
	 * 发起http请求获取返回结果
	 * 
	 * @param requestUrl 请求地址
	 * @return
	 */
	public static String httpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (Exception e) {
		}
		return buffer.toString();
	}
	
	/**
	 * utf编码
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String select(String source) {
		String dst = null;

		// 组装查询地址
		String requestUrl = "http://182.92.79.92/NEUQ_Helper/couaction!select_courseByClassNameAndWeek.action?cl_classname={a}&cou_week={b}&weeknum={c}";
		// 对参数q的值进行urlEncode utf-8编码
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if(week == 1){
			week = 7;
		}else{
			week--;
		}
		String weeknum = WeekNumUtil.getWeekNum("2014-03-03", System.currentTimeMillis());
		requestUrl = requestUrl.replace("{a}", urlEncodeUTF8(source));
		requestUrl = requestUrl.replace("{b}", week+"");
		requestUrl = requestUrl.replace("{c}", weeknum);
//		requestUrl = requestUrl.replace("{a}", urlEncodeUTF8(source));
//		requestUrl = requestUrl.replace("{b}", "2");
//		requestUrl = requestUrl.replace("{c}", "4");

		// 查询并解析结果
		try {
			// 查询并获取返回结果
			String json = httpRequest(requestUrl);
			int end = 0;
			if(json.contains("今天没有课")){
				dst = "今天没有课";
			}else if(json.contains("数据库中没有您所在的班级")){
				dst = "学号输入有误！请重新输入！";
			}else{
				end = json.indexOf("flag");
				String str = json.substring(12, end-3);
				str = str.replaceAll("\\\\","");
				JSONArray array = JSONArray.fromObject(str);        
		        Object[] obj = new Object[array.size()];        
		        for(int i = 0; i < array.size(); i++){        
		            JSONObject jsonObject = array.getJSONObject(i);        
		            obj[i] = JSONObject.toBean(jsonObject, Course.class);        
		        }        
		        StringBuffer sb = new StringBuffer();
		        sb.append("今天课表：").append("\n");
		        for(Object o :obj){
		        	Course c = (Course) o;
		        	sb.append(String.valueOf(Character.toChars(0x1F514))).append("第").append(c.getCou_num()).append("节课").append("\n");
		        	sb.append(c.getCou_name()).append("\n");
		        	sb.append(c.getCla_classroomname()).append("\n");
		        	sb.append(c.getTea_name()).append("\n");
		        }
				dst = sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			dst = "查询课表出错，请重新查询";
		}
		
		return dst;
	}
	
	public static void main(String[] args) {
		String a = select("41013");
		System.out.println(a);
	}

}
