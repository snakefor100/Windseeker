package com.junlong.windseeker.server.session;

import com.junlong.windseeker.server.netty.TextWebSocketFrameHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

/**
 * 会话管理
 * Created by niujunlong on 17/9/27.
 */
public class DefaultSessionManager {
    private static final Logger LOG = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();


    public static Session newSession() {
        return new DefaultSessionManager.Session();
    }


    public static Session getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }


    public static void closeAll() {

    }

    public static void close(String sessionId) {

    }


    public static class Session {
        /**
         * 本次会话针对的java进程pid
         */
        private int javaPid;

        /**
         * sessionID
         */
        private String sessionId;
        /**
         * session开始时间
         */
        private String sessionStartTime;

        /**
         * session对外传递消息的通道
         */
        private Channel channel;


        /**
         * Instrumentation对象
         */
        private Instrumentation inst;

        public DefaultSessionManager.Session javaPid(int javaPid) {
            this.javaPid = javaPid;
            return this;
        }

        public DefaultSessionManager.Session sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public DefaultSessionManager.Session sessionStartTime(String sessionStartTime) {
            this.sessionStartTime = sessionStartTime;
            return this;
        }

        public DefaultSessionManager.Session channel(Channel channel) {
            this.channel = channel;
            return this;
        }

        public DefaultSessionManager.Session inst(Instrumentation inst) {
            this.inst = inst;
            return this;
        }

        public Session build() {
            sessionMap.put(sessionId, this);
            return this;
        }


        public int getJavaPid() {
            return javaPid;
        }

        public String getSessionId() {
            return sessionId;
        }

        public String getSessionStartTime() {
            return sessionStartTime;
        }

        public Channel getChannel() {
            return channel;
        }

        public Instrumentation getInst() {
            return inst;
        }
    }
}
