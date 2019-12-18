package com.nice.gatway.cocdex;

import com.nice.gatway.SessionManager;
import com.nice.core.netty.cocdex.PacketNetData;
import com.nice.core.netty.cocdex.Tea;
import com.nice.core.utils.ProtoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class TeaDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeaDecoder.class);

    public TeaDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        String secret_key = "";
        try {
            in.markReaderIndex();
            final byte[] buf = new byte[2];
            for (int i = 0; i < buf.length; i++) {
                if (!in.isReadable()) {
                    in.resetReaderIndex();
                    return;
                }

                buf[i] = in.readByte();
            }
            int length = Tea.byteToShort(buf, 0);
            if (length == 0) {
                LOGGER.debug("decode->receive empty heartbeat parackage");
                return;
            }
            int encodeLen = (length + 7) / 8 * 8;
            if (in.readableBytes() < encodeLen) {
                in.resetReaderIndex();
            } else {
                ByteBuf message = in.readBytes(encodeLen);
                int[] secretKey = SessionManager.getSecretKey(ctx);
                PacketNetData packetNetData = Tea.decrypt3(length, message.array(), 0, secretKey);
                String msgType = ProtoUtil.allMsgMap.get(packetNetData.getMsgId());
                if (msgType == null) {
                    packetNetData = Tea.decrypt3(length, message.array(), 0, Tea.KEY);
                    LOGGER.error(Arrays.toString(Tea.KEY));
                    LOGGER.error("ÃØÔ¿´íÎó£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡" + Arrays.toString(secretKey));
                    msgType = ProtoUtil.allMsgMap.get(packetNetData.getMsgId());
                    if (msgType == null) {
                        LOGGER.error("msgId={},key={},channel={}", packetNetData.getMsgId(), Tea.KEY, ctx.channel().remoteAddress().toString());
                    } else {
                        LOGGER.error("msgId={},key={},channel={}", packetNetData.getMsgId(), Tea.KEY, ctx.channel().remoteAddress().toString());
                    }

                }

                out.add(packetNetData);
            }
        } catch (Exception e) {
            LOGGER.error("decode->secret_key={},emsg={}", secret_key, e.getMessage(), e);
        }
    }
}
