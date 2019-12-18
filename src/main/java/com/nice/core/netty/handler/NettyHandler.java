package com.nice.core.netty.handler;
import com.nice.core.netty.NettyEventProcessorInterface;
import com.nice.core.netty.session.Session;
import com.nice.core.utils.CtxUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyHandler.class);


    private Session session;
    private final NettyEventProcessorInterface nettyEventProcessor;

    public NettyHandler(Session session, NettyEventProcessorInterface nettyEventProcessor) {
        this.session = session;
        this.nettyEventProcessor = nettyEventProcessor;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CtxUtil.setSession(ctx, session);
        super.channelActive(ctx);
    }

    private void closeChannel(ChannelHandlerContext ctx){
        nettyEventProcessor.onClose(session);
        if(session != null){
            session.setCtx(null);
        }
        CtxUtil.removeSession(ctx);
        ctx.flush();
        ctx.close();
        session = null;
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        closeChannel(ctx);
        super.channelInactive(ctx);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        nettyEventProcessor.onMessage(session, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }
        IdleStateEvent e = (IdleStateEvent) evt;
        if (e.state() != IdleState.READER_IDLE) {
            return;
        }
        if(session.incrNotHeartBreatNums()){
            closeChannel(ctx);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("exceptionCaught->ip={},emsg={}",ctx.channel().remoteAddress(), cause.getMessage());
        closeChannel(ctx);
    }
}
