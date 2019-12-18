package com.nice.gatway.cocdex;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ByteBufEncoder extends MessageToByteEncoder<byte[]> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ByteBufDecoder.class);

    public ByteBufEncoder(){
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out)  {
        try {
            //²»¼ÓÃÜ
            out.writeShort((short) msg.length);
            out.writeBytes(msg, 0, msg.length);
        } catch (Exception e){
            LOGGER.error("encode->emsg={}", e.getMessage(), e);
        }
    }
}

