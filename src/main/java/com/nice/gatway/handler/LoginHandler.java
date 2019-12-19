package com.nice.gatway.handler;
import com.nice.core.netty.handler.BaseHandler;
import com.nice.core.netty.message.Handler;
import com.nice.core.netty.message.Msg;
import com.nice.core.netty.session.Session;
import net.protol.MsgIDDefineDic;

@Handler(command = MsgIDDefineDic.LOGIN_REQ_LOGIN)
public class LoginHandler extends BaseHandler {
    @Override
    public void process(Session session, byte[] proto) throws Exception {

        byte[] a = proto;


    }
}
