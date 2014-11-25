package org.cs.demo.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.cs.demo.pojo.Schedule;
import org.cs.demo.util.WeekNumUtil;

/**
 * 新版查询课表
 * @author cs
 *
 */
public class SelectScheduleService {
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
	
	public static String select(String source){
		// 组装查询地址
		String requestUrl = "http://182.92.79.92/NEUQ_Helper/jsonschedule.action?sche_class_num={a}&sche_current_week={b}";
		String sche_current_week = WeekNumUtil.getCurrentWeekNum();
		requestUrl = requestUrl.replace("{a}", urlEncodeUTF8(source));
		requestUrl = requestUrl.replace("{b}", sche_current_week);
		// 查询并获取返回结果
		String json = httpRequest(requestUrl);
		JSONObject jsonObject = JSONObject.fromObject(json); 
		String schedule = (String) jsonObject.get("schedule");
		if("".equals(schedule)){
			return "今天没有课！";
			
		}else{
			JSONArray array = JSONArray.fromObject(schedule);        
	        Object[] obj = new Object[array.size()];        
	        for(int i = 0; i < array.size(); i++){        
	            JSONObject object = array.getJSONObject(i);        
	            obj[i] = JSONObject.toBean(object, Schedule.class);        
	        }  
	        StringBuffer sb = new StringBuffer();
	        sb.append("今天课表：").append("\n");
	        for(Object o :obj){
	        	Schedule sche = (Schedule) o;
	        	sb.append(String.valueOf(Character.toChars(0x1F514))).append("第").append(sche.getSche_num()).append("节课").append("\n");
	        	sb.append(sche.getSche_name()).append("\n");
	        	sb.append(sche.getSche_space()).append("\n");
	        	sb.append(sche.getSche_teacher()).append("\n");
	        }
			return sb.toString();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(select("41005"));
	}
}
