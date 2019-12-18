package com.nice.core.netty;

import com.nice.core.netty.session.Session;

public interface NettyEventProcessorInterface {

    void onMessage(Session session, final Object message);

    void onClose(Session session);
}
