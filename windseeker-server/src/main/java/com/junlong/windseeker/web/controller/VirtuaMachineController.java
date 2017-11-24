package com.junlong.windseeker.web.controller;

import com.junlong.windseeker.config.ServerConfig;
import com.junlong.windseeker.domain.Configure;
import com.junlong.windseeker.utils.VirtuaMachineUtils;
import com.junlong.windseeker.web.response.BaseResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by niujunlong on 17/9/6.
 */
@RestController
@RequestMapping("/vm")
public class VirtuaMachineController {
    @Resource
    private ServerConfig serverConfig;

    @GetMapping(value = "/attach")
    public BaseResponse attachTargetServer(String targetIp,int targetPort,int javaPid) throws Exception {

        Configure configure = new Configure();
        configure.setSessionId("niuniu");
        configure.setJavaPid(javaPid);
        configure.setTargetIp(targetIp);
        configure.setTargetPort(targetPort);
        configure.setAgentJarUrl(serverConfig.getAgentJarUrl());
        configure.setConnectTime(serverConfig.getConnectTime());
        configure.setCoreJarUrl(serverConfig.getCoreJarUrl());
        configure.setJavaPid(javaPid);
        configure.setWsServerLauncherClassUrl(serverConfig.getWsServerLauncherClassUrl());
        VirtuaMachineUtils.loadAgent(configure);
        return BaseResponse.build(null);
    }

    @RequestMapping(value = "/close")
    public BaseResponse closeTargetServer(){
        VirtuaMachineUtils.close(9999);
        return BaseResponse.build(null);
    }
}
