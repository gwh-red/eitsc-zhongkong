package com.allimu.zhongkong.dao;

import org.apache.ibatis.annotations.Param;

import com.allimu.zhongkong.entity.Equip;

public interface EquipDao{
	
	/**
	 * 保存网络中控
	 * @param Equip
	 * @return
	 */
	public int saveEquip(Equip Equip);
	/**
	 * 更新网络中控
	 * @param Equip
	 * @return
	 */
	public int updateEquip(Equip Equip);
	/**
	 * 获取mac地址
	 * @param tempId	网络中控id
	 * @return
	 */
	public String getMacAddressByTempId(@Param("tempId")Long tempId);
	/**
	 * 获取网络中控mac地址
	 * @param schoolCode	学校编码
	 * @param equipmentCode	设备编码
	 * @return
	 */
	public String getMacAddressByCode(@Param("schoolCode")Long schoolCode,@Param("equipmentCode")String equipmentCode);
	

}
