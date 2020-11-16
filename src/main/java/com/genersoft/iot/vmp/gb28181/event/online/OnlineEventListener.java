package com.genersoft.iot.vmp.gb28181.event.online;

import com.genersoft.iot.vmp.gb28181.bean.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.genersoft.iot.vmp.common.VideoManagerConstants;
import com.genersoft.iot.vmp.storager.VideoManagerStoragerServiceImpl;

import java.util.Date;


/**
 * @Description: 在线事件监听器，监听到离线后，修改设备离在线状态。 设备在线有两个来源：
 *               1、设备主动注销，发送注销指令，{@link com.genersoft.iot.vmp.gb28181.transmit.request.impl.RegisterRequestProcessor}
 *               2、设备未知原因离线，心跳超时,{@link com.genersoft.iot.vmp.gb28181.transmit.request.impl.MessageRequestProcessor}
 * @author: swwheihei
 * @date: 2020年5月6日 下午1:51:23
 */
@Component
public class OnlineEventListener implements ApplicationListener<OnlineEvent> {
	
	private final static Logger logger = LoggerFactory.getLogger(OnlineEventListener.class);

	@Autowired
	private VideoManagerStoragerServiceImpl storager;

	@Override
	public void onApplicationEvent(OnlineEvent event) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("设备离线事件触发，deviceId：" + event.getDeviceId() + ",from:" + event.getFrom());
		}
		
		String key = VideoManagerConstants.KEEPLIVEKEY_PREFIX + event.getDeviceId();
		boolean needUpdateStorager = false;

		Device device = storager.queryVideoDevice(event.getDeviceId());
		if (device ==null) {
			logger.error("在线事件未找到设备: " + event.getDeviceId());
			return;
		}
		switch (event.getFrom()) {
			// 注册时触发的在线事件，修数据库device在线, 并更新logginTime
			case VideoManagerConstants.EVENT_ONLINE_REGISTER:
			// 设备主动发送心跳触发的在线事件
			case VideoManagerConstants.EVENT_ONLINE_KEEPLIVE:
				setDeviceOnline(device);
				break;
		}

	}

	public void setDeviceOnline(Device device) {
		device.setOnline(1);
		device.setLoginTime(new Date());
		storager.updateDevice(device);
	}
}
