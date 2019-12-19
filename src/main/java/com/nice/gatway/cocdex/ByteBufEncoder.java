package com.nice.gatway.cocdex;
import com.nice.core.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ByteBufEncoder extends MessageToByteEncoder<ByteBuf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ByteBufDecoder.class);

    public ByteBufEncoder(){
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int bodyLen = msg.readableBytes();
        int headerLen = 4;
        out.ensureWritable(headerLen + bodyLen);

        byte[] headerByte = ByteUtil.intToBytes(bodyLen);

        out.writeBytes(headerByte, 0, headerLen);
        out.writeBytes(msg, msg.readerIndex(), bodyLen);

    }

}

