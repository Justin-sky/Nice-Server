package com.nice.gatway.handler;

import Net.Protol.MsgIDDefineDic;
import com.nice.core.netty.handler.BaseHandler;
import com.nice.core.netty.message.Handler;
import com.nice.core.netty.message.Msg;

@Handler(command = MsgIDDefineDic.LOGIN_REQ_LOGIN)
public class LoginHandler extends BaseHandler {
    @Override
    public void process(Msg msg) throws Exception {

    }
}
