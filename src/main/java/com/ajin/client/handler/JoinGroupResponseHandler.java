package com.ajin.client.handler;

import com.ajin.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端接收到服务端加群响应
 *
 * @author ajin
 */

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {

        if (joinGroupResponsePacket.isSuccess()) {

            System.out.println("加入群[" + joinGroupResponsePacket.getGroupId() + "]成功");

        } else {
            System.err.println("加入群[" + joinGroupResponsePacket.getGroupId() +
                    "]失败，原因为：" + joinGroupResponsePacket.getReason());
        }

    }
}
