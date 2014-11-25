package org.cs.demo.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.cs.demo.message.resp.Article;
import org.cs.demo.pojo.News;

public class SelectNewsService {
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
	
	public static List<Article> select(){
		// 组装查询地址
		String requestUrl ="http://182.92.79.92/NEUQ_Helper/selectnewsaction!select.action";
		// 查询并获取返回结果
		String json = httpRequest(requestUrl);
		int end = json.indexOf("msg");
		String str = json.substring(23, end-3);
		str = str.replaceAll("\\\\","");
		JSONArray array = JSONArray.fromObject(str);        
        Object[] obj = new Object[array.size()];        
        for(int i = 0; i < array.size(); i++){        
            JSONObject jsonObject = array.getJSONObject(i);        
            obj[i] = JSONObject.toBean(jsonObject, News.class);        
        }
        List<Article> list = new ArrayList<Article>();
        for(Object o:obj){
        	News news = (News) o;
        	Article article = new Article();
        	article.setDescription(news.getNew_description());
        	article.setPicUrl(news.getNew_picurl());
        	article.setTitle(news.getNew_title());
        	article.setUrl(news.getNew_url());
        	list.add(article);
        }
        
		return list;
	}
	public static void main(String[] args) {
		System.out.println(select());
	}
}
