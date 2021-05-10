package com.allimu.zhongkong.server;


import com.allimu.zhongkong.common.ImuCST;
import com.allimu.zhongkong.config.ChannelMappingMac;
import com.allimu.zhongkong.config.HeartStatusMapping;
import com.allimu.zhongkong.dao.DeviceBindInfoDao;
import com.allimu.zhongkong.dao.SerialInterfaceDao;
import com.allimu.zhongkong.entity.DeviceBindInfo;
import com.allimu.zhongkong.entity.SerialInterface;
import com.allimu.zhongkong.remote.InstructionCodeRemoteService;
import com.allimu.zhongkong.util.CommonUtil;
import com.allimu.zhongkong.util.FastUtils;
import com.allimu.zhongkong.util.ZksoftwareUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 通道数据输入的处理
 *
 * @author ymsn
 * @date 2020年02月13日
 */
@Component
@Sharable
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Autowired
    private InstructionCodeRemoteService instructionCodeRemoteService;

    @Autowired
    private SerialInterfaceDao serialInterfaceDao;

    @Autowired
    private DeviceBindInfoDao deviceBindInfoDao;

    private Long schoolCode = CommonUtil.schoolCode;

    private String schoolName = CommonUtil.schoolName;

    // mac地址 : 心跳状态
    private Map<String, String> macMap = new HashMap<String, String>();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        // 当通道为空的时候,保存通道
        if (ChannelMappingMac.getCtx() == null) {
            System.out.println(">>> 保存通道信息成功......");
            ChannelMappingMac.setCtx(ctx);
        }

        // 读取收到的数据
        ByteBuf buf = (ByteBuf) packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        // 转换成16进制
        String hexStr = null;
        // 转换成0X形式
        String resStr = null;
        if (req != null) {
            hexStr = ZksoftwareUtils.byteArrayToHexString(req);
            resStr = ZksoftwareUtils.bytesToHexStr(hexStr);
        }

        if (hexStr != null) {
            if ("BB1F1F00".equalsIgnoreCase(hexStr.substring(0, 8))) {
                String macAddress = hexStr.substring(12, 24);
                // 保存mac地址前先移除重复的mac信息
                ChannelMappingMac.removeMac(packet.sender(), macAddress);
                System.out.println(">>> mac地址获取成功==" + macAddress);
                ChannelMappingMac.setMap(packet.sender(), macAddress.toUpperCase());


                SerialInterface serialInterface = serialInterfaceDao.getSerialInterface(macAddress);
                System.out.println(serialInterface);
                if (serialInterface == null && "".equals(serialInterface.getMac())) {
                    serialInterfaceDao.saveSerialInterface(new SerialInterface(macAddress));
                } else {
                    DeviceBindInfo deviceBindInfoTempId = deviceBindInfoDao.getDeviceBindInfoTempId(schoolCode, serialInterface.getId(), ImuCST.CK_TYPENAME);
                    if (deviceBindInfoTempId == null) {
                        DeviceBindInfo deviceBindInfo = new DeviceBindInfo();
                        System.out.println(serialInterface.getId());
                        deviceBindInfo.setTempId(serialInterface.getId());
                        deviceBindInfo.setSchoolCode(schoolCode);
                        deviceBindInfo.setSchoolName(schoolName);
                        deviceBindInfo.setEquipmentType(ImuCST.CK_TYPENAME);
                        deviceBindInfo.setMark(ImuCST.CHUANKOU);
                        deviceBindInfo.setIsUpload(false);
                        deviceBindInfo.setCreateTime(new Date());
                        deviceBindInfoDao.saveDeviceBindInfo(deviceBindInfo);
                    }
                }

            }

            // 心跳包示例: BB 03 21 00 00 04 01 03 05 67 FF; BB0321代表心跳包
            // 当接收到的数据为心跳包时,则发送指令获取mac地址
            if ("BB032100".equalsIgnoreCase(hexStr.substring(0, 8))) {
                // 判断map中是否已存在该中控的信息
                String macAddress = ChannelMappingMac.getMac(packet.sender());
                if (FastUtils.isEmpty(macAddress)) {
                    // 发送获取mac地址的指令
                    String macHexStr = "AA0002021FFF";
                    byte[] macBytes = ZksoftwareUtils.hexStrToByteArr(macHexStr);
                    ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(macBytes), packet.sender())).sync();
                } else {
                    System.out.println(">>> 该网络中控信息已存在...");
                    // 保存macAddress:心跳包的映射
                    HeartStatusMapping.setHeartMap(macAddress, hexStr);
                    // 状态更新时间
                    HeartStatusMapping.setDateMap(macAddress, new Date());
                    System.out.println(">>> 保存心跳状态映射..." + HeartStatusMapping.getHeartMap()
                            + " >>>" + HeartStatusMapping.getDateMap());

                    // 启动时保证每个状态更新只执行一次
                    if (macMap.get(macAddress) == null) {
                        instructionCodeRemoteService.saveOrUpdateDeviceBindInfoStatus(schoolCode, HeartStatusMapping.getHeartMap(),
                                HeartStatusMapping.getDateMap());
                        macMap.put(macAddress, hexStr);
                    }
                }
            }

            // 查询状态后的状态返回 BB 02 21 00 00 04 02 04 06 60 FF
            if ("BB022100".equalsIgnoreCase(hexStr.substring(0, 8))) {
                String macAddress = ChannelMappingMac.getMac(packet.sender());
                if (FastUtils.isNotNull(macAddress)) {
                    HeartStatusMapping.setHeartMap(macAddress, hexStr);
                    // 状态更新时间
                    HeartStatusMapping.setDateMap(macAddress, new Date());
                    System.out.println(">>> 保存心跳状态映射..." + HeartStatusMapping.getHeartMap()
                            + " >>>" + HeartStatusMapping.getDateMap());

                    instructionCodeRemoteService.saveOrUpdateDeviceBindInfoStatus(schoolCode, HeartStatusMapping.getHeartMap(),
                            HeartStatusMapping.getDateMap());
                }
            }

            // mac地址示例: BB 02 1F 00 00 06 46 19 12 12 02 14 FF
            // 当接收到的数据包前面12位等于BB 02 1F 00 00 06即为返回mac地址成功
            if ("BB021F000006".equalsIgnoreCase(hexStr.substring(0, 12))) {
                String macAddress = hexStr.substring(12, 24);
                // 保存mac地址前先移除重复的mac信息
                ChannelMappingMac.removeMac(packet.sender(), macAddress);

                System.out.println(">>> mac地址获取成功==" + macAddress);
                ChannelMappingMac.setMap(packet.sender(), macAddress.toUpperCase());
            }
            // 打印接收到的数据
            int port = packet.sender().getPort();
            String ipAddress = packet.sender().getAddress().getHostAddress();
            System.out.println("收到客户端的数据>>" + resStr + " >>端口=" + port + " >>IP=" + ipAddress);
        }

        if (ChannelMappingMac.getMap() != null) {
            Map<InetSocketAddress, String> map = ChannelMappingMac.getMap();
            for (InetSocketAddress key : map.keySet()) {
                String value = map.get(key).toString();
                System.out.println(">> key=" + key + ">> mac=" + value);
            }
            System.out.println("通道信息>>>" + ChannelMappingMac.getCtx());
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(">>> UDP服务器启动成功 ...");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(">>> 服务器发生异常...");
        // 清空通道数据,心跳记录
        ChannelMappingMac.removeAll();
        HeartStatusMapping.removeAll();

        ctx.close();
        cause.printStackTrace();
    }


}
