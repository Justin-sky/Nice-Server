package com.nice.gatway;
import com.nice.gatway.cocdex.ByteBufDecoder;
import com.nice.gatway.cocdex.ByteBufEncoder;
import com.nice.core.netty.BaseNettyServer;
import com.nice.core.netty.NettyEventProcessorInterface;
import com.nice.core.netty.handler.NettyHandler;
import com.nice.core.netty.session.SessionFactoryInterface;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyServer extends BaseNettyServer {

    private int reader_idle_time;
    private int writer_idle_time;
    private int all_idle_time;

    public NettyServer(NettyEventProcessorInterface nettyEventProcessor, SessionFactoryInterface sessionFactory) {
        super(10002, nettyEventProcessor, sessionFactory);
        this.reader_idle_time = 30;
        this.writer_idle_time = 30;
        this.all_idle_time = 30;
    }

    @Override
    public ChannelInitializer createChannelInitializer() {
        return new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new IdleStateHandler(reader_idle_time, writer_idle_time, all_idle_time, TimeUnit.SECONDS));
                p.addLast(new ByteBufDecoder());
                p.addLast(new ByteBufEncoder());
                p.addLast(new NettyHandler(sessionFactory.createSession(), nettyEventProcessor));
            }
        };
    }
}
