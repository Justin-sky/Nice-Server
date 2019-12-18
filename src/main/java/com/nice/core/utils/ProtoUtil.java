package com.nice.core.utils;

import Net.Protol.MsgIDDefineDic;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ProtoUtil {
    public static Map<Integer, String> allMsgMap = new HashMap<>();

    public static void getAllMsg() throws IllegalAccessException {
        Field[] fields = MsgIDDefineDic.class.getDeclaredFields();
        for (Field field : fields) {
            int msgId = field.getInt(MsgIDDefineDic.class);
            if (allMsgMap.containsKey(msgId)) {
                throw new RuntimeException("msgId repeat!!!! msgId=" + msgId + " msgName=" + field.getName());
            }
            allMsgMap.put(msgId, field.getName());
        }
    }
}
