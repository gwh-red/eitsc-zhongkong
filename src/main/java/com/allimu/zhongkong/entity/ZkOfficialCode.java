package com.allimu.zhongkong.entity;

import java.io.Serializable;
import java.util.Date;
/**
  * 中控操作正式指令对象
 * @author ymsn
 * @date  2020年2月20日
 */
public class ZkOfficialCode implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long schoolCode;		// 学校编码
	private String equipmentCode;	// 设备编码
	private String equipmentType;	// 设备类型
	private String type;			// 操作类型,操作类型(例如:开,关)
	private Byte value;				// 关为0,开为1
	private Boolean isUpload;		// 指令是否已被中控调用,true=1/false=0
	private Date createTime;		// 创建时间
	
	private String userName;		// 用户名
	private Long userNumber;		// 用户编码
	
	
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
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Byte getValue() {
		return value;
	}
	public void setValue(Byte value) {
		this.value = value;
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
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(Long userNumber) {
		this.userNumber = userNumber;
	}
	
	
	
	
	
	
		

}
