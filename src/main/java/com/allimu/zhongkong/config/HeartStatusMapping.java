package com.allimu.zhongkong.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
  * 心跳状态映射类
 * @author ymsn
 * @date  2020年3月10日
 */
public class HeartStatusMapping{
	public static Map<String,String> heartMap = new HashMap<String,String>();
	public static Map<String,Date> dateMap = new HashMap<String,Date>();
	
	public static Map<String, String> getHeartMap() {
		return heartMap;
	}
	public static void setHeartMap(String mac,String heart) {
		heartMap.put(mac, heart);
	}
	public static String getHeart(String mac) {
		return heartMap.get(mac);
	}
	
	
	public static Map<String, Date> getDateMap() {
		return dateMap;
	}
	public static void setDateMap(String mac,Date date) {
		dateMap.put(mac, date);
	}
	public static Date getDate(String mac) {
		return dateMap.get(mac);
	}
	
	public static void removeAll(){
		heartMap.clear();
		dateMap.clear();
	}
	
	
	
}
