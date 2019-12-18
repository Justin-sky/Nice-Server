package com.nice.core.netty.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public interface Session {

    void setCtx(ChannelHandlerContext ctx);
    ChannelHandlerContext getCtx();
    Channel getChannel();

    boolean channelIsActive();

    boolean channelIsOpen() ;

    boolean channelIsWritable();

    void write(Object msg);

    void writeAndFlush(Object msg);

    void write(Object msg, boolean flush);

    void close();

    String getRemoteAddress();

    String getLocalAddress() ;

    Object getData();

    void setData(Object data);

    void resetNotHeartBreatNums();

    //增加1后，如果超过了最大的未收到心跳的次数，则返回true
    boolean incrNotHeartBreatNums();
}
