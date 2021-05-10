package com.allimu.zhongkong.util;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 获取 学校编码, 学校名称, UDP服务端监听端口, 网络中控可控制设备类型, 中控设备类型名称
 *
 * @author ymsn
 * @date 2020年3月9日
 */
@Component
public class CommonUtil {
    /**
     * 学校编码
     */
    public static Long schoolCode;
    /**
     * 学校名称
     */
    public static String schoolName;
    /**
     * UDP服务端监听端口
     */
    public static Integer udpServerPort;
    /**
     * 中控可控制设备列表
     */
    public static String equipTypeList;
    /**
     * 集控远程连接
     */
    public static String remoteServiceUrl;

    /**
     * 远程连接
     */
    public static String codeRemoteServiceUrl;


    static {

        Properties ps = new Properties();


       /* try {
            ps.load(new InputStreamReader(new FileInputStream("/usr/local/zhongkong-common.properties"), "UTF-8"));
            schoolCode = Long.parseLong(ps.getProperty("schoolCode"));
            schoolName = ps.getProperty("schoolName");
            udpServerPort = Integer.parseInt(ps.getProperty("udpServerPort"));
            equipTypeList = ps.getProperty("equipTypeList");
            remoteServiceUrl = ps.getProperty("remoteServiceUrl");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
		
		/* 其中"UTF-8"，用于明确指定.properties文件的编码格式（不指定则默认使用OS的,
	      这会造成同一份配置文件同一份代码,在linux和windows上、英文windows和中文windows之间的表现都不一致)
	      这个参数应该和具体读取的properties文件的格式匹 */
        try {
            ps.load(new InputStreamReader(
                    CommonUtil.class.getClassLoader().getResourceAsStream("zhongkong-common.properties"), "UTF-8"));
            schoolCode = Long.parseLong(ps.getProperty("schoolCode"));
            schoolName = ps.getProperty("schoolName");
            udpServerPort = Integer.parseInt(ps.getProperty("udpServerPort"));
            equipTypeList = ps.getProperty("equipTypeList");
            remoteServiceUrl = ps.getProperty("remoteServiceUrl");
            codeRemoteServiceUrl = ps.getProperty("codeRemoteServiceUrl");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
