package com.nice.gatway;

import com.nice.core.utils.CtxUtil;
import com.nice.core.netty.cocdex.Tea;
import com.nice.core.netty.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);

    private final static int MAX_ONLINE_PLAYER_NUM = 10000; //允许的最大在线人数
    private static Map<Integer, Session> sessionMap = new ConcurrentHashMap<>(MAX_ONLINE_PLAYER_NUM);
    public static ChannelGroup allChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static class SessionData {
        int uid;
        int[] secretKey;
    }

    public static int[] getSecretKey(ChannelHandlerContext ctx) {
        Session session = CtxUtil.getSession(ctx);
        SessionData sessionDate = (SessionData) session.getData();
        if (sessionDate == null || sessionDate.secretKey == null) {
            return Tea.KEY;
        }
        return sessionDate.secretKey;
    }

    public static SessionData getSessionData(Session session) {
        SessionData sessionDate = (SessionData) session.getData();
        if (sessionDate == null) {
            sessionDate = new SessionData();
            session.setData(sessionDate);
            CtxUtil.setSession(session.getCtx(), session);
        }
        return sessionDate;
    }

    public static void updateSecretKey(Session session, int[] secretKey) {
        SessionData sessionDate = getSessionData(session);
        sessionDate.secretKey = secretKey;
    }

    public static Session getSession(int uid) {
        return sessionMap.get(uid);
    }

    public static Map<Integer, Session> getAllSession() {
        return sessionMap;
    }

    public static void addSession(Session session, int uid) {
        LOGGER.info("addSession->uid={}", uid);
        SessionData sessionDate = getSessionData(session);
        sessionDate.uid = uid;
        sessionMap.put(uid, session);
        allChannels.add(session.getChannel());
    }

    public static void removeUser(int uid) {
        LOGGER.info("removeSession->pid={}", uid);
        Session session = sessionMap.get(uid);
        if (session != null) {
            allChannels.remove(session.getChannel());
            sessionMap.remove(uid);
            session.close();
        }
    }

    public static void removeUser(Session session) {
        if (session == null) {
            return;
        }
        SessionData sessionDate = (SessionData) session.getData();
        if (sessionDate == null) {
            return;
        }

        LOGGER.info("removeUser->pid={}", sessionDate.uid);
        removeUser(sessionDate.uid);

    }

    public static void write(int uid, Object msg) {
        Session session = sessionMap.get(uid);
        if (session != null) {
            session.write(msg);
        }
    }

    public static void writeAndFlush(int uid, Object msg) {
        Session session = sessionMap.get(uid);
        if (session != null) {
            session.writeAndFlush((byte[]) msg);
        }
    }

    public static void write(int uid, Object msg, boolean flush) {
        Session session = sessionMap.get(uid);
        if (session != null) {
            session.write(msg, flush);
        }
    }

    public static void sendAll(Object msg, boolean flush) {
        if (flush) {
            allChannels.writeAndFlush((byte[]) msg);
        } else {
            allChannels.write((byte[]) msg);
        }
    }
}
