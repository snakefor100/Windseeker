package com.junlong.windseeker.agent;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

/**
 * Created by niujunlong on 17/9/4.
 */
public class AgentLauncher {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 随着监听程序一起启动的agent
     */
    public static void premain(String args, Instrumentation inst) {
        System.out.println("test");
        System.out.println(args);
        System.out.println(inst);
    }

    /**
     * agent延后启动
     */
    public static void agentmain(String args, Instrumentation inst) {
        final String agentJarUrl = "/Users/didi/workspace/Windseeker/windseeker-agent/target/windseeker-agent-jar-with-dependencies.jar";
        try {
            AgentClassLoader agentClassLoader = new AgentClassLoader(agentJarUrl);
            System.out.println(agentJarUrl);
            inst.appendToBootstrapClassLoaderSearch(new JarFile(AgentLauncher.class.getProtectionDomain().getCodeSource().getLocation().getFile()));
            Class<?> aClass = agentClassLoader.loadClass("com.junlong.windseeker.service.VmServer");
            Object getInstance = aClass.getMethod("getInstance", int.class, Instrumentation.class).invoke(null, 123, inst);
            System.out.println("111111111111");
            System.out.println(objectMapper.writeValueAsString(getInstance));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("222222222222");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            System.out.println(e.getLocalizedMessage());

        }

    }


}
