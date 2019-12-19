package com.nice.core.netty.message;
import com.nice.core.netty.session.Session;

public class Msg {
    private Session session;
    private byte[] bytes;


    public Msg(Session session, byte[] bytes) {
        this.session = session;
        this.bytes = bytes;
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

}