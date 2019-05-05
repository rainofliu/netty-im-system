package com.ajin.server.handler;

import com.ajin.protocol.request.LoginRequestPacket;
import com.ajin.protocol.response.LoginResponsePacket;
import com.ajin.session.Session;
import com.ajin.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;


/**
 * 处理登录逻辑的处理器(服务端使用）
 *
 * @author ajin
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private LoginRequestHandler() {
    }

    @SuppressWarnings("all")
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {


        // 响应对象
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        // 登录校验
        if (valid(loginRequestPacket)) {

            // 校验成功
            loginResponsePacket.setSuccess(true);

            String userId = randomUserId();

            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");

            // 标记已经登录成功
            SessionUtil.bindSession(
                    new Session(
                            userId, loginRequestPacket.getUserName()
                    ),
                    ctx.channel()
            );
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }


        // 登录响应 ，直接传Packet即可，不用传ByteBuf对象
        ctx.channel().writeAndFlush(loginResponsePacket);

    }

    /**
     * 随机生成一个用户ID
     */
    private static String randomUserId() {

        return UUID.randomUUID().toString().split("-")[0];
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
