package com.nice.core.netty;

import com.nice.core.netty.session.SessionFactoryInterface;
import com.nice.core.utils.NettyUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseNettyServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseNettyServer.class);
    private final int tcpPort;
    private final int readTimeout;
    private final int writeTimeout;
    private final int allTimeout;
    protected final NettyEventProcessorInterface nettyEventProcessor;
    protected final SessionFactoryInterface sessionFactory;

    public BaseNettyServer(int netty_port,  NettyEventProcessorInterface nettyEventProcessor,
                           SessionFactoryInterface sessionFactory) {
        this.tcpPort = netty_port;
        this.readTimeout = 20;
        this.writeTimeout = 20;
        this.allTimeout =20;
        this.nettyEventProcessor = nettyEventProcessor;
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unused")
    private ChannelFuture channelFuture = null;

    public abstract ChannelInitializer createChannelInitializer();

    @SuppressWarnings("rawtypes")
    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ServerBootstrap b = serverBootstrap.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        NettyUtil.setOption(b);
        b.childHandler(createChannelInitializer());

        try {
            channelFuture = serverBootstrap.bind(tcpPort).sync().channel().closeFuture().sync();

            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    LOGGER.info("Server have success bind to " + tcpPort);
                } else {
                    LOGGER.error("Server fail bind to " + tcpPort);
                }
            });
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }


}
