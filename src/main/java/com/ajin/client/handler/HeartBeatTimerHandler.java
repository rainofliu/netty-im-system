package com.ajin.client.handler;

import com.ajin.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * 客户端定时发心跳到服务端
 *
 * @author ajin
 */

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor()// 返回当前channel绑定的NIO线程
                //定时任务
                .schedule(() -> {

                    if (ctx.channel().isActive()) {
                        ctx.writeAndFlush(new HeartBeatRequestPacket());
                        scheduleSendHeartBeat(ctx);
                    }

                }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
