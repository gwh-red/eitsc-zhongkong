package com.allimu.zhongkong.entity;

import java.io.Serializable;
import java.util.Date;
/**
  * 中控主机可控制的设备类型生成记录跟中控主机信息关联
 * @author ymsn
 * @date  2020年1月19日
 */
public class DeviceBindInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long tempId;			// 临时id(等于设备运维中的中控主键id,通过该id获取中控信息,得到mac地址)
	private Long schoolCode;		// 学校编码
	private String schoolName;		// 学校名称
	private Long buildCode;			// 教学楼编码
	private String buildName;		// 教学楼名称
	private Long classRoomCode;		// 课室编码
	private String classRoomName;	// 课室名称
	private String equipmentType;	// 设备类型名称(如电脑,投影仪等)
	private Boolean isUpload;		// 数据是否已被同步到集控,1为true/0为false
	private String mark;			// mark=1,表示数据为中控产生
	private Date createTime;		// 创建时间
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTempId() {
		return tempId;
	}
	public void setTempId(Long tempId) {
		this.tempId = tempId;
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
	public Long getBuildCode() {
		return buildCode;
	}
	public void setBuildCode(Long buildCode) {
		this.buildCode = buildCode;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public Long getClassRoomCode() {
		return classRoomCode;
	}
	public void setClassRoomCode(Long classRoomCode) {
		this.classRoomCode = classRoomCode;
	}
	public String getClassRoomName() {
		return classRoomName;
	}
	public void setClassRoomName(String classRoomName) {
		this.classRoomName = classRoomName;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public Boolean getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
	
	
	
		

}
