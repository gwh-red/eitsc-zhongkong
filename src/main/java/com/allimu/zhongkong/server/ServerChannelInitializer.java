package com.allimu.zhongkong.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通道初始化，主要用于设置各种Handler
 *
 * @author ymsn
 */
@Component
public class ServerChannelInitializer extends ChannelInitializer<NioDatagramChannel> {

    @Autowired
    UdpServerHandler udpServerHandler;

    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ch.pipeline().addLast("udpServerHandler", udpServerHandler);
    }
}



