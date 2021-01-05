package com.allimu.zhongkong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.allimu.zhongkong.remote.InstructionCodeRemoteService;
import com.allimu.zhongkong.util.CommonUtil;

@Configuration
public class BeanConfig {
	
	/**
	 * 注入HessianProxyFactoryBean
	 * @author ymsn
	 * @date 2020-10-17
	 * @return
	 */
	@Bean
    public HessianProxyFactoryBean hessianProxyFactoryBean() {
        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
        factory.setServiceUrl(CommonUtil.remoteServiceUrl);
        factory.setServiceInterface(InstructionCodeRemoteService.class);
        factory.setOverloadEnabled(false);
        return factory;
    }

}
