package com.nice.core.netty.cocdex;

public class ClientDate {
    //包长2  uid 4  msgid 4   result 4   index4
    private byte[] clientDate;

    public int getMsgId() {
        return Tea.byteToInt(clientDate, 4);
    }

    public int getUid() {
        return Tea.byteToInt(clientDate, 0);
    }

    public int getResult() {
        return Tea.byteToInt(clientDate, 8);
    }

    public int getIndex() {
        return Tea.byteToInt(clientDate, 12);
    }

    /**
     * 只获取协议信息
     *
     * @return
     */
    public byte[] parseProtoNetData() {
        byte[] data = new byte[clientDate.length - 16];
        System.arraycopy(clientDate, 16, data, 0, data.length);
        return data;
    }


    public byte[] getClientDate() {
        return clientDate;
    }


    public void setClientDate(byte[] clientDate) {
        this.clientDate = clientDate;
    }
}
