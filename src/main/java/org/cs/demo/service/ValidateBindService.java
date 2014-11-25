package org.cs.demo.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

/**
 * 验证是否已经绑定
 * @author cs
 *
 */
public class ValidateBindService {
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
	
	public static boolean validate(String openid){
		boolean b = false;
		// 组装查询地址
		String requestUrl = "http://182.92.79.92/NEUQ_Helper/stuajax!reqStu.action?openid={a}";
		requestUrl = requestUrl.replace("{a}", urlEncodeUTF8(openid));
		// 查询并获取返回结果
		String json = httpRequest(requestUrl);
		JSONObject jsonObject = JSONObject.fromObject(json);
		String flag = (String) jsonObject.get("flag");
		if("true".equals(flag)){
			b = true;
		}
		return b;
	}
	
	public static String getClassNum(String openid){
		// 组装查询地址
		String requestUrl = "http://182.92.79.92/NEUQ_Helper/stuajax!reqStu.action?openid={a}";
		requestUrl = requestUrl.replace("{a}", urlEncodeUTF8(openid));
		// 查询并获取返回结果
		String json = httpRequest(requestUrl);
		JSONObject jsonObject = JSONObject.fromObject(json);
		String classnum = (String) jsonObject.get("class_num");
		return classnum;
	}
	
	public static void main(String[] args) {
		getClassNum("o_0r3t24nvEsEgxb5YjybPV1SNj4");
	}
}
