package com.allimu.zhongkong.dao;


import com.allimu.zhongkong.entity.SerialInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
@Mapper
public interface SerialInterfaceDao {

    int saveSerialInterface(SerialInterface serialInterface);

    /**
     * @param mac
     * @return
     */
    SerialInterface getSerialInterface(@Param("mac") String mac);

}
