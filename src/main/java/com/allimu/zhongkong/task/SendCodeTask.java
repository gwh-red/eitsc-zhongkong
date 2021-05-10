package com.allimu.zhongkong.task;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allimu.zhongkong.common.ImuCST;
import com.allimu.zhongkong.config.ChannelMappingMac;
import com.allimu.zhongkong.config.HeartStatusMapping;
import com.allimu.zhongkong.dao.DeviceBindInfoDao;
import com.allimu.zhongkong.dao.EquipDao;
import com.allimu.zhongkong.dao.TempReflectDao;
import com.allimu.zhongkong.entity.*;
import com.allimu.zhongkong.remote.InstructionCodeRemoteService;
import com.allimu.zhongkong.util.CommonUtil;
import com.allimu.zhongkong.util.FastUtils;
import com.allimu.zhongkong.util.ZksoftwareUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author ymsn
 * @date 2020年1月16日
 */
@Service
@EnableScheduling
public class SendCodeTask {

    @Autowired
    private EquipDao equipDao;
    @Autowired
    private DeviceBindInfoDao deviceBindInfoDao;
    @Autowired
    private TempReflectDao tempReflectDao;

    @Autowired
    private InstructionCodeRemoteService instructionCodeRemoteService;

    // 学校编码
    private Long schoolCode = CommonUtil.schoolCode;


    /**
     * 保存更新中控主机信息_定时任务
     * (并同时生成设备类型临时信息表)
     *
     * @author ymsn
     * @date 2020年2月18日
     */
    @Scheduled(cron = "0/25 * * * * ?")
    public void saveZkEquipWork() {
//		System.out.println(">> 保存网络中控信息_定时任务");
		
		String equipListStr = instructionCodeRemoteService.getZkEquipBySchoolCode(schoolCode);
		JSONObject jsonObj = JSONObject.parseObject(equipListStr);
		JSONArray jsonjArr = JSONObject.parseArray(jsonObj.get("data").toString());
		List<Equip> equipList = JSONObject.parseArray(jsonjArr.toJSONString(), Equip.class);
		if(equipList != null && equipList.size() > 0) {
			for (Equip equip : equipList) {
				String macAddress = equip.getMacAddress();
				macAddress = macAddress.replaceAll("-", "").replaceAll(":", "");	
				equip.setMacAddress(macAddress);
				equip.setTempId(equip.getId());
				equip.setCreateTime(new Date());
				Long schoolCode = equip.getSchoolCode();
				Long tempId = equip.getId();  
				List<DeviceBindInfo> list = deviceBindInfoDao.getDeviceBindInfoListByTempId(schoolCode,tempId);
				if(list != null && list.size() > 0) { 
//					System.out.println(">>该中控主机下设备类型关联记录已生成...");
					equipDao.updateEquip(equip);
				}else {  // 生成中控主机可控制的设备类型生成记录跟中控主机信息关联
					System.out.println(">>自动生成中控主机下设备类型关联记录...");
					String equipTypeList = CommonUtil.equipTypeList;
					String[] typeArr = equipTypeList.split(",");
					for (int i = 0; i < typeArr.length; i++) {
						String equipmentType = typeArr[i];	
						DeviceBindInfo deviceBindInfo = new DeviceBindInfo();
						deviceBindInfo.setTempId(tempId);
						deviceBindInfo.setSchoolCode(schoolCode);
						deviceBindInfo.setSchoolName(equip.getSchoolName());
						deviceBindInfo.setBuildCode(equip.getBuildCode());
						deviceBindInfo.setBuildName(equip.getBuildName());
						deviceBindInfo.setClassRoomCode(equip.getClassCode());
						deviceBindInfo.setClassRoomName(equip.getClassName());
						deviceBindInfo.setEquipmentType(equipmentType);
						deviceBindInfo.setMark(ImuCST.MARK); 	
						deviceBindInfo.setIsUpload(false);		
						deviceBindInfo.setCreateTime(new Date());
						deviceBindInfoDao.saveDeviceBindInfo(deviceBindInfo);
					}
					
					equipDao.saveEquip(equip);
				} // else end!
				
			}  // for end!
		}
	}
	
