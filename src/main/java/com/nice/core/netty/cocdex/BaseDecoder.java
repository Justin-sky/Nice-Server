package com.nice.core.netty.cocdex;

import com.nice.core.utils.ByteUtil;
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
    public int readLen(ByteBuf in){
        in.markReaderIndex();
        final byte[] buf = new byte[4];
        for (int i = 0; i < buf.length; i ++) {
            if (!in.isReadable()) {
                in.resetReaderIndex();
                return -1;
            }

            buf[i] = in.readByte();
        }
        int length = ByteUtil.bytesToInt(buf, 0);

        return  length;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }
}
