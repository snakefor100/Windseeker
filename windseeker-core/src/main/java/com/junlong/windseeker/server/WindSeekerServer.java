package com.junlong.windseeker.server;

import com.junlong.windseeker.domain.Configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.channels.SelectionKey.OP_ACCEPT;

/**
 * Created by niujunlong on 17/9/6.
 */
public class WindSeekerServer {
    private static final Logger LOG = LoggerFactory.getLogger(WindSeekerServer.class);

    private static volatile WindSeekerServer windSeekerServer;

    private ServerSocketChannel serverSocketChannel = null;
    private Selector selector = null;

    /**
     * 判断当前应用的agent是否开启过server
     */
    private final AtomicBoolean bindStatus = new AtomicBoolean(false);
    /**
     * 创建守护线程池
     */
    private final ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            final Thread t = new Thread(r, "windseeker-server-handler-daemon");
            t.setDaemon(true);
            return t;
        }
    });


    //钩子函数,关闭server
    private final Thread jvmShutdownHooker = new Thread("windseeker-server-hooker") {

        @Override
        public void run() {
            WindSeekerServer.this._destory();
        }
    };

/***************************  业务方法  *********************************************/
    /**
     * 构造防范
     */
    private WindSeekerServer(final int javaPid, final Instrumentation instrumentation) {

        Runtime.getRuntime().addShutdownHook(jvmShutdownHooker);
    }


    public static WindSeekerServer getInstance(final int javaPid, final Instrumentation instrumentation) {
        if (null == windSeekerServer) {
            synchronized (WindSeekerServer.class) {
                if (null == windSeekerServer) {
                    windSeekerServer = new WindSeekerServer(javaPid, instrumentation);
                }
            }
        }

        return windSeekerServer;
    }

    public void startServer(Configure configure) throws IOException {
        if (!bindStatus.compareAndSet(false, true)) {
            throw new IllegalStateException("WindSeekerServer is start!");
        }

        try {

            serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().setSoTimeout(configure.getConnectTimeout());
            serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.register(selector, OP_ACCEPT);

            // 服务器挂载端口
            serverSocketChannel.socket().bind(getInetSocketAddress(configure.getTargetIp(), configure.getTargetPort()), 24);
            logger.info("ga-server listening on network={};port={};timeout={};", configure.getTargetIp(),
                    configure.getTargetPort(),
                    configure.getConnectTimeout());

            activeSelectorDaemon(selector, configure);

        } catch (IOException e) {
            unbind();
            throw e;
        }

    }


    /**
     * 外部关闭
     */
    public void destroy() {
        Runtime.getRuntime().removeShutdownHook(jvmShutdownHooker);
        _destory();

    }

    /**
     * 内部关闭
     */
    private void _destory() {
        if (isBind()) {
            unbind();
        }

        if (!sessionManager.isDestroy()) {
            sessionManager.destroy();
        }

        executorService.shutdown();
    }
}
