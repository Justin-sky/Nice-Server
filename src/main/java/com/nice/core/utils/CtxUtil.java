package com.nice.core.utils;

import com.nice.core.netty.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CtxUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CtxUtil.class);
    private static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("S_K");

    public static void setSession(Channel channel, Session session)  {
        if(session != null) {
            channel.attr(SESSION_KEY).set(session);
        }
    }
    public static  Session getSession(Channel channel){
        LOGGER.info("getSession->channel={}", channel);
        return channel.attr(SESSION_KEY).get();
    }

    public static void setSession(ChannelHandlerContext ctx, Session session)  {
        if(session != null) {
            LOGGER.info("setSession->channel={}", ctx.channel());
            ctx.channel().attr(SESSION_KEY).set(session);
            session.setCtx(ctx);
        }
    }
    public static  Session getSession(ChannelHandlerContext ctx){
        return ctx.channel().attr(SESSION_KEY).get();
    }

    public static  void removeSession(ChannelHandlerContext ctx){
        ctx.channel().attr(SESSION_KEY).remove();
    }

    private static final AttributeKey<Boolean> FIRST_PACKET_KEY= AttributeKey.valueOf("F_K");
    public static boolean isFirstPacket(ChannelHandlerContext ctx){
        Boolean firstPacket = ctx.channel().attr(FIRST_PACKET_KEY).get();
        if(firstPacket == null){
            ctx.channel().attr(FIRST_PACKET_KEY).set(false);
        }
        return firstPacket == null;
    }
}