package com.allimu.zhongkong.dao;

import com.allimu.zhongkong.entity.DeviceBindInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceBindInfoDao {

    /**
     * 保存网络中控生成的可控制设备类型信息
     *
     * @param deviceBindInfo
     * @return
     */
    public int saveDeviceBindInfo(DeviceBindInfo deviceBindInfo);

    /**
     * 获取还未向集控保存的网络中控可控制设备类型信息
     *
     * @param schoolCode 学校编码
     * @return
     */
    public List<DeviceBindInfo> getDeviceBindInfoListBySchoolCode(@Param("schoolCode") Long schoolCode);

    /**
     * 根据网络中控设备id获取生成的可控制设备类型信息
     *
     * @param schoolCode 学校编码
     * @param tempId     网络中控设备id
     * @return
     */
    public List<DeviceBindInfo> getDeviceBindInfoListByTempId(@Param("schoolCode") Long schoolCode, @Param("tempId") Long tempId);

    /**
     * 更新可控制设备类型信息的状态,设置为已上传
     *
     * @param deviceBindInfoList
     * @return
     */
    public int updateDeviceBindInfoList(@Param("deviceBindInfoList") List<DeviceBindInfo> deviceBindInfoList);

    DeviceBindInfo getDeviceBindInfoTempId(@Param("schoolCode") Long schoolCode, @Param("tempId") Long tempId, @Param("equipmentType") String equipmentType);

}
