package com.junlong.windseeker.agent;

import com.junlong.windseeker.domain.Configure;
import com.junlong.windseeker.server.netty.WebSocketService;
import com.junlong.windseeker.utils.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * Created by niujunlong on 17/9/4.
 */
public class AgentLauncher {
    private static final Logger LOG = LoggerFactory.getLogger(AgentLauncher.class);


    // 全局持有classloader
    private static volatile ClassLoader wsClassLoader;


    /**
     * 随着监听程序一起启动的agent,暂不支持
     */
    public static void premain(String args, Instrumentation inst) {

    }

    /**
     * agent延后启动
     */
    public static void agentmain(String args, Instrumentation inst) {
        attachAgent(args, inst);
    }


    public static synchronized void attachAgent(String args, Instrumentation inst) {

        try {

            Configure configure = JsonUtils.toObject(args, Configure.class);

//            //加载core包到类加载器
//            final ClassLoader classLoader = loadWSClassLoader(configure.getCoreJarUrl());
//            //windseekerService Class全路径
//            final Class<?> wsServerClass = classLoader.loadClass(configure.getWsServerLauncherClassUrl());
//            LOG.info("查看args: {}",configure);
//            LOG.info("查看string: {}",args);
//
//            LOG.info("加载jar: {}",configure.getCoreJarUrl());
//
//            LOG.info("查看类加载起:{}",JsonUtils.toString(classLoader));
//            LOG.info("查看:{}",JsonUtils.toString(scanPackage(classLoader,"/")));

            WebSocketService.start(configure,inst);

//            wsServerClass.getMethod("start", Configure.class, Instrumentation.class).invoke(null, configure, inst);


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