	/**
	 * 向集控远程保存设备类型关联中控信息表_定时任务
	 * (保存后改变调用状态)
	 * @author ymsn
	 * @date   2020年2月19日
	 */
	@Scheduled(cron = "0/15 * * * * ?")
	public void saveDeviceBindInfoWork() {
		
		List<DeviceBindInfo> list = deviceBindInfoDao.getDeviceBindInfoListBySchoolCode(schoolCode);
		if(list != null && list.size() > 0) {
			System.out.println(">> 向集控保存设备类型关联中控信息_定时任务");
			JSONArray jsonjArr = JSONObject.parseArray(JSON.toJSONString(list));
			int count = instructionCodeRemoteService.saveDeviceBindInfoByZk(jsonjArr,schoolCode);
			if(count > 0) {	
				deviceBindInfoDao.updateDeviceBindInfoList(list);
			}
		}
	}
	
	/**
	 * 从集控获取中控可控制的设备定义_定时任务
	 * (保存后调用改变调用状态)
	 * @author ymsn
	 * @date   2020年2月20日
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	public void saveTempReflectWork() {
		JSONArray jsonArr = instructionCodeRemoteService.getTempReflectByZk(schoolCode,ImuCST.MARK);
		if(jsonArr != null && jsonArr.size() > 0) {
		List<TempReflect> list = JSONObject.parseArray(jsonArr.toJSONString(), TempReflect.class);
			System.out.println(">>启动从集控获取中控可控制的设备定义_定时任务");
			
			for (TempReflect tempReflect : list) {
				instructionCodeRemoteService.updateTempReflectByZk(JSON.toJSONString(tempReflect));
				Long tempId = tempReflect.getTempId();
				String equipmentCode = tempReflect.getEquipmentCode();
				TempReflect obj = tempReflectDao.getTempReflect(tempId, equipmentCode);
				if(obj != null){  
					tempReflectDao.deleteTempReflect(tempId, equipmentCode);
				}
				tempReflect.setCreateTime(new Date());
				tempReflectDao.saveTempReflect(tempReflect);
			}
		}
	}
	
	
	/**
	  * 从集控获取测试指令,转换后发送到中控_定时任务
	 * @author ymsn
	 * @date   2020年2月20日
	 */
	@Scheduled(cron = "0/2 * * * * ?")
	public void sendTestCodeWork() {
		
		List<ZkTestCode> list = instructionCodeRemoteService.getZkTestCodeList(schoolCode);
		if(list != null && list.size() > 0) {
			sendTestCode(list);
		}
	}
	@Async
	public void sendTestCode(List<ZkTestCode> list) {
		System.out.println(">> 成功获取到测试指令...");
		ChannelHandlerContext ctx = ChannelMappingMac.getCtx();
		Map<InetSocketAddress, String> map = ChannelMappingMac.getMap();
		Long currTime = System.currentTimeMillis();
		if (ctx != null && map != null) {
			System.out.println(">> 准备发送测试指令...");
			
			for (ZkTestCode ztc : list) {
				if(currTime - ztc.getCreateTime().getTime() > 10008) {	
					System.out.println(">> 测试指令超时抛弃...");
					instructionCodeRemoteService.updateZkTestCode(ztc);
					continue;
				}
				Long tempId = ztc.getTempId();
				String type = ztc.getType();
				String equipmentType = ztc.getEquipmentType();
				String macAddress = equipDao.getMacAddressByTempId(tempId);	
				String code = "";
				if(equipmentType != null) {
					if(equipmentType.equals(ImuCST.TYJ_TYPENAME)) {
						if(type.equals(ImuCST.OPEN)) {  
							code = "0XAA 0X00 0X02 0X00 0X05 0XFF";
						}else if(type.equals(ImuCST.OFF)) {	
							code = "0XAA 0X00 0X02 0X00 0X06 0XFF";
						}
					}else if(equipmentType.equals(ImuCST.DN_TYPENAME)){
						if(type.equals(ImuCST.OPEN)) {  
							code = "0XAA 0X00 0X02 0X00 0X03 0XFF";
						}else if(type.equals(ImuCST.OFF)) {	
							code = "0XAA 0X00 0X02 0X00 0X04 0XFF";
						}
					}else if(equipmentType.equals(ImuCST.KY_TYPENAME)){
						if (type.equals(ImuCST.OPEN)) { 
							code = "0XAA 0X00 0X02 0X00 0X09 0XFF";
						} else if (type.equals(ImuCST.OFF)) { 
							code = "0XAA 0X00 0X02 0X00 0X0A 0XFF";
						} else if(type.equals("静音")) {	
							code = "0XAA 0X00 0X02 0X00 0X0B 0XFF";
						}
					}else {
						if(type.equals(ImuCST.OPEN)) {  
							code = "0XAA 0X00 0X02 0X00 0X01 0XFF";
						}else if(type.equals(ImuCST.OFF)) {
							code = "0XAA 0X00 0X02 0X00 0X02 0XFF";
						}
					}
					
				}
				if (FastUtils.isNotNull(macAddress)) {
					for (InetSocketAddress inetSocketAddress : map.keySet()) {
						String macValue = map.get(inetSocketAddress).toString();
						if (macAddress.equals(macValue)) {
							System.out.println(">> 向中控主机发送正式指令,mac地址="+macValue);
							byte[] bytes = ZksoftwareUtils.hexStrToByteArr(code);
							ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(bytes), inetSocketAddress));
							String heartHexStr = "AA00020221FF";
							byte[] heartBytes = ZksoftwareUtils.hexStrToByteArr(heartHexStr);
							ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(heartBytes), inetSocketAddress));
//							break;
                        }
                    }
                    // 更新调用状态
                    instructionCodeRemoteService.updateZkTestCode(ztc);
                }

            }

        } else {
            System.out.println(">> 通道为空,抛弃超时指令...");
            for (ZkTestCode ztc : list) {
                if (currTime - ztc.getCreateTime().getTime() > 10008) {
                    System.out.println(">> 测试指令超时抛弃...");
                    instructionCodeRemoteService.updateZkTestCode(ztc);
                    continue;
                }
            }
        }
    }

    /**
     * 从集控获取正式指令,转换后发送到中控_定时任务
     *
     * @author ymsn
     * @date 2020年2月20日
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void sendOfficialCodeWork() {

        List<ZkOfficialCode> list = instructionCodeRemoteService.getZkOfficialCodeList(schoolCode);
        if (list != null && list.size() > 0) {
            sendOfficialCode(list);
        }
    }

    @Async
    public void sendOfficialCode(List<ZkOfficialCode> list) {
        InetSocketAddress ia = null;
        ChannelHandlerContext ctx = ChannelMappingMac.getCtx();
        Map<InetSocketAddress, String> map = ChannelMappingMac.getMap();
        Long currTime = System.currentTimeMillis();
        if (ctx != null && map != null) {
            System.out.println(">> 准备发送正式指令...");

            for (ZkOfficialCode zoc : list) {
                if (currTime - zoc.getCreateTime().getTime() > 10000 * 2) {
                    System.out.println(">> 正式指令超时抛弃..." + currTime + "  " + zoc.getCreateTime().getTime());
                    instructionCodeRemoteService.updateZkOfficialCode(zoc);
                    continue;
                }
                Long schoolCode = zoc.getSchoolCode();
                String equipmentCode = zoc.getEquipmentCode();
                String type = zoc.getType();
                String equipmentType = zoc.getEquipmentType();
                String code = "";
                String macAddress = "";
                if (equipmentType.equals(ImuCST.WLZK_TYPENAME)) {
                    macAddress = equipDao.getMacAddressByCode(schoolCode, equipmentCode);

                    if (type.equals(ImuCST.OPEN)) {
                        code = "0XAA 0X00 0X02 0X00 0X01 0XFF";
                    } else if (type.equals(ImuCST.OFF)) {
                        code = "0XAA 0X00 0X02 0X00 0X02 0XFF";
                    }
                } else {
                    Long tempId = tempReflectDao.getTempId(schoolCode, equipmentCode);
                    if (tempId != null) {
                        macAddress = equipDao.getMacAddressByTempId(tempId);
                        if (equipmentType.equals(ImuCST.TYJ_TYPENAME)) {
                            if (type.equals(ImuCST.OPEN)) {
                                code = "0XAA 0X00 0X02 0X00 0X05 0XFF";
                            } else if (type.equals(ImuCST.OFF)) {
                                code = "0XAA 0X00 0X02 0X00 0X06 0XFF";
                            }
                        } else if (equipmentType.equals(ImuCST.DN_TYPENAME)) {
                            if (type.equals(ImuCST.OPEN)) {
                                code = "0XAA 0X00 0X02 0X00 0X03 0XFF";
                            } else if (type.equals(ImuCST.OFF)) {
                                code = "0XAA 0X00 0X02 0X00 0X04 0XFF";
                            }
                        } else if (equipmentType.equals(ImuCST.KY_TYPENAME)) {
                            if (type.equals(ImuCST.OPEN)) {
                                code = "0XAA 0X00 0X02 0X00 0X09 0XFF";
                            } else if (type.equals(ImuCST.OFF)) {
                                code = "0XAA 0X00 0X02 0X00 0X0A 0XFF";
                            } else if (type.equals("静音")) {
                                code = "0XAA 0X00 0X02 0X00 0X0B 0XFF";
                            }
                        } else {
                            if (type.equals(ImuCST.OPEN)) {
                                code = "0XAA 0X00 0X02 0X00 0X01 0XFF";
                            } else if (type.equals(ImuCST.OFF)) {
                                code = "0XAA 0X00 0X02 0X00 0X02 0XFF";
                            }
                        }
                    }
                }

                if (FastUtils.isNotNull(macAddress)) {
                    for (InetSocketAddress inetSocketAddress : map.keySet()) {
                        String macValue = map.get(inetSocketAddress).toString();
                        if (macAddress.equals(macValue)) {
                            System.out.println(">> 向中控主机发送正式指令,mac地址=" + macValue);

                            byte[] bytes = ZksoftwareUtils.hexStrToByteArr(code);
                            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(bytes), inetSocketAddress));
                            ia = inetSocketAddress;
//							break;
                        }
                    }
                    // 更新调用状态
                    instructionCodeRemoteService.updateZkOfficialCode(zoc);
                }

            }

            if (ia != null) {
                sendHeart(ctx, ia);
            }

        } else {
            System.out.println(">> 通道为空,抛弃超时指令...");
            for (ZkOfficialCode zoc : list) {
                if (currTime - zoc.getCreateTime().getTime() > 10008) {
                    System.out.println(">> 正式指令超时抛弃...");
                    instructionCodeRemoteService.updateZkOfficialCode(zoc);
                    continue;
                }
            }
        }
    }

    /**
     * 保存或更新中控可控制的设备的状态_定时任务
     * 注意: 这里的中控主机mac没有:,设备运维里面中控mac地址有:, 在集控需要处理加上:才能查询出数据
     * 返回1=成功 0=失败
     *
     * @author ymsn
     * @date 2020年3月9日
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void saveOrUpdateDeviceBindInfoStatusWork() {
        if (HeartStatusMapping.getHeartMap() != null && !HeartStatusMapping.getHeartMap().isEmpty()) {
            instructionCodeRemoteService.saveOrUpdateDeviceBindInfoStatus(schoolCode, HeartStatusMapping.getHeartMap(),
                    HeartStatusMapping.getDateMap());
        }
    }

    /**
     * 指令发送后发送查询设备状态
     *
     * @param ctx
     * @param inetSocketAddress
     * @author ymsn
     * @date 2020年3月12日
     */
    public void sendHeart(ChannelHandlerContext ctx, InetSocketAddress inetSocketAddress) {
        String heartHexStr = "AA00020221FF";
        byte[] heartBytes = ZksoftwareUtils.hexStrToByteArr(heartHexStr);
        ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(heartBytes), inetSocketAddress));
        System.out.println(">> 发送查询设备状态...");
    }

    public void getOne() {
        String result1 = HttpUtil.get(CommonUtil.codeRemoteServiceUrl);
    }


}

