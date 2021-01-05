package com.allimu.zhongkong.util;

/**
  * 中控指令转换工具类
 * @author ymsn
 * @date  2019年11月28日
 */
public class ZksoftwareUtils {
	
	
	/**
	  * 十六进制字符串转字节数组
	  * 清除中控指令空格,并替换指令中的0X,
	 * @param str
	 * @return
	 * @author ymsn
	 * @date   2019年11月28日
	 */
	public static byte[] hexStrToByteArr(String hexStr) {
		if (hexStr == null || hexStr.length() == 0) {
			return null;
		}else {
			// 字符串中小写字母转大写(经测试发现小写可能会导致错误,aa会转成-1,AA为-86,ff和FF则一致)
			hexStr = hexStr.toUpperCase();	
			// 替换所有空格
			hexStr = hexStr.trim().replaceAll(" ", "");
			// 替换命令中的0X
			hexStr = hexStr.replaceAll("0X","");
		}
		if (hexStr.length() % 2 == 1) {
			hexStr = "0" + hexStr;
		}
		int len = hexStr.length() / 2;
		byte[] result = new byte[len];
		char[] chars = hexStr.toCharArray();
		for (int i = 0; i < len; i++) {
			result[i] = (byte) (charToByte(chars[i*2]) << 4 | charToByte(chars[i*2 + 1]));
		}
		return result;
	}
	public static byte charToByte(char c) {
		String chars = "0123456789ABCDEF";
		byte b = (byte) chars.indexOf(c);
		return b;
	}
	
	/**
	  * 字节数组转十六进制字符串
	 * @author OuYang
	 * @date 2019-10-25
	 * @version 1.0
	 **/
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F" };
	
	/**
	  * 将中控主机返回的信息转换成16进制的字符串
	 * @param str
	 * @return
	 * @author ymsn
	 * @date   2019年11月28日
	 */
	public static String bytesToHexStr(String str) {
		if(str != null && str != "") {
			StringBuffer s1 = new StringBuffer(str);
			int index;
			for (index = 2; index < s1.length(); index += 3) {
				s1.insert(index, ',');
			}
			String resStr = "";
			String[] array = s1.toString().split(",");
			for (int i = 0; i < array.length; i++) {
				String s = "";
				if (i < array.length - 1) {
					s = "0X" + array[i] + " ";
				} else {
					s = "0X" + array[i];
				}
				resStr += s;
			}
			return resStr.trim().toString();
		}
		return null;
	}
	

}
