package com.ajin.client.useless.handler;

import com.ajin.client.NettyClient;
import com.ajin.protocol.Packet;
import com.ajin.protocol.PacketCodeC;
import com.ajin.protocol.request.LoginRequestPacket;
import com.ajin.protocol.response.LoginResponsePacket;
import com.ajin.protocol.response.MessageResponsePacket;
import com.ajin.utils.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * 登录相关的客户端{@link NettyClient}逻辑处理器
 *
 * @author: ajin
 */

public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 与服务端连接成功后调用该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + " :客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("ajin");
        loginRequestPacket.setPassword("root");

        // 编码
//        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 写出数据到服务端
//        ctx.channel() // 获取当前连接的抽象
//                .writeAndFlush(buffer);


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }

        } else if (packet instanceof MessageResponsePacket) {

            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;

            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        }
    }
}
