package com.nice.core.netty.session;

public class SessionDefaultFactory implements SessionFactoryInterface {
    @Override
    public Session createSession() {
        return new NettySession();
    }
}
