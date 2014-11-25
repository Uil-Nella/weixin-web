package org.cs.demo.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.cs.demo.pojo.Movie;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 *查询电影票房
 */
public class MoviePerformService {
	/**
	 * 发起http请求获取返回结果
	 * 
	 * @param requestUrl 请求地址
	 * @return
	 */
	public static List<Movie> httpRequest(String requestUrl) {
		List<Movie> mlist = new ArrayList<Movie>();
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
		    Element result = elementList.get(2);
		    List<Element> items = result.elements();
		  
		    
		    // 遍历所有子节点  
		    for (Element e : items){
		    	List<Element> list = e.elements();
		    	Movie mo = new Movie();
		    	mo.setRid(list.get(0).getText());
		    	mo.setName(list.get(1).getText());
		    	mo.setWk(list.get(2).getText());
		    	mo.setWboxoffice(list.get(3).getText());
		    	mo.setTboxoffice(list.get(4).getText());
		    	mlist.add(mo);
		    }  
		    
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		   
		}catch(Exception e){
		}
		 return mlist;  
	}
	
	/**
	 * 获取电影票房
	 */
	public static String getMovieMessage(String source){
		String requesturl = "http://v.juhe.cn/boxoffice/rank.php?key=006aa248d0229d491047b30241fc1d20&dtype=xml&area={keyWord}";
		
		requesturl = requesturl.replace("{keyWord}", source);
		
		List<Movie> list= httpRequest(requesturl);
		StringBuffer sb = new StringBuffer();
		for(Movie m :list){
			sb.append(m.getRid()).append("\n");
			sb.append(m.getName()).append("\n");
			sb.append(m.getWk()).append("\n");
			sb.append(m.getWboxoffice()).append("\n");
			sb.append("总票房：").append(m.getTboxoffice()).append("\n\n");
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		String source = "CN";
		String message = getMovieMessage(source);
		System.out.println(message);
	}
}
