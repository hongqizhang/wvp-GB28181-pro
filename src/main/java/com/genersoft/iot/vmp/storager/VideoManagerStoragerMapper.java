package com.genersoft.iot.vmp.storager;

import com.genersoft.iot.vmp.common.StreamInfo;
import com.genersoft.iot.vmp.gb28181.bean.Device;
import com.genersoft.iot.vmp.gb28181.bean.DeviceChannel;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:视频设备数据存储接口
 * @author: panll
 * @date:   2020年11月15日 11:48:03
 */
@Mapper
@Repository
public interface VideoManagerStoragerMapper {
    

    /**
     * 根据设备ID判断设备是否存在
     *
     * @param deviceId 设备ID
     * @return true:存在  false：不存在
     */
    @Select("SELECT count(1) FROM device WHERE deviceId == #{deviceId}")
    public int exists(String deviceId);

    /**
     * 视频设备创建
     *
     * @param device 设备对象
     * @return true：创建成功  false：创建失败
     */
    @Insert("INSERT INTO device (deviceId, name, manufacturer, model, firmware, transport, streamMode, " +
            "ip, port, address, online) " +
            "VALUES (#{deviceId} , #{name}, #{manufacturer}, #{model}, #{firmware}, #{transport}, " +
            "#{streamMode}, #{ip}, #{port}, #{address}, #{online})")
    public int addDevice(Device device);

    /**
     * 视频设备更新
     *
     * @param device 设备对象
     * @return true：创建成功  false：创建失败
     */
    @Update("UPDATE device SET name = #{name}, manufacturer = #{manufacturer}, model = #{model}, " +
            "firmware = #{firmware},transport = #{transport}, streamMode = #{streamMode} WHERE deviceId = #{deviceId} ")
    public int updateDevice(Device device);

    /**
     * 视频设备删除
     *
     * @param deviceId 设备id
     */
    @Delete("DELETE FROM device WHERE deviceId = #{deviceId} ")
    public int deleteDevice(String deviceId);

    /**
     * 添加设备通道
     *
     * @param channel
     * @return
     */
    @Insert("INSERT INTO channel (channelId, deviceId, name, manufacturer, model, owner, civilCode, block, " +
            "address, parental, parentId, safetyWay, registerWay, certNum, certifiable, errCode, endTime, " +
            "secrecy, ipAddress, port, password, PTZType, status, longitude, latitude, subCount, hasAudio) " +
            "VALUES (#{channelId} ,#{deviceId} , #{name}, #{manufacturer}, #{model}, #{owner}, #{civilCode},#{block}, " +
            "#{address}, #{parental}, #{parentId}, #{safetyWay}, #{registerWay}, #{certNum}, #{certifiable}, #{errCode}" +
            ", #{endTime}, #{secrecy}, #{ipAddress}, #{port}, #{password}, #{PTZType}, #{status}, #{longitude}, #{latitude}, " +
            "#{hasAudio})")
    public int addChannel(DeviceChannel channel);


    /**
     * 获取设备
     *
     * @param deviceId 设备ID
     * @return DShadow 设备对象
     */
    @Select("SELECT de.*, (SELECT count(1) FROM channel WHERE deviceId == de.deviceId) as channelCount FROM device de WHERE deviceId == #{deviceId}")
    public Device queryVideoDevice(String deviceId);

    /**
     * 分页获取设备
     *
     * @return DShadow 设备对象
     */
    @Select("<script> " +
            "SELECT de.*, (SELECT count(1) FROM channel WHERE deviceId == de.deviceId) as channelCount FROM device de" +
            "<if test='query != null'> and (deviceId like '%#{query}$' or name like '%#{query}$')</if>\n" +
            "<if test='online != null'> and online == #{online} </if>\n" +
            "</script>")
    public List<Device> queryVideoDevices(String query, String online);


