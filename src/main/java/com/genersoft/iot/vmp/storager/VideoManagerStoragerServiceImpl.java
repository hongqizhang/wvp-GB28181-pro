package com.genersoft.iot.vmp.storager;

import java.util.List;
import java.util.Map;

import com.genersoft.iot.vmp.common.StreamInfo;
import com.genersoft.iot.vmp.conf.MediaServerConfig;
import com.genersoft.iot.vmp.gb28181.bean.Device;
import com.genersoft.iot.vmp.gb28181.bean.DeviceChannel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**    
 * @Description:视频设备数据存储接口
 * @author: panll
 * @date:   2020年11月15日 11:48:03
 */
@Service
public class VideoManagerStoragerServiceImpl implements VideoManagerStoragerService{

	@Autowired
	private VideoManagerStoragerMapper storageMapper;

	private MediaServerConfig mediaServerConfig;

	@Override
	public boolean updateMediaInfo(MediaServerConfig mediaServerConfig) {
		if (mediaServerConfig != null) {
			this.mediaServerConfig = mediaServerConfig;
			return true;
		}else {
			return false;
		}
	}

	@Override
	public MediaServerConfig getMediaInfo() {
		return mediaServerConfig;
	}

	@Override
	public boolean exists(String deviceId) {
		return storageMapper.queryVideoDevice(deviceId) != null;
	}

	@Override
	public boolean isOnline(String deviceId) {
		return storageMapper.queryVideoDevice(deviceId).getOnline() == 1;
	}

	@Override
	public boolean create(Device device) {
		return storageMapper.addDevice(device) > 0;
	}

	@Override
	public boolean updateDevice(Device device) {
		return storageMapper.updateDevice(device) > 0;
	}

	@Override
	public void updateChannel(String deviceId, DeviceChannel channel) {
		if (storageMapper.queryChannel(deviceId, channel.getChannelId()) != null) {
			storageMapper.updateChannel(deviceId, channel);
		}else {
			channel.setDeviceId(deviceId);
			storageMapper.addChannel(channel);
		}

	}

	@Override
	public Device queryVideoDevice(String deviceId) {
		return storageMapper.queryVideoDevice(deviceId);
	}

	@Override
	public PageInfo queryChannelsByDeviceId(String deviceId, String parentChannelId, String query, String online, int page, int count) {
		PageHelper.startPage(page,count);
		return new PageInfo<>(storageMapper.queryChannelsByDeviceId(deviceId, parentChannelId, query, online));
	}

	@Override
	public List<DeviceChannel> queryChannelAllByDeviceId(String deviceId, String parentChannelId, String query, String online) {
		return storageMapper.queryChannelsByDeviceId(deviceId, parentChannelId, query, online);
	}


	@Override
	public DeviceChannel queryChannel(String deviceId, String channelId) {
		return storageMapper.queryChannel(deviceId, channelId);
	}

	@Override
	public PageInfo<Device> queryVideoDeviceList(String query, String online, int page, int count) {

		PageHelper.startPage(page,count);
		PageInfo<Device> pageInfo = new PageInfo<>(storageMapper.queryVideoDevices(query, online));
		return pageInfo;
	}

	@Override
	public List<Device> queryVideoDeviceList(String query, String online) {
		return storageMapper.queryVideoDevices(query, online);
	}

	@Override
	public boolean delete(String deviceId) {
		return storageMapper.deleteDevice(deviceId) > 0;
	}

	@Override
	public boolean online(String deviceId) {
		return storageMapper.online(deviceId, 1) > 0;
	}

	@Override
	public boolean outline(String deviceId) {
		return storageMapper.online(deviceId, 0) > 0;
	}

	@Override
	public boolean startPlay(StreamInfo streamInfo) {
		return storageMapper.addStreamInfo(streamInfo) > 0;
	}

	@Override
	public boolean stopPlay(StreamInfo streamInfo) {
		return storageMapper.deleteStreamInfo(streamInfo) > 0;
	}

	@Override
	public StreamInfo queryPlay(StreamInfo streamInfo) {
		StreamInfo result = null;
		if (streamInfo.getSsrc() != null) {
			result = storageMapper.queryPlayBySSRC(streamInfo.getSsrc());
		}else {
			result = storageMapper.queryPlayBydeviceIdAndChannelId(streamInfo.getDeviceId(), streamInfo.getChannelId());
		}
		return result;
	}

	@Override
	public void cleanChannelsForDevice(String deviceId) {

	}

	@Override
	public StreamInfo queryPlayBySSRC(String ssrc) {
		return null;
	}

	@Override
	public StreamInfo queryPlayByDevice(String deviceId, String code) {
		return null;
	}

	@Override
	public Map<String, StreamInfo> queryPlayByDeviceId(String deviceId) {
		return null;
	}

	@Override
	public boolean startPlayback(StreamInfo streamInfo) {
		return false;
	}

	@Override
	public boolean stopPlayback(StreamInfo streamInfo) {
		return false;
	}

	@Override
	public StreamInfo queryPlaybackByDevice(String deviceId, String channelId) {
		return null;
	}

	@Override
	public StreamInfo queryPlaybackBySSRC(String ssrc) {
		return null;
	}




}
