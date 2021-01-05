package com.allimu.zhongkong.server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allimu.zhongkong.util.CommonUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.Future;

/**
 * udp服务端
 * @author ymsn
 * @date  2019年11月13日
 */
@Service
public class NettyUdpServer implements Runnable{
	
	//监听端口的通道，即server的处理通道
	private Channel channel;
	
	private EventLoopGroup group = new NioEventLoopGroup(1);
	private Thread server;
	
	@Autowired
	ServerChannelInitializer serverChannelInitializer;
	
	public void init() {
		server = new Thread(this);
		server.start();
	}
	
	/**
	 * 停止udp server服务
	 * 销毁前的拦截
	 */
	public void destory() {
		if (channel != null) {
			try {
				ChannelFuture await = channel.close().await();
				if (!await.isSuccess()) {
					System.out.println(">>> udp channel close fail..." + await.cause());
				}
				Future<?> future = group.shutdownGracefully().await();
				if (!future.isSuccess()) {
					System.out.println(">>> udp group shutdown fail" + future.cause());
				}
				group = null;
				channel = null;
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
	}
		
	@Override
	public void run() {
		System.out.println(">>> udp服务初始化..."+Thread.currentThread().getName()+" >>>线程启动 ...");
		try {
			Bootstrap  b = new Bootstrap();
			b.group(group);
			// 支持广播
			b.option(ChannelOption.SO_BROADCAST, true);	
			// 数据包通道,udp通道类型
			b.channel(NioDatagramChannel.class);	
			b.handler(serverChannelInitializer );
			// 绑定端口,开启监听,同步等待
			ChannelFuture f = b.bind(CommonUtil.udpServerPort).sync();
			// 监听服务器关闭监听
			f.channel().closeFuture().sync();
			if (f != null && f.isSuccess()) {
				// 获取通道
				channel = f.channel(); 
	        } else {
	            f.cause().printStackTrace();
	        }
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	
  

}

