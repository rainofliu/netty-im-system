package com.ajin.client.handler;

import com.ajin.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 读取服务端发的消息
 *
 * @author ajin
 */

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {


    /**
     * 获取服务端转发的 来自 消息发送方 的消息,并回复
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                MessageResponsePacket messageResponsePacket) throws Exception {

        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + "->" + messageResponsePacket.getMessage());
    }
}
