package com.genersoft.iot.vmp.common;

import com.alibaba.fastjson.JSONArray;

public class StreamInfo {

    private String ssrc;
    private String deviceId;
    private String channelId;
    private String flv;
    private String ws_flv;
    private String rtmp;
    private String hls;
    private String rtsp;
    private JSONArray tracks;

    public String getSsrc() {
        return ssrc;
    }

    public void setSsrc(String ssrc) {
        this.ssrc = ssrc;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getFlv() {
        return flv;
    }

    public void setFlv(String flv) {
        this.flv = flv;
    }

    public String getWs_flv() {
        return ws_flv;
    }

    public void setWs_flv(String ws_flv) {
        this.ws_flv = ws_flv;
    }

    public String getRtmp() {
        return rtmp;
    }

    public void setRtmp(String rtmp) {
        this.rtmp = rtmp;
    }

    public String getHls() {
        return hls;
    }

    public void setHls(String hls) {
        this.hls = hls;
    }

    public String getRtsp() {
        return rtsp;
    }

    public void setRtsp(String rtsp) {
        this.rtsp = rtsp;
    }

    public JSONArray getTracks() {
        return tracks;
    }

    public void setTracks(JSONArray tracks) {
        this.tracks = tracks;
    }
}
