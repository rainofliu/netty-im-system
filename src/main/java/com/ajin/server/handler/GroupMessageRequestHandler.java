package com.ajin.server.handler;

import com.ajin.protocol.request.GroupMessageRequestPacket;
import com.ajin.protocol.response.GroupMessageResponsePacket;
import com.ajin.session.Session;
import com.ajin.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 群发消息的处理器
 *
 * @author ajin
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE=new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) throws Exception {

        String toGroupId = requestPacket.getToGroupId();
        String message = requestPacket.getMessage();

        Session session = SessionUtil.getSession(ctx.channel());

        String fromUserId = session.getUserId();
        String fromUserName = session.getUserName();

        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();

        responsePacket.setFromUserId(fromUserId);
        responsePacket.setFromUserName(fromUserName);
        responsePacket.setGroupId(toGroupId);
        responsePacket.setMessage(message);

        ctx.channel().writeAndFlush(responsePacket);
    }
}
