package com.junlong.windseeker.domain;

import com.junlong.windseeker.domain.enums.QueryCommandEnum;

/**
 * 用户界面查询参数
 * Created by niujunlong on 17/9/28.
 */
public class QueryCommand {
    private QueryCommandEnum queryCommandEnum;
    private String sessionId;
    private String content;
    private String javaPid;
    private String targetIp;
    private int targetPort;

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

    public String getJavaPid() {
        return javaPid;
    }

    public void setJavaPid(String javaPid) {
        this.javaPid = javaPid;
    }

    public QueryCommandEnum getQueryCommandEnum() {
        return queryCommandEnum;
    }

    public void setQueryCommandEnum(QueryCommandEnum queryCommandEnum) {
        this.queryCommandEnum = queryCommandEnum;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
