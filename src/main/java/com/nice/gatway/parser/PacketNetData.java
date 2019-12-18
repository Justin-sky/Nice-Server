package com.nice.gatway.parser;

public class PacketNetData {
    //
    private byte[] packetData;

    public PacketNetData() {
    }

    public void setPacketData(byte[] packetData) {
        this.packetData = packetData;
    }

    public int getRouterId() {
        return Tea.byteToInt(packetData, 0);
    }

    public byte getRouterType() {
        return (packetData[4]);
    }


    public int getMsgId() {
        return Tea.byteToInt(packetData, 5);
    }

    public int getIndex() {
        return Tea.byteToInt(packetData, 9);
    }

    public int getIndicationIndex() {
        return Tea.byteToShort(packetData, 13);
    }

    public int getToken() {
        return Tea.byteToInt(packetData, 15);
    }


    /**
     * 只获取协议信息
     *
     * @return
     */
    public byte[] parseProtoNetData() {
        byte[] data = new byte[packetData.length - 19];
        System.arraycopy(packetData, 19, data, 0, data.length);
        return data;
    }

    /**
     * 获取协议加消息id字节数据
     *
     * @return
     */
    public byte[] getMsgIdAndPBData() {
        byte[] data = new byte[packetData.length - 5];
        System.arraycopy(packetData, 5, data, 0, data.length);
        return data;
    }


    /**
     * 获取全部数据
     *
     * @return
     */
    public byte[] getPacketData() {
        return packetData;
    }

}