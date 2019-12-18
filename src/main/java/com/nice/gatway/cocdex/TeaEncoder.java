package com.nice.gatway.cocdex;

import com.nice.gatway.SessionManager;
import com.nice.core.utils.CtxUtil;
import com.nice.gatway.parser.Tea;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class TeaEncoder extends MessageToByteEncoder<byte[]> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeaDecoder.class);

    public TeaEncoder(){
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out)  {
        try {
            int[] secretKey;
            if (CtxUtil.isFirstPacket(ctx)) {
                secretKey = Tea.KEY;
            } else {
                secretKey = SessionManager.getSecretKey(ctx);
            }
            byte[] bys = Tea.encrypt2(msg, secretKey);
            out.writeBytes(bys);
        } catch (Exception e){
            LOGGER.error("decode->emsg={}", e.getMessage(), e);
        }
    }
}
