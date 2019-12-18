package com.nice.core.netty.message;
import com.nice.core.netty.session.Session;

public class Msg {
    private Session session;
    private byte[] bytes;
    private int uid;
    private int index;

    public Msg() {
    }

    public Msg(Session session, byte[] bytes, int uid, int index) {
        this.session = session;
        this.bytes = bytes;
        this.uid = uid;
        this.index = index;
    }

    public Msg(Session session, int uid) {
        this.session = session;
        this.uid = uid;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}