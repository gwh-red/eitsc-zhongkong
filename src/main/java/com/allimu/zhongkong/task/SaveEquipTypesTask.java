package com.allimu.zhongkong.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.allimu.zhongkong.entity.ZkEquipTypes;
import com.allimu.zhongkong.remote.InstructionCodeRemoteService;
import com.allimu.zhongkong.util.CommonUtil;


/**
 * 项目启动时运行一次,向集控保存网络中控可控制的设备类型
 * @author ymsn
 */
@Service
public class SaveEquipTypesTask implements  ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private InstructionCodeRemoteService instructionCodeRemoteService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) { 
			
			if(CommonUtil.schoolCode != null && CommonUtil.equipTypeList != null){
				System.out.println(" >> 项目启动时向集控保存网络中控可控制的设备类型...");
				
				ZkEquipTypes zet = new ZkEquipTypes();
				zet.setSchoolCode(CommonUtil.schoolCode);
				zet.setSchoolName(CommonUtil.schoolName);
				zet.setEquipmentTypes(CommonUtil.equipTypeList);
				int result = instructionCodeRemoteService.saveZkEquipTypes(zet);
				if (result > 0) {
					System.out.println(">> 向集控保存网络中控可控制的设备类型成功...");
				}
			}
			
			
//			System.out.println("schoolCode=="+CommonUtil.schoolCode
//					+"==schoolName=="+CommonUtil.schoolName
//					+"==equipTypeList=="+CommonUtil.equipTypeList
//					+"==remoteServiceUrl=="+CommonUtil.remoteServiceUrl
//					);
			
		}
	}
	
	
}
