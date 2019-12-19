package com.nice.gatway.cocdex;
import com.nice.core.common.PackageConstant;
import com.nice.core.netty.cocdex.BaseDecoder;
import com.nice.gatway.parser.PacketNetData;
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
        try {
            int length = readLen(in);
            if (length <= 0) {
                return;
            }
            int l = in.readableBytes();
            if (in.readableBytes() < length) {
                in.resetReaderIndex();
            } else {
                byte[] data = new byte[length];
                in.readBytes(data);

                PacketNetData packet = new PacketNetData();
                packet.setPacketData(data);

                out.add(packet);
            }
        } catch (Exception e) {
            LOGGER.error("decode->emsg={}", e.getMessage(), e);
        }
    }
}