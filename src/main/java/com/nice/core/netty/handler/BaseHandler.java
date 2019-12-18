package com.nice.core.netty.handler;

import com.nice.core.netty.message.Msg;

/**
 * Created by justin on 14-7-30.
 * 消息处理父类
 * 具体消息处理类都继承它
 */
public abstract class BaseHandler {

    public abstract void process(Msg msg) throws Exception;

}
