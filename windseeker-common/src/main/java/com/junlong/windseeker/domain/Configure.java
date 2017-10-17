package com.junlong.windseeker.domain;

import java.io.Serializable;

/**
 * Created by niujunlong on 17/9/6.
 */
public class Configure implements Serializable{
    private String targetIp;
    private int targetPort;
    private int javaPid;
    private int connectTime;
    private String agentJarUrl;
    private String coreJarUrl;
    private String wsServerLauncherClassUrl;
    private String sessionId;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getWsServerLauncherClassUrl() {
        return wsServerLauncherClassUrl;
    }

    public void setWsServerLauncherClassUrl(String wsServerLauncherClassUrl) {
        this.wsServerLauncherClassUrl = wsServerLauncherClassUrl;
    }

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public int getJavaPid() {
        return javaPid;
    }

    public void setJavaPid(int javaPid) {
        this.javaPid = javaPid;
    }

    public int getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(int connectTime) {
        this.connectTime = connectTime;
    }

    public String getAgentJarUrl() {
        return agentJarUrl;
    }

    public void setAgentJarUrl(String agentJarUrl) {
        this.agentJarUrl = agentJarUrl;
    }

    public String getCoreJarUrl() {
        return coreJarUrl;
    }

    public void setCoreJarUrl(String coreJarUrl) {
        this.coreJarUrl = coreJarUrl;
    }



}
