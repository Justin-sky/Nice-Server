package com.nice.gatway.parser;
import com.google.protobuf.GeneratedMessage;
import com.nice.core.common.PackageConstant;
import com.nice.core.netty.session.Session;
import com.nice.core.utils.ProtoUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageSendMgr {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSendMgr.class);

    public static ByteBuf wrappedBuffer(int uid, int index, int result, int msgId, GeneratedMessage generatedMessage) {
        byte[] byMessage;
        if (generatedMessage == null) {
            byMessage = new byte[0];
        } else {
            byMessage = generatedMessage.toByteArray();
        }
        int length = byMessage.length + PackageConstant.UID_FIELD_LEN
                + PackageConstant.MSG_ID_FIELD_LEN + PackageConstant.LENGTH_FIELD_LEN
                + PackageConstant.RESULT_LEN + PackageConstant.INDEXT_FIELD_LEN;
        byte[] bytes = new byte[length];
        int pos = 0;
        Tea.shortToByte(bytes, pos, length);
        pos += PackageConstant.LENGTH_FIELD_LEN;
        Tea.intToByte(bytes, pos, uid);
        pos += PackageConstant.UID_FIELD_LEN;
        Tea.intToByte(bytes, pos, msgId);
        pos += PackageConstant.MSG_ID_FIELD_LEN;
        Tea.intToByte(bytes, pos, result);
        pos += PackageConstant.RESULT_LEN;
        Tea.intToByte(bytes, pos, index);
        pos += PackageConstant.INDEXT_FIELD_LEN;
        if (byMessage.length != 0) {
            System.arraycopy(byMessage, 0, bytes, pos, byMessage.length);
        }
        return Unpooled.wrappedBuffer(bytes);
    }




    private static void sendMessage(Session session, int uid, int index, int result, int msgId, GeneratedMessage generatedMessage, boolean flush) throws Exception {
        if (msgId != 10009) {
            LOGGER.info("sendMessage->uid={},msg={},result={}", uid, ProtoUtil.allMsgMap.get(msgId), result);
        }
        ByteBuf byteBuf = wrappedBuffer(uid, index, result, msgId, generatedMessage);
        if (flush) {
            session.writeAndFlush(byteBuf);
        } else {
            session.write(byteBuf);
        }
    }
}
