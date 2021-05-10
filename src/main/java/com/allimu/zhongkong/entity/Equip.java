package com.allimu.zhongkong.entity;

import java.io.Serializable;
import java.util.Date;

/**
  * 中控主机信息表
 * @author ymsn
 * @date  2020年2月18日
 */
public class Equip implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long tempId;		// 临时id (等于设备运维中控主机主键)
	private Long regionCode;	// 所属区域编码
	private String regionName;	// 所属区域编码
	private Long schoolCode;	// 所属学校编码
	private String schoolName;	// 所属学校编码
	private Long buildCode;		// 所属教学楼编码
	private String buildName;	// 所属教学楼编码
	private Long classCode;		// 所属教室编码
	private String className;	// 所属教室编码
	
	private String manufName;	// 所属厂商名称
	private String brandName;	// 所属厂商品牌名称
	private String modelName;	// 所属厂商品牌型号名称
	private String typeName;	// 所属设备类型名称
	
	private String code;		// 设备固定资产编号
	private String name;		// 设备名称
	
	private String macAddress;	// 中控mac地址
	private String ip;			// 中控主机内网ip地址
	
	private Date createTime;	// 创建时间

	
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
	public Long getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}

	public Long getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(Long schoolCode) {
		this.schoolCode = schoolCode;
	}

	public Long getBuildCode() {
		return buildCode;
	}

	public void setBuildCode(Long buildCode) {
		this.buildCode = buildCode;
	}

	public Long getClassCode() {
		return classCode;
	}

	public void setClassCode(Long classCode) {
		this.classCode = classCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getManufName() {
		return manufName;
	}

	public void setManufName(String manufName) {
		this.manufName = manufName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	@Override
	public String toString() {
		return "Equip{" +
				"id=" + id +
				", tempId=" + tempId +
				", regionCode=" + regionCode +
				", regionName='" + regionName + '\'' +
				", schoolCode=" + schoolCode +
				", schoolName='" + schoolName + '\'' +
				", buildCode=" + buildCode +
				", buildName='" + buildName + '\'' +
				", classCode=" + classCode +
				", className='" + className + '\'' +
				", manufName='" + manufName + '\'' +
				", brandName='" + brandName + '\'' +
				", modelName='" + modelName + '\'' +
				", typeName='" + typeName + '\'' +
				", code='" + code + '\'' +
				", name='" + name + '\'' +
				", macAddress='" + macAddress + '\'' +
				", ip='" + ip + '\'' +
				", createTime=" + createTime +
				'}';
	}
}