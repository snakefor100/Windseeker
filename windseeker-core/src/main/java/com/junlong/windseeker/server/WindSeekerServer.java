package com.junlong.windseeker.server;

import com.junlong.windseeker.domain.AppConstants;
import com.junlong.windseeker.domain.Configure;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.channels.SelectionKey.OP_ACCEPT;

/**
 * 参考:http://ifeve.com/selectors/
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
    public WindSeekerServer(final int javaPid, final Instrumentation instrumentation) {

        Runtime.getRuntime().addShutdownHook(jvmShutdownHooker);
    }
    public WindSeekerServer() {

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
            //打开一个channel
            serverSocketChannel = ServerSocketChannel.open();
            //创建一个selector
            selector = Selector.open();
            //将channel注册到selector上,channel必须设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            //设置连接超时时长 配置参见: http://blog.csdn.net/woshisap/article/details/6597413
            serverSocketChannel.socket().setSoTimeout(configure.getConnectTime());
            serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.register(selector, OP_ACCEPT);

            // 服务器挂载端口
            serverSocketChannel.socket().bind(getInetSocketAddress(configure.getTargetIp(), configure.getTargetPort()), 24);

            LOG.info("WindSeekerServer 服务端监听已启动,配置 {}", configure);

            activeSelectorDaemon(selector, configure);

        } catch (IOException e) {
//            unbind();
            throw e;
        }

    }

    private void activeSelectorDaemon(final Selector selector, Configure configure) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(AppConstants.DEFAULT_BUFFER_SIZE_4M);
        Thread daemon = new Thread("windseeker-selector-daemon") {
            @Override
            public void run() {
                System.out.println(999);
                System.out.println(selector.isOpen());
                System.out.println(888);
                while (!isInterrupted()){
                    while (selector.isOpen()) {
                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        while (iterator.hasNext()) {
                            SelectionKey next = iterator.next();
                            if (next.isValid() && next.isAcceptable()) {
                                System.out.println(111);
                            }
                            if (next.isValid() && next.isReadable()) {
                                System.out.println(222);
                            }
                            if (next.isValid() && next.isWritable()) {
                                System.out.println(333);
                            }
                            System.out.println(555);
                        }
                    }
                }


            }
        };
        daemon.setDaemon(true);
        daemon.start();
        System.out.println(123);
    }


    /**
     * 如果传入的是本地127.0.0.1地址,则绑定InetAddress.anyLocalAddress() 所有网卡,否则外网无法访问
     */
    private InetSocketAddress getInetSocketAddress(String targetIp, int targetPort) {
        if (StringUtils.equals(AppConstants.LOCAL_HOST, targetIp)) {
            return new InetSocketAddress(targetPort);
        } else {
            return new InetSocketAddress(targetIp, targetPort);
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
//        if (isBind()) {
//            unbind();
//        }
//
//        if (!sessionManager.isDestroy()) {
//            sessionManager.destroy();
//        }
//
//        executorService.shutdown();
    }
}
