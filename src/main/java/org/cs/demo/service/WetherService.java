package org.cs.demo.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 查询天气
 * @author Administrator
 *
 */
public class WetherService {
	/**
	 * 发起http请求获取返回结果
	 * 
	 * @param requestUrl 请求地址
	 * @return
	 */
	public static Map<String, String> httpRequest(String requestUrl) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 从request中取得输入流  
			InputStream inputStream = httpUrlConn.getInputStream();
		    // 读取输入流  
		    SAXReader reader = new SAXReader();  
		    Document document = reader.read(inputStream);  
		    // 得到xml根元素  
		    Element root = document.getRootElement();  
		    // 得到根元素的所有子节点  
		    List<Element> elementList = root.elements();
		    Element weather = elementList.get(0);
		    List<Element> weathers = weather.elements();
		  
		    // 遍历所有子节点  
		    for (Element e : weathers)  
		        map.put(e.getName(), e.getText());  
		    
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		   
		}catch(Exception e){
		}
		 return map;  
	}
	
	/**
	 * 获取天气
	 */
	public static String getWetherMessage(String source){
		String requesturl = "http://php.weather.sina.com.cn/xml.php?city={keyWord}&password=DJOYnieT8234jlsK&day=0";
		
		requesturl = requesturl.replace("{keyWord}", urlEncodeGB2312(source));
		
		Map<String, String> map = httpRequest(requesturl);
		StringBuffer sb = new StringBuffer();
		sb.append(map.get("city")).
			append("今天").append(map.get("status1")).append("白天").append(map.get("direction1")).append(map.get("power1")).append("级，夜间")
			.append(map.get("direction2")).append(map.get("power2")).append("级  温度").append(map.get("temperature2")).append("到").append(map.get("temperature1"))
			.append("度").append(map.get("pollution_s")).append(map.get("gm_s"));
		
		return sb.toString();
	}
	
	/**
	 * gb2312编码
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeGB2312(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String source = "北京";
		String wether = getWetherMessage(source);
		System.out.println("测试结果"+wether);
	}
}
