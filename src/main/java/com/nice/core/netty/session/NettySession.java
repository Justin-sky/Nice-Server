package com.nice.core.netty.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettySession implements Session {
    protected Logger logger = LoggerFactory.getLogger(NettySession.class);
    private volatile ChannelHandlerContext ctx;
    private final static int MAX_NOT_HEART_BREAT_NUMS = 3;     //允许未收到心跳的最大次数
    private int notHeartBreatNums = 0;

    private Object data;    //用户数据

    @Override
    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public Channel getChannel() {
        if (ctx != null) {
            return ctx.channel();
        }
        return null;
    }

    @Override
    public boolean channelIsActive() {
        return (ctx != null) && (ctx.channel().isActive());
    }

    @Override
    public boolean channelIsOpen() {
        return (ctx != null) && (ctx.channel().isOpen());
    }

    @Override
    public boolean channelIsWritable() {
        return (ctx != null) && (ctx.channel().isWritable());
    }

    @Override
    public void write(Object msg) {
        if ((ctx != null) && (ctx.channel() != null) && (ctx.channel().isOpen())
                && (ctx.channel().isActive()) && (ctx.channel().isWritable())) {
            ctx.write(msg).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
    }

    @Override
    public void writeAndFlush(Object msg) {
        if ((ctx != null) && (ctx.channel() != null) && (ctx.channel().isOpen())
                && (ctx.channel().isActive()) && (ctx.channel().isWritable())) {
            ctx.channel().writeAndFlush(msg).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
    }

    @Override
    public void write(Object msg, boolean flush) {
        if ((ctx != null) && (ctx.channel() != null) && (ctx.channel().isOpen())
                && (ctx.channel().isActive()) && (ctx.channel().isWritable())) {
            if (flush) {
                ctx.channel().writeAndFlush(msg).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            } else {
                ctx.channel().write(msg).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            }
        }
    }

    @Override
    public void close() {
        if (ctx != null) {
            ctx.channel().close();
            data = null;
            ctx = null;
        }
    }

    @Override
    public String getRemoteAddress() {
        if ((ctx != null) && (ctx.channel() != null)) {
            return ctx.channel().remoteAddress().toString();
        }
        return "";
    }

    @Override
    public String getLocalAddress() {
        if ((ctx != null) && (ctx.channel() != null)) {
            return ctx.channel().localAddress().toString();
        }
        return "";
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    public void resetNotHeartBreatNums() {
        notHeartBreatNums = 0;
    }

    //增加1后，如果超过了最大的未收到心跳的次数，则返回true
    public boolean incrNotHeartBreatNums() {
        notHeartBreatNums++;
        return notHeartBreatNums >= MAX_NOT_HEART_BREAT_NUMS;
    }
}
