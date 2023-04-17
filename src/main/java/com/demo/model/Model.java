package com.demo.model;

import com.demo.SessionDescription;

/**
 * Created by taxiao on 2019/8/14
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/taxiao213
 * 微信公众号:他晓
 */
public class Model {

    /**
     * id : register
     * sdpOffer : v=0
     * name : 11
     * from : 1001
     * to : 11
     * isSucceed :1
     * candidate : 262626
     */

    private String id;
    private String from;
    private String fromName;
    private String to;
    private String toName;
    private SessionDescription sessionDescription;
    private IceCandidate iceCandidate;
    private int isSucceed;//1成功 2失败

    public Model() {
    }

    public Model(String id, String fromName, String from, String toName, String to) {
        this.id = id;
        this.fromName = fromName;
        this.from = from;
        this.toName = toName;
        this.to = to;
    }

    public Model(String fromName, String from, String toName, String to) {
        this.fromName = fromName;
        this.from = from;
        this.toName = toName;
        this.to = to;
    }

    public IceCandidate getIceCandidate() {
        return iceCandidate;
    }

    public void setCandidate(IceCandidate iceCandidate) {
        this.iceCandidate = iceCandidate;
    }

    public int getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(int isSucceed) {
        this.isSucceed = isSucceed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SessionDescription getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(SessionDescription sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public void setIceCandidate(IceCandidate iceCandidate) {
        this.iceCandidate = iceCandidate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    /**
     * ICE实体
     * Created by yin13 on 2019/8/14
     * Email:yin13753884368@163.com
     * CSDN:http://blog.csdn.net/yin13753884368/article
     * Github:https://github.com/yin13753884368
     */
    public class IceCandidate {
        public final String sdpMid;
        public final int sdpMLineIndex;
        public final String sdp;
        public final String serverUrl;

        public IceCandidate(String sdpMid, int sdpMLineIndex, String sdp) {
            this.sdpMid = sdpMid;
            this.sdpMLineIndex = sdpMLineIndex;
            this.sdp = sdp;
            this.serverUrl = "";
        }

        IceCandidate(String sdpMid, int sdpMLineIndex, String sdp, String serverUrl) {
            this.sdpMid = sdpMid;
            this.sdpMLineIndex = sdpMLineIndex;
            this.sdp = sdp;
            this.serverUrl = serverUrl;
        }

        public String toString() {
            return this.sdpMid + ":" + this.sdpMLineIndex + ":" + this.sdp + ":" + this.serverUrl;
        }

        String getSdpMid() {
            return this.sdpMid;
        }

        String getSdp() {
            return this.sdp;
        }
    }
}
