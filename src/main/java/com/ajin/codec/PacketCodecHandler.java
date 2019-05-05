package com.ajin.codec;

import com.ajin.protocol.Packet;
import com.ajin.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * 通过{@link MessageToMessageCodec}编解码操作放到一个类里面
 *
 * @author ajin
 */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {

    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {

        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();

        PacketCodeC.INSTANCE.encode(byteBuf, packet);

        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {

        out.add(PacketCodeC.INSTANCE.decode(byteBuf));
    }
}
