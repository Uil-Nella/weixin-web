package org.cs.demo.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import org.cs.demo.util.WeekNumUtil;

public class SelectSelfStudyRommService {
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
	
	public static String select(){
		String dst = null;
		// 组装查询地址
		String requestUrl = "http://182.92.79.92/NEUQ_Helper/couaction!select_freeClassRoom.action?cou_week={a}&weeknum={b}";
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
		requestUrl = requestUrl.replace("{a}", week+"");
		requestUrl = requestUrl.replace("{b}", weeknum);
		// 查询并获取返回结果
		String json = httpRequest(requestUrl);
		if(json.contains("获取空闲教室成功")){
			//查询成功
			int start = json.indexOf("rooms");
			String str = json.substring(start+9, json.length()-3);
			String str1[] = str.split(",");
			StringBuffer sb = new StringBuffer();
			for(String s1:str1){
				String s2[] = s1.split(":");
				sb.append(String.valueOf(Character.toChars(0x1F47B))).append("第").append(s2[0]).append("节课").append("\n");
				String s3[] = s2[1].split("#");
				for(String s4:s3){
					sb.append(" ").append(s4);
				}
				sb.append("\n");
			}
			dst = sb.toString();
		}else{
			dst = "查询自习室失败了,请重新查询！";
		}
		return dst;
	}
	
	public static void main(String[] args) {
		String a = select();
		System.out.println(a);
	}
}
