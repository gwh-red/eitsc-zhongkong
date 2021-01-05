package com.allimu.zhongkong.common;

/**
  * 通用静态变量
 */
public interface ImuCST {
	/**
	 * 中控调用标识
	 */
	final static String MARK = "1";
	// 开
	final static String OPEN = "开";
	// 关
	final static String OFF = "关";
	
	/**
	 * 网络中控
	 */
	final static String WLZK_TYPENAME = "网络中控";
	/**
	 * 电脑
	 */
	final static String DN_TYPENAME = "电脑";
	/**
	 * 投影机
	 */
	final static String TYJ_TYPENAME = "投影机";
	/**
	 * 投影幕布
	 */
	final static String TYMB_TYPENAME = "投影幕布";
	/**
	 * 一体机
	 */
	final static String YTJ_TYPENAME = "一体机";
	/**
	 * 扩音
	 */
	final static String KY_TYPENAME = "扩音";
	/**
	 * 门禁
	 */
	final static String MJ_TYPENAME = "门禁";

    //内部统一成功失败返回码
    static final String SUCCESS = "1";		//成功
    static final String FAIL = "-1";		//服务器忙
    static final String PARAM_FAIL = "0";	//参数错误
    static final String SUCCESS_INFO = "操作成功";
    static final String FAIL_INFO = "操作失败";
    
    //统一返回码的key
    static final String DATA = "data";
    static final String RESULT_CODE = "code";
    static final String RESULT_INFO = "info";
    
    

   
}
