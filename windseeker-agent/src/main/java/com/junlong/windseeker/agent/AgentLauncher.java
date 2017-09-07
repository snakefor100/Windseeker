package com.junlong.windseeker.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junlong.windseeker.domain.Configure;

import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

/**
 * Created by niujunlong on 17/9/4.
 */
public class AgentLauncher {
    // 全局持有classloader用于隔离greys实现
    private static volatile ClassLoader wsClassLoader;


    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 随着监听程序一起启动的agent,暂不支持
     */
    public static void premain(String args, Instrumentation inst) {

    }

    /**
     * agent延后启动
     */
    public static void agentmain(String args, Instrumentation inst) {
        System.out.println(args);
        attachAgent(args, inst);
    }


    public static synchronized void attachAgent(String args, Instrumentation inst) {

        try {
            Configure configure = objectMapper.readValue(args, Configure.class);
            System.out.println(9999999);
            System.out.println(configure.getCoreJarUrl());
//            //加载core包到类加载器
//            final ClassLoader classLoader = loadWSClassLoader(configure.getCoreJarUrl());
//            //windseekerService Class全路径
//            final Class<?> wsServerClass = classLoader.loadClass(configure.getWsServerLauncherClassUrl());
//            //windseekerService 实例
//            final Object wsServerInstance = wsServerClass.getMethod("getInstance", Configure.class, Instrumentation.class).invoke(null, configure, inst);
//            //windseeker server 是否启动
//            final boolean isBind = (Boolean) wsServerClass.getMethod("isBind").invoke(wsServerInstance);
//
//            if (!isBind) {
//                wsServerClass.getMethod("bind", Configure.class).invoke(wsServerInstance, configure);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成自定义类加载器
     */
    private static ClassLoader loadWSClassLoader(String agentJar) throws Exception {
        if (wsClassLoader == null) {
            final ClassLoader agentClassLoader = new AgentClassLoader(agentJar);
            wsClassLoader = agentClassLoader;
        }

        return wsClassLoader;
    }


}
