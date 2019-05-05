package com.ajin.server.handler;

import com.ajin.protocol.request.ListGroupMembersRequestPacket;
import com.ajin.protocol.response.ListGroupMembersResponsePacket;
import com.ajin.session.Session;
import com.ajin.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ajin
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket) throws Exception {

        // 1. 获取群的 channelGroup
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        // 2. 遍历群成员的channel
        List<Session> sessionList = new ArrayList<>();

        for (Channel channel : channelGroup) {

            Session session = SessionUtil.getSession(channel);

            sessionList.add(session);
        }

        // 3. 构造相应对象
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();

        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);

        ctx.channel().writeAndFlush(responsePacket);

    }
}
