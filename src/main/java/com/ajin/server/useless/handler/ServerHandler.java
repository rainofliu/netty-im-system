package com.ajin.server.useless.handler;

import com.ajin.protocol.Packet;
import com.ajin.protocol.PacketCodeC;
import com.ajin.protocol.request.LoginRequestPacket;
import com.ajin.protocol.request.MessageRequestPacket;
import com.ajin.protocol.response.LoginResponsePacket;
import com.ajin.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 与登录相关的服务端{@link ServerHandler}逻辑处理器
 *
 * @author: ajin
 */

public class ServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf requestByteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);


        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            System.out.println(new Date() + ": 收到客户端登录请求……");
            // 登录流程
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            // 响应对象
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            // 登录校验
            if (valid(loginRequestPacket)) {
                // 校验成功
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": 登录成功!");
            } else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + ": 登录失败!");
            }

            // 编码
//            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);

            // 将服务端的登录响应发送给客户端
//            ctx.channel().writeAndFlush(responseByteBuf);

        } else if (packet instanceof MessageRequestPacket) {

            // 客户端发来消息
            MessageRequestPacket messageRequestPacket = ((MessageRequestPacket) packet);

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

//            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);


//            ctx.channel().writeAndFlush(responseByteBuf);

        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
