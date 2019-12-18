package com.nice.gatway;
import com.nice.core.netty.NettyEventProcessorInterface;
import com.nice.gatway.parser.PacketNetData;
import com.nice.core.netty.session.Session;
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
            int routerId = packetNetData.getRouterId();
            int msgId = packetNetData.getMsgId();
            int routerType = packetNetData.getRouterType();
            int token = packetNetData.getToken();
            int indicationIndex = packetNetData.getIndicationIndex();
            int index = packetNetData.getIndex();

            if(!GatewayApp.start) {

                return;
            }


            LOGGER.error("onMessage->uid={},msyId={},routerType={},indicationIndex={},index={}", routerId, msgId, routerType, indicationIndex, index);

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
