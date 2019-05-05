package com.ajin.server.handler;

import com.ajin.utils.LoginUtil;
import com.ajin.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 用户认证handler
 *
 * @author ajin
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE=new AuthHandler();

    private AuthHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 如果没有登录
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {

            // 如果已经登录 ,那么就从处理器链中移除当前处理器
            // 删除之后，这条客户端连接的逻辑链中就不再有这段逻辑了。
            ctx.pipeline().remove(this);

            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        if (SessionUtil.hasLogin(ctx.channel())) {

            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");

        } else {

            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