    /**
     * 获取某个设备的通道列表
     *
     * @param deviceId 设备ID
     * @return
     */
    @Select("<script> " +
            "SELECT ch.*, st.ssrc FROM channel ch WHERE ch.deviceId == #{deviceId} " +
            "<if test='parentChannelId != null'> and parentId == #{parentChannelId}</if>\n" +
            "<if test='query != null'> and (channelId like '%#{query}$' or name like '%#{query}$')</if>\n" +
            "<if test='online != null'> and online == #{online} </if>\n" +
            "INNER JOIN streamInfo st ON ch.deviceId == st.deviceId AND ch.channelId == st.channelId" +
            "</script>")
    public List<DeviceChannel> queryChannelsByDeviceId(String deviceId ,String parentChannelId, String query, String online);

    /**
     * 获取某个通道
     * @param deviceId 设备ID
     * @param channelId 通道ID
     */
    @Select("SELECT" +
            "ch.*, st.ssrc" +
            "FROM" +
            "channel ch" +
            "WHERE" +
            "ch.deviceId == #{deviceId }" +
            "AND ch.channelId = #{ channelId }" +
            "INNER JOIN streamInfo st ON ch.deviceId == st.deviceId AND ch.channelId == st.channelId ")
    public DeviceChannel queryChannel(String deviceId, String channelId);


    /**
     * 更新设备在线/离线
     *
     * @param deviceId 设备ID
     * @return true：更新成功  false：更新失败
     */
    @Update("UPDATE device SET online = #{online} WHERE deviceId = #{deviceId} ")
    public int online(String deviceId, int online);


    /**
     * 清空通道
     * @param deviceId
     */
    @Delete("DELETE FROM channel WHERE deviceId = #{deviceId} ")
    void cleanChannelsForDevice(String deviceId);

    /**
     * 更新通道
     * @param deviceId
     */
    @Update("UPDATE device SET name = #{name}," +
            " name = #{name} " +
            " manufacture = #{manufacture} " +
            " model = #{model} " +
            " owner = #{owner} " +
            " civilCode = #{civilCode} " +
            " block = #{block} " +
            " address = #{address} " +
            " parentId = #{parentId} " +
            " safetyWay = #{safetyWay} " +
            " registerWay = #{registerWay} " +
            " certNum = #{certNum} " +
            " certifiable = #{certifiable} " +
            " errCode = #{errCode} " +
            " endTime = #{endTime} " +
            " secrecy = #{secrecy} " +
            " ipAddress = #{ipAddress} " +
            " port = #{port} " +
            " password = #{password} " +
            " PTZType = #{PTZType} " +
            " status = #{status} " +
            " longitude = #{longitude} " +
            " latitude = #{latitude} " +
            " hasAudio = #{hasAudio} " +
            "WHERE deviceId = #{deviceId} and channelId = #{channel.channelId}")
    void updateChannel(String deviceId, DeviceChannel channel);

    /**
     * 通过ssrc查询流信息
     * @param ssrc 流唯一标识
     * @return
     */
    @Select("SELECT * FROM streamInfo WHERE ssrc == #{ssrc}")
    StreamInfo queryPlayBySSRC(String ssrc);

    /**
     * 通过设备信息和通道信息查询流信息
     * @param deviceId 设备ID
     * @param channelId 通道ID
     * @return
     */
    @Select("SELECT * FROM streamInfo WHERE deviceId == #{deviceId} and channelId = #{channelId}")
    StreamInfo queryPlayBydeviceIdAndChannelId(String deviceId, String channelId);


    @Insert("INSERT INTO streamInfo (ssrc, deviceId, channelId, flv, ws_flv, rtmp, hls, rtsp, tracks )" +
            "VALUES (#{ssrc} ,#{deviceId} , #{channelId}, #{flv}, #{ws_flv}, #{rtmp}, #{hls}, #{rtsp} ,#{tracks}) ")
    int addStreamInfo(StreamInfo streamInfo);

    @Delete("DELETE FROM streamInfo WHERE ssrc = #{streamInfo.ssrc} ")
    int deleteStreamInfo(StreamInfo streamInfo);
}

