package com.junlong.windseeker.server.netty;

import com.junlong.windseeker.domain.Configure;
import com.junlong.windseeker.server.session.DefaultSessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by niujunlong on 17/9/19.
 */
public class WebSocketService {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketService.class);

    public static void start(Configure configure, Instrumentation inst) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new WebSocketServerChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            LOG.info("WS 服务器已启动!");
            ChannelFuture f = serverBootstrap.bind(configure.getTargetIp(), configure.getTargetPort()).sync();
            DefaultSessionManager.newSession().javaPid(configure.getJavaPid())
                    .sessionId(configure.getSessionId()).inst(inst).build();
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            LOG.error("WS 服务发生异常", e);
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            LOG.info("WS 服务器已关闭!");
        }
    }


}
