package com.nice.core.netty.cocdex;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BaseDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDecoder.class);

    public BaseDecoder(){
    }
    public short readLean(ByteBuf in){
        in.markReaderIndex();
        final byte[] buf = new byte[2];
        for (int i = 0; i < buf.length; i ++) {
            if (!in.isReadable()) {
                in.resetReaderIndex();
                return -1;
            }

            buf[i] = in.readByte();
        }
        int length =  Tea.byteToShort(buf, 0);

        return (short) length;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }
}
