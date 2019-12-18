package com.nice.core.netty.cocdex;


public class NetData {
    private int routerId;
    private byte routerType;
    private int msgId;
    private int index;
    private byte[] content;

    public int getRouterId() {
        return routerId;
    }

    public void setRouterId(int routerId) {
        this.routerId = routerId;
    }

    public byte getRouterType() {
        return routerType;
    }

    public void setRouterType(byte routerType) {
        this.routerType = routerType;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int requestType) {
        this.msgId = requestType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}