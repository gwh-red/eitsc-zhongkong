package com.allimu.zhongkong.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 中控设备定义信息
 * @author ymsn
 * @date 2020-4-21
 */
public class TempReflect implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id; 				// 主键id
	private Long tempId;			// 临时id(等于设备运维中的中控主键id,通过该id获取中控信息,得到mac地址)
	private Long schoolCode; 		// 学校编号
	private String equipmentCode;	// 设备编号
	private String equipmentName;	// 设备名称
	private Date createTime;		// 创建时间
	
	// 下面三个属性只是为了配合远程接口
	private Boolean isUpload;		// 是否已调用
	private String mark;			// mark=1,中控设备定义 ;mark=2,网关设备定义 
	private String equipmentType;	// 设备类型  用于判断中控设备是否定义时需要(因为中控tempId是重复的)
	
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
	public Long getTempId() {
		return tempId;
	}
	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	public Boolean getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	
	
	
	
	

}
