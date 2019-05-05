package com.ajin.client.handler;

import com.ajin.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ajin
 */

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        System.out.print("群创建成功，id为 ：" + createGroupResponsePacket.getGroupId());
        System.out.print("群里面有：" + createGroupResponsePacket.getUserNameList());
    }
}
