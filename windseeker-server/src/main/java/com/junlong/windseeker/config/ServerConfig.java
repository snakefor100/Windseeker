package com.junlong.windseeker.config;

/**
 * Created by niujunlong on 17/9/6.
 */
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "windseeker.serverConfig")
public class ServerConfig {
    private int connectTime;
    private String agentJarUrl;
    private String coreJarUrl;
    private String WsServerLauncherClassUrl;

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

    public String getWsServerLauncherClassUrl() {
        return WsServerLauncherClassUrl;
    }

    public void setWsServerLauncherClassUrl(String wsServerLauncherClassUrl) {
        WsServerLauncherClassUrl = wsServerLauncherClassUrl;
    }
}
