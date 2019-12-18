package com.nice.core.netty.message;

import com.nice.core.common.ClassScanner;
import com.nice.core.netty.session.Session;
import com.nice.core.utils.ProtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MessageDispatcher {
    private static Logger LOGGER = LoggerFactory.getLogger(MessageDispatcher.class);
    private static final Map<Integer, CmdExecutor> CMD_HANDLERS = new HashMap<>();
    public static final Map<String, Integer> MSG_ID_NUM = new HashMap<>();

    public static void initialize(String packageName) {
        Set<Class<?>> messageCommandClasses = ClassScanner.listClassesWithAnnotation(packageName, Handler.class);

        for (Class<?> cls : messageCommandClasses) {
            try {
                Object handler = cls.newInstance();
                Method method = cls.getMethod("process", Msg.class);
                int msgId = cls.getAnnotation(Handler.class).command();
                CmdExecutor cmdExecutor = CMD_HANDLERS.get(msgId);
                if (cmdExecutor != null) {
                    throw new RuntimeException(String.format("cmd[%d] duplicated", msgId));
                }
                cmdExecutor = CmdExecutor.valueOf(method, handler);
                CMD_HANDLERS.put(msgId, cmdExecutor);
            } catch (Exception e) {
                LOGGER.error("initialize error:", e);
            }
        }

    }

    public static void dispatch(Session session, int msgId, int uid, byte[] bytes, int index) throws Exception {
        String msgInfo = ProtoUtil.allMsgMap.get(msgId);
        Integer msg_num = MSG_ID_NUM.get(msgInfo);
        if (msg_num == null) {
            msg_num = 1;
            MSG_ID_NUM.put(msgInfo, msg_num);
        } else {
            MSG_ID_NUM.put(msgInfo, ++msg_num);
        }
        LOGGER.error("dispatch->uid={}, msgId={},msg={}", uid, msgId, msgInfo);
        CmdExecutor cmdExecutor = CMD_HANDLERS.get(msgId);
        if (cmdExecutor == null) {
            LOGGER.error("message executor missed,cmd={}", msgId);
            return;
        }

        Object handler = cmdExecutor.getHandler();
        //应该在此处提交到其它线程处理
        Msg msg = new Msg(session, bytes, uid, index);
        try {
            cmdExecutor.getMethod().invoke(handler, msg);

        }  catch (InvocationTargetException e) {
            LOGGER.error("message executor error,cmd={},msg={}", msgId, e.getTargetException().getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("message executor error,cmd={},msg={}", msgId, e.getMessage(), e);
        }
    }

}