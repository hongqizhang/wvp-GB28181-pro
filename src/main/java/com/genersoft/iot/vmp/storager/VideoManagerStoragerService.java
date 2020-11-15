package com.genersoft.iot.vmp.storager;

import com.genersoft.iot.vmp.common.StreamInfo;
import com.genersoft.iot.vmp.conf.MediaServerConfig;
import com.genersoft.iot.vmp.gb28181.bean.Device;
import com.genersoft.iot.vmp.gb28181.bean.DeviceChannel;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description:视频设备数据存储接口
 * @author: panll
 * @date: 2020年11月15日 11:48:03
 */
public interface VideoManagerStoragerService {

	/**
	 * 更新流媒体信息
	 *
	 * @param mediaServerConfig
	 * @return
	 */
	public boolean updateMediaInfo(MediaServerConfig mediaServerConfig);

	/**
	 * 获取流媒体信息
	 * @return
	 */
	public MediaServerConfig getMediaInfo();


	/**
	 * 根据设备ID判断设备是否存在
	 *
	 * @param deviceId 设备ID
	 * @return true:存在  false：不存在
	 */
	public boolean exists(String deviceId);

	/**
	 * 视频设备创建
	 *
	 * @param device 设备对象
	 * @return true：创建成功  false：创建失败
	 */
	public boolean create(Device device);

	/**
	 * 视频设备更新
	 *
	 * @param device 设备对象
	 * @return true：创建成功  false：创建失败
	 */
	public boolean updateDevice(Device device);

	/**
	 * 添加设备通道
	 *
	 * @param deviceId 设备id
	 * @param channel  通道
	 */
	public void updateChannel(String deviceId, DeviceChannel channel);

	/**
	 * 获取设备
	 *
	 * @param deviceId 设备ID
	 * @return DShadow 设备对象
	 */
	public Device queryVideoDevice(String deviceId);

	/**
	 * 获取某个设备的通道列表
	 *
	 * @param deviceId
	 * @param parentChannelId
	 * @param query
	 * @param online
	 * @param page
	 * @param count
	 * @return
	 */
	public PageInfo queryChannelsByDeviceId(String deviceId, String parentChannelId, String query, String online, int page, int count);


	/**
	 * 查询设备下的所有通道
	 * @param deviceId
	 * @param parentChannelId
	 * @param query
	 * @param online
	 * @return
	 */
	public List<DeviceChannel> queryChannelAllByDeviceId(String deviceId, String parentChannelId, String query, String online);


	/**
	 * 获取某个的通道
	 *
	 * @param deviceId  设备ID
	 * @param channelId 通道ID
	 */
	public DeviceChannel queryChannel(String deviceId, String channelId);

	/**
	 * 获取多个设备
	 *
	 * @return List<Device> 设备对象数组
	 */
	public PageInfo<Device> queryVideoDeviceList( int page, int count);

	/**
	 * 获取多个设备
	 *
	 * @return List<Device> 设备对象数组
	 */
	public List<Device> queryVideoDeviceList();

	/**
	 * 删除设备
	 *
	 * @param deviceId 设备ID
	 * @return true：删除成功  false：删除失败
	 */
	public boolean delete(String deviceId);

	/**
	 * 更新设备在线
	 *
	 * @param deviceId 设备ID
	 * @return true：更新成功  false：更新失败
	 */
	public boolean online(String deviceId);

	/**
	 * 更新设备离线
	 *
	 * @param deviceId 设备ID
	 * @return true：更新成功  false：更新失败
	 */
	public boolean outline(String deviceId);

	/**
	 * 开始播放时将流存入
	 *
	 * @param stream 流信息
	 * @return
	 */
	public boolean startPlay(StreamInfo stream);

	/**
	 * 停止播放时删除
	 *
	 * @return
	 */
	public boolean stopPlay(StreamInfo streamInfo);

	/**
	 * 查找视频流
	 *
	 * @return
	 */
	public StreamInfo queryPlay(StreamInfo streamInfo);

	/**
	 * 清空通道
	 *
	 * @param deviceId
	 */
	void cleanChannelsForDevice(String deviceId);

	StreamInfo queryPlayBySSRC(String ssrc);

	StreamInfo queryPlayByDevice(String deviceId, String code);

	Map<String, StreamInfo> queryPlayByDeviceId(String deviceId);

	boolean startPlayback(StreamInfo streamInfo);

	boolean stopPlayback(StreamInfo streamInfo);

	StreamInfo queryPlaybackByDevice(String deviceId, String channelId);

	StreamInfo queryPlaybackBySSRC(String ssrc);
}
