package com.junlong.windseeker.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by niujunlong on 17/9/17.
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MAX_CONTENT_LENGTH = 1024 * 10;
    private static final String SOCKET_PATH="/websocket";
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(MAX_CONTENT_LENGTH));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new TextWebSocketFrameHa());
        pipeline.addLast(new HttpRequestHan());
        pipeline.addLast(new WebSocketServerProtocolHandler(SOCKET_PATH));


//        pipeline.addLast(new HttpRequestDecoder());
//        pipeline.addLast(new HttpObjectAggregator(MAX_CONTENT_LENGTH));
//        pipeline.addLast(new HttpResponseEncoder());
//        pipeline.addLast(new WebsocketServerHandler())

    }
}
