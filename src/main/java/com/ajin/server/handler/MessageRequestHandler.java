package com.ajin.server.handler;

import com.ajin.protocol.request.MessageRequestPacket;
import com.ajin.protocol.response.MessageResponsePacket;
import com.ajin.session.Session;
import com.ajin.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 与客户端进行消息传递
 *
 * @author ajin
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler() {
    }

    /**
     * 将 消息发送方 的消息 发送给 消息接收方
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        // 1. 拿到消息发送方的会话消息
        Session session = SessionUtil.getSession(ctx.channel());

        // 2. 通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();

        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 3. 拿到消息接收方的Channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());


        // 4. 把消息发送给消息接收方

        // 判断消息接收方是否在线，如果不在线，则发送失败
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {

            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，消息发送失败");
        }

    }
}
