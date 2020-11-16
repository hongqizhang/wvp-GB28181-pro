package com.genersoft.iot.vmp.gb28181.event;

import com.genersoft.iot.vmp.common.VideoManagerConstants;
import com.genersoft.iot.vmp.gb28181.bean.Device;
import com.genersoft.iot.vmp.storager.VideoManagerStoragerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 设备超时离线检测
 */
@Component
public class DeviceStatusManagerJob {

    private final Logger logger = LoggerFactory.getLogger(DeviceStatusManagerJob.class);

    @Autowired
    private VideoManagerStoragerService storagerService;

    @Autowired
    private EventPublisher publisher;

    @Value("${sip.deviceOfflineTimeout}")
    private int timeout;

    //表示方法执行完成后5秒
    @Scheduled(fixedDelay = 1000)
    public void checkOut() throws InterruptedException {
        // 查询在线的设备
        List<Device> devices = storagerService.queryVideoDeviceList(null, "1");
        Date now = new Date();
        for (int i = 0; i < devices.size(); i++) {
            // 超过 deviceOfflineTimeout 默认180, 设置离线
            Device device = devices.get(i);
            if (now.getTime() - device.getLoginTime().getTime() > timeout) {
                String deviceId = device.getDeviceId();
                storagerService.outline(deviceId);
                logger.info(deviceId + "设备已经超时离线");
                // 发送设备离线事件
                publisher.outlineEventPublish(deviceId, VideoManagerConstants.EVENT_OUTLINE_TIMEOUT);
            }
        }

    }

}
