package com.junlong;

import com.junlong.windseeker.domain.Configure;
import com.junlong.windseeker.server.WindSeekerServer;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niujunlong on 17/9/8.
 */
public class TestSockServer {
    public static void main(String[] args) throws IOException, InterruptedException {
//        WindSeekerServer a = new WindSeekerServer();
//        Configure configure = new Configure();
//        configure.setConnectTime(5000);
//        configure.setTargetPort(8156);
//        configure.setTargetIp("192.168.1.107");
//
//        a.startServer(configure);
//        Thread.sleep(1000*1000);

        List<String> a = null;
        System.out.println(StringUtils.join(a,","));
    }
}
