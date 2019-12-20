package com.nice.gatway;
import com.google.protobuf.GeneratedMessage;
import com.nice.core.netty.NettyEventProcessorInterface;
import com.nice.core.netty.message.MessageDispatcher;
import com.nice.core.utils.MessageUtils;
import com.nice.gatway.parser.PacketNetData;
import com.nice.core.netty.session.Session;
import net.protol.MsgIDDefineDic;
import net.protol.common.Common;
import net.protol.login.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyServerEventProcessor implements NettyEventProcessorInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerEventProcessor.class);


    public static Map<Integer, Integer> indexMap = new ConcurrentHashMap<>();


    public NettyServerEventProcessor() {

    }

    @Override
    public void onMessage(Session session, Object message) {
        try {
            PacketNetData packetNetData = (PacketNetData) message;
            int msgId = packetNetData.getMsgId();
            int seq = packetNetData.getSeq();

            //忽略心跳包
            if (msgId == MsgIDDefineDic.COMMON_HEART_BEAT) return;

            Common.client_info client_info = Common.client_info.newBuilder().setUserName("justin").build();
            Login.test1 test1 = Login.test1.newBuilder().setA(1).build();
            Login.rsp_login rsp = Login.rsp_login.newBuilder().
                    setResult(1).setGameTime(3000).setStatus(2).
                    setClientInfo(client_info).
                    setTest(test1).build();


            MessageUtils.sendMessage(session,seq, MsgIDDefineDic.LOGIN_RSP_LOGIN, rsp.toByteArray(),true);

            MessageDispatcher.dispatch(session, packetNetData);

            //转发数据
        } catch (Exception e) {
            LOGGER.error("onMessage->emsg={}", e.getMessage(), e);
        }
    }


    private synchronized boolean syncIndex(int routerId, int index) {
        if(!indexMap.containsKey(routerId)) {
            indexMap.put(routerId, index);
            return true;
        }
        if(indexMap.get(routerId) == index) {
            return false;
        }
        indexMap.put(routerId, index);
        return true;
    }




    @Override
    public void onClose(Session session) {
        SessionManager.removeUser(session);
    }
}
