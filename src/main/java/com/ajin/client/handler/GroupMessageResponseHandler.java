package com.ajin.client.handler;

import com.ajin.protocol.response.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ajin
 */

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                GroupMessageResponsePacket responsePacket) throws Exception {

        String groupId = responsePacket.getGroupId();


        String fromUserId = responsePacket.getFromUserId();
        String fromUserName = responsePacket.getFromUserName();
        String message = responsePacket.getMessage();


        System.out.print("收到群[" + groupId + "]中[" + fromUserId + ":" + fromUserName + "]发来的消息：" + message);
    }
}
