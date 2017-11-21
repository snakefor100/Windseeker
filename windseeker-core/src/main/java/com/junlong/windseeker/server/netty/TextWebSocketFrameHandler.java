package com.junlong.windseeker.server.netty;

import com.junlong.windseeker.command.Command;
import com.junlong.windseeker.command.impl.JvmCommand;
import com.junlong.windseeker.command.impl.SearchClassCommand;
import com.junlong.windseeker.command.impl.TraceCommand;
import com.junlong.windseeker.command.session.SessionCommand;
import com.junlong.windseeker.domain.AppConstants;
import com.junlong.windseeker.domain.QueryCommand;
import com.junlong.windseeker.server.session.DefaultSessionManager;
import com.junlong.windseeker.utils.JsonUtils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 文本处理
 * Created by niujunlong on 17/9/19.
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger LOG = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);


    /**
     * 总的channel数量
     */
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
       try {
           Channel incoming = channelHandlerContext.channel();
           LOG.error("入参详情 {}",textWebSocketFrame.text());
           QueryCommand queryCommand;
           try {
               queryCommand = JsonUtils.toObject(textWebSocketFrame.text(), QueryCommand.class);
           } catch (Exception e) {
               LOG.error("json 转换异常,不做处理",e);
               return;
           }
           LOG.error("command详情 {}",queryCommand);

           DefaultSessionManager.Session session = DefaultSessionManager.getSession(queryCommand.getSessionId());
           if (null == session) {
               //// TODO: 17/9/29 先不考虑多用户情况,后续添加
               LOG.error("查不到session记录");
           }
           LOG.error("session详情 {}",session);

           //如果是第一次连接
           if (StringUtils.isBlank(session.getSessionStartTime())) {
               LOG.info("设置session的开始时间以及channel {}",session);
               session.sessionStartTime(new DateTime().toString(AppConstants.DATA_FORMAT_TIME))
                       .channel(incoming).build();
           }
           LOG.info("SESSION: {}",session);

           Command command = null;
           switch (queryCommand.getQueryCommandEnum()) {
               case SESSION:
                   command = new SessionCommand();
               case JVM:
                   command = new JvmCommand();
               case SEARCH_CLASS:
                   command = new SearchClassCommand(queryCommand.getContent());
               case TRACE:
                   command = new TraceCommand();
               default:
                   break;

           }
           LOG.info("开始执行");
           command.getAction().doAction(session);
       }catch (Exception e){
           LOG.info("报错啦~");
       }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("发生异常: ",cause);
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush(new TextWebSocketFrame("[在线]" + incoming.remoteAddress()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        incoming.writeAndFlush(new TextWebSocketFrame("[掉线]" + incoming.remoteAddress()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
        }
        channels.add(incoming);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
        }
        channels.remove(ctx.channel());
    }
}
