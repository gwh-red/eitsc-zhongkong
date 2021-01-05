package com.allimu.zhongkong.remote;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.allimu.zhongkong.entity.ZkEquipTypes;
import com.allimu.zhongkong.entity.ZkOfficialCode;
import com.allimu.zhongkong.entity.ZkTestCode;

/**
  * 调用集控远程接口
 * @author ymsn
 * @date  2020年1月16日
 */
public interface InstructionCodeRemoteService {
	
	/**
	  * 根据学校编号获取所有中控主机信息
	 * @param schoolCode	学校编码
	 * @return
	 * @author ymsn
	 * @date   2020年2月19日
	 */
	public String getZkEquipBySchoolCode(Long schoolCode);
	
	/**
	  * 保存中控主机可控制的设备类型与中控主机的关联信息
	 * (保存前会先删除重复的关联信息)
	 * @param jsonjArr 		List<DeviceBindInfo>的json数组格式
	 * @param schoolCode	学校编码
	 * @return
	 * @author ymsn
	 * @date   2020年9月23日
	 */
	public int saveDeviceBindInfoByZk(JSONArray jsonjArr,Long schoolCode);
	
	/**
	 * 根据学校编号和标识获取中控设备定义的信息
	 * (临时id跟设备关联信息)
	 * @param schoolCode	学校编码
	 * @param mark			mark=1为中控调用,mark=2为网关调用
	 * @return
	 * @author ymsn
	 * @date   2020年9月23日
	 */
	public JSONArray getTempReflectByZk(Long schoolCode,String mark);
	
	/**
	 * 更新中控设备定义关联表的调用状态
	 * @param tempReflectJson	设备定义的字符串格式
	 * @author ymsn
	 * @date   2020年9月23日
	 */
	public void updateTempReflectByZk(String tempReflectJson);
	
	/**
	  * 根据学校编号获取所有还未被中控调用的测试指令列表
	 * @param	schoolCode 	学校编码(机构号)
	 * @return 
	 * @author ymsn
	 * @date   2020年2月20日
	 */
	public List<ZkTestCode> getZkTestCodeList(Long schoolCode);
	
	/**
	  * 根据学校编号获取所有还未被中控调用的正式指令列表
	 * @param schoolCode	学校编码(机构号)
	 * @return
	 * @author ymsn
	 * @date   2020年2月20日
	 */
	public List<ZkOfficialCode> getZkOfficialCodeList(Long schoolCode);
	
	/**
	 * 更新中控测试指令状态为已调用
	 * 1为更新成功,0为失败
	 * @param zkTestCode	测试指令
	 * @return
	 * @author ymsn
	 * @date   2020年2月20日
	 */
	public int updateZkTestCode(ZkTestCode zkTestCode);
	
	/**
	 * 更新中控正式指令状态为已调用
	 * 1为更新成功,0为失败
	 * @param zkOfficialCode	正式指令
	 * @return
	 * @author ymsn
	 * @date   2020年2月20日
	 */
	public int updateZkOfficialCode(ZkOfficialCode zkOfficialCode);
	
	/**
	 * 保存或更新中控可控制的设备的状态
	 * 返回1=成功 0=失败
	 * @param   schoolCode	学校编码
	 * @param   heartMap	mac地址 : 心跳包 	
	 * @param   dateMap		mac地址 : 状态更新时间
	 * @return
	 * @author ymsn
	 * @date   2020年3月9日
	 */
	public int saveOrUpdateDeviceBindInfoStatus(Long schoolCode,Map<String,String> heartMap,Map<String,Date> dateMap);
	
	/**
	 * 中控向集控保存网络中控可控制的设备类型
	 * @param zkEquipTypes
	 * @return
	 * @author ymsn
	 * 2020年4月27日
	 */
	public int saveZkEquipTypes(ZkEquipTypes zkEquipTypes);

	

}
