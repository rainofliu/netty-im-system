package com.ajin.client.handler;

import com.ajin.protocol.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 获取群成员响应的客户端处理器
 *
 * @author ajin
 */

public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                ListGroupMembersResponsePacket responsePacket) throws Exception {

        System.out.println("群[" + responsePacket.getGroupId() + "]中的人包括：" + responsePacket.getSessionList());
    }
}
