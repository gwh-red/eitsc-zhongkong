package com.allimu.zhongkong.entity;

import java.io.Serializable;
/**
 * 中控可控制的设备类型
 * @author ymsn
 * @date  2020年2月25日
 */
public class ZkEquipTypes implements Serializable{
	
	private static final long serialVersionUID = -1L;
	private Long id;
	private Long schoolCode;		// 学校编码
	private String schoolName;		// 学校名称
	private String equipmentTypes;	// 设备类型(多个时逗号隔开,格式为:"电脑,投影机,投影幕布,一体机,扩音,门禁")
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(Long schoolCode) {
		this.schoolCode = schoolCode;
	}
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getEquipmentTypes() {
		return equipmentTypes;
	}
	public void setEquipmentTypes(String equipmentTypes) {
		this.equipmentTypes = equipmentTypes;
	}
	
	
}
