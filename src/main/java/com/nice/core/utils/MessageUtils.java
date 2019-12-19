package com.nice.core.utils;

import com.google.protobuf.GeneratedMessage;
import com.nice.core.common.PackageConstant;
import com.nice.core.netty.message.Msg;
import com.nice.core.netty.session.Session;
import com.nice.gatway.parser.PacketNetData;
import com.nice.gatway.cocdex.Tea;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtils.class);



    public static byte[] SerializeMessage(int seq, int msgId, byte[] protobytes){
        int len = 8+ protobytes.length;
        byte[] bytes = new byte[len];

        byte[] seqs = ByteUtil.intToBytes(seq);
        System.arraycopy(seqs,0, bytes,0,4);

        byte[] msgids = ByteUtil.intToBytes(msgId);
        System.arraycopy(msgids,0, bytes, 4,4);

        System.arraycopy(protobytes,0, bytes, 8, protobytes.length);

        return bytes;
    }

    public static void sendMessage(Session session,int seq, int msgId, byte[] protobytes ,boolean flush) throws Exception {


        byte[] bytes = SerializeMessage(seq, msgId, protobytes);
        if (flush) {
            session.writeAndFlush(Unpooled.wrappedBuffer(bytes));
        } else {
            session.write(Unpooled.wrappedBuffer(bytes));
        }

    }




}
