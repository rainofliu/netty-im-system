package com.ajin.codec;

import com.ajin.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 实现Netty提供的{@link ByteToMessageDecoder} 将二进制数据转化成Java对象
 * <p>用来进行服务端的解码</p>
 *
 * @author ajin
 */

public class PacketDecoder extends ByteToMessageDecoder {

    /**
     * <h2>Netty 4.1.6.Final</h2>中ByteBuf默认使用的是堆外内存，
     * 需要手动释放内存空间，不然会造成内存泄漏
     * {@link ByteToMessageDecoder}可以自动释放内存空间，无需手动操作
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // List中添加解码后的Packet对象，可以自动实现结果往下一个 handler 进行传递
        out.add(PacketCodeC.INSTANCE.decode(in));
    }
}
