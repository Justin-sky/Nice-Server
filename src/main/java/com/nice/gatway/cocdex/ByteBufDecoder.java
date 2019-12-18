package com.nice.gatway.cocdex;
import com.nice.core.common.PackageConstant;
import com.nice.core.netty.cocdex.BaseDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ByteBufDecoder extends BaseDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ByteBufDecoder.class);

    public ByteBufDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        String secret_key = "";
        try {
            int length = readLean(in);
            if (length <= 0) {
                return;
            }
            if (in.readableBytes() + PackageConstant.LENGTH_FIELD_LEN < length) {
                in.resetReaderIndex();
            } else {
                ByteBuf message = in.readBytes(length - PackageConstant.LENGTH_FIELD_LEN);
                out.add(message);
            }
        } catch (Exception e) {
            LOGGER.error("decode->secret_key={},emsg={}", secret_key, e.getMessage(), e);
        }
    }
}