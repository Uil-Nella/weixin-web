package org.cs.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 传入开学日期和当前日期
 * start开学日期
 * end  当前日期
 * @author cs
 *
 */
public class WeekNumUtil {
	public static String getWeekNum(String start,String end){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    long to = 0;
	    long from = 0;
		try {
			from = df.parse(start).getTime();
			to = df.parse(end).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	    
		long result = (to-from) / (1000 * 60 * 60 * 24* 7) == 0?(to-from) / (1000 * 60 * 60 * 24* 7):(to-from) / (1000 * 60 * 60 * 24* 7)+1;

		return result+"";
	}
	
	public static String getWeekNum(String start,long end){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    long from = 0;
		try {
			from = df.parse(start).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	    
		long result = (end-from) / (1000 * 60 * 60 * 24* 7) == 0?(end-from) / (1000 * 60 * 60 * 24* 7):(end-from) / (1000 * 60 * 60 * 24* 7)+1;

		return result+"";
		
	}
	public static void main(String[] args) {
		String a = WeekNumUtil.getWeekNum("2014-03-03", System.currentTimeMillis());
		System.out.println(a);
	}
	
	public static String getCurrentWeekNum(){
		return WeekNumUtil.getWeekNum("2014-03-03", System.currentTimeMillis());
	}
}
