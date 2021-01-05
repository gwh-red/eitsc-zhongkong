package com.allimu.zhongkong.util;

import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * 常用方法
 * @author ymsn
 * @date 2019年9月19日
 */
public class FastUtils extends PropertyUtilsBean {

	public static void main(String[] args) {
		System.out.println(getAftertFromatTime(30, "yyyy-MM-dd HH:mm"));
		System.out.println(getFromatDate("yyyy-MM-dd HH:mm"));
	}

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(Object str) {
		if (str != null && str != "" && !"".equals(str.toString())) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object str) {
		return !isNotNull(str);
	}

	public static void copyBeanToBean(Object databean, Object tobean) throws Exception {
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(databean);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			if ("class".equals(name)) {
				continue;
			}
			if (PropertyUtils.isReadable(databean, name) && PropertyUtils.isWriteable(tobean, name)) {
				try {
					Object value = PropertyUtils.getSimpleProperty(databean, name);
					if (value != null) {
						getInstance().setSimpleProperty(tobean, name, value);
					}
				} catch (java.lang.IllegalArgumentException ie) {
					ie.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param min    分钟数
	 * @param fromat 格式 yyyy-MM-dd :hh:mm:ss
	 * @return
	 * @author ymsn
	 * @date 2018年10月17日
	 */
	public static String getAftertFromatTime(int min, String fromat) {
		Date date = new Date();
		int ms = min * 60000;
		Date afterTime = new Date(date.getTime() + ms);
		SimpleDateFormat dateFormat = new SimpleDateFormat(fromat);
		return dateFormat.format(afterTime);
	}

	/**
	 * 获取Date类型的当前时间
	 * @param fromat
	 * @return
	 * @author ymsn
	 * @date 2018年10月18日
	 */
	public static Date getFromatDate(String fromat) {
		Date date = new Date();
		Date fromatDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(fromat);
		String StringDate = dateFormat.format(date);
		try {
			fromatDate = dateFormat.parse(StringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fromatDate;
	}

	/**
	 * map中key值按降序排列
	 * @param map
	 * @return
	 * @author ymsn
	 * @date 2019年3月19日
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				int compare = (o1.getValue()).compareTo(o2.getValue());
				return -compare;
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * 返回map
	 * @return
	 * @author ymsn
	 * @date   2019年9月19日
	 */
	public static Map<String,Object> getMap(){
		return new HashMap<String, Object>();
	}

}
