package com.allimu.zhongkong.config;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;

/**
 * UDP通道与mac地址的映射类<br>
 * @author ymsn
 * @date  2020年1月15日
 */
public class ChannelMappingMac{
	
	/**
	 * 通道
	 */
	public static ChannelHandlerContext ctx;
	/**
	 * InetSocketAddress:mac对应
	 */
	public static Map<InetSocketAddress,String> map = new HashMap<InetSocketAddress,String>();
	
	
	public static ChannelHandlerContext getCtx() {
		return ctx;
	}
	public static void setCtx(ChannelHandlerContext ctx) {
		ChannelMappingMac.ctx = ctx;
	}
	
	public static Map<InetSocketAddress, String> getMap() {
		return map;
	}
	public static void setMap(InetSocketAddress inetSocketAddress,String mac) {
		map.put(inetSocketAddress, mac);
	}
	
	public static String getMac(InetSocketAddress inetSocketAddress) {
		return map.get(inetSocketAddress);
	}
	
	/**
	 * 移除重复的mac信息
	 * @param mac
	 */
	public static void removeMac(InetSocketAddress isa,String mac) {
		if (map != null) {
			Iterator<Map.Entry<InetSocketAddress, String>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<InetSocketAddress, String> entry = it.next();
				InetSocketAddress ia = entry.getKey();
				String macAddress = entry.getValue();
				if (mac.equals(macAddress) && isa != ia) {
					it.remove();
					System.out.println(">> key="+ ia + "被删除");
				}
			}
		}
	}
	
	public static void removeAll(){
		ctx = null;
		map.clear();
		System.out.println(">> 清空通道数据");
	}
	
	
	
	
	
	
	
	
	
	
}
