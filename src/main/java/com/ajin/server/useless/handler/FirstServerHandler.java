package com.ajin.server.useless.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 服务端逻辑处理器， 负责读取客户端发来的数据
 *
 * @author: ajin
 */

public class FirstServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * @param msg Netty里面数据读写的载体
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        // 1. 服务端读取客户端发送的数据
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + " : 服务端读到数据 -> "
                + byteBuf.toString(Charset.forName("utf-8")));

//        // 2. 服务端写出数据到客户端
//        System.out.println(new Date() + ": 服务端写出数据");
//
//        ByteBuf buf = getByteBuf(ctx);
//
//        ctx.channel().writeAndFlush(buf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        byte[] bytes = "你好，我是正在学习Netty的刘天若，很高兴给你发消息 ！"
                .getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
