package com.nice.gatway;

import com.nice.core.netty.message.MessageDispatcher;
import com.nice.core.netty.session.SessionDefaultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayApp.class);

    public static boolean start = false;


    private static NettyServer nettyServer;

    public static void main(String[] args) throws Exception {

        start = true;

        MessageDispatcher.initialize("com.nice.gatway.handler");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Thread.currentThread().setName("addShutdownHook");
                try {
                    close();
                } catch (Exception e) {
                    LOGGER.error("error->",e);
                }
            }
        });
        nettyServer = new NettyServer(new NettyServerEventProcessor(), new SessionDefaultFactory());
        nettyServer.start();
    }

    private static void close() throws Exception {
        LOGGER.info("--------------Gateway close--------------");
        start = false;
        Thread.sleep(2000);
        LOGGER.info("----------------Gateway closed----------------");
    }
}
