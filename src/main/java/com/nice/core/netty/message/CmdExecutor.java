package com.nice.core.netty.message;

import java.lang.reflect.Method;

public class CmdExecutor {

    /**
     * logic handler method
     */
    private Method method;
    /**
     * logic controller
     */
    private Object handler;

    public static CmdExecutor valueOf(Method method, Object handler) {
        CmdExecutor executor = new CmdExecutor();
        executor.method = method;
        executor.handler = handler;

        return executor;
    }

    public Method getMethod() {
        return method;
    }


    public Object getHandler() {
        return handler;
    }
}