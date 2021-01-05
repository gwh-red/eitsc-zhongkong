package com.allimu.zhongkong.dao;

import org.apache.ibatis.annotations.Param;

import com.allimu.zhongkong.entity.TempReflect;

public interface TempReflectDao {
	
	/**
	 * 保存网络中控可控制设备的定义信息
	 * @param tempReflect
	 * @return
	 */
	public int saveTempReflect(TempReflect tempReflect);
	
	/**
	 * 根据网络中控id和设备编码获取
	 * @param tempId		网络中控id
	 * @param equipmentCode	设备编码
	 * @return
	 */
	public TempReflect getTempReflect(@Param("tempId")Long tempId,@Param("equipmentCode")String equipmentCode);
	
	/**
	 * 删除中控可控制设备的定义信息
	 * @param tempId		网络中控id
	 * @param equipmentCode	设备编码
	 */
	public int deleteTempReflect(@Param("tempId")Long tempId,@Param("equipmentCode")String equipmentCode);
	
	/**
	 * 获取网络中控id
	 * @param schoolCode	学校编码
	 * @param equipmentCode	设备编码
	 * @return
	 */
	public Long getTempId(@Param("schoolCode")Long schoolCode,@Param("equipmentCode")String equipmentCode);

}
