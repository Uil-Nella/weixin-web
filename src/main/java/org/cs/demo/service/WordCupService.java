package org.cs.demo.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCupService {
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
	
	public static Map<String, String> getWordCupNews(){
		String html = httpRequest("http://m.zhibo8.cc/#news");
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sb = null;
		Pattern p = Pattern.compile("(.*)(<ul class=\"ent\">)(.*?)(</ul>)(.*)");  
        Matcher m = p.matcher(html);
		if(m.matches()){
			sb = new StringBuffer();
//			System.out.println(m.group(3));
			String str[] = m.group(3).split("</li>");
			for(String s1 :str){
				if(s1.contains("<a")){
					String s2 = s1.substring(s1.indexOf("<a"), s1.indexOf("</a>"));
					String [] s3 = s2.split(">");
//					System.out.println("内容是"+s3[1]);
					String pattern= "href=\"([^\"]*)\""; 
					Pattern pa = Pattern.compile(pattern, 2 | Pattern.DOTALL); 
					Matcher ma = pa.matcher(s3[0]); 
					if(ma.find()) { 
//					     System.out.println("url="+ma.group(1)); 
						map.put(ma.group(1), s3[1]);
					}
				}
			}
		}
		return map;
	}
	
	public static void main(String[] args) {
		Map<String, String> map = getWordCupNews();
		Set<Entry<String, String>> set = map.entrySet();
		for(Entry<String, String> entry :set ){
			System.out.println("链接是："+entry.getKey() +"内容是："+entry.getValue());
		}
	}
}
