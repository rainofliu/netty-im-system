package com.ajin.server;

import com.ajin.codec.PacketCodecHandler;
import com.ajin.codec.Spliter;
import com.ajin.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;

/**
 * 服务端
 *
 * @author ajin
 */
@SuppressWarnings("all")
public class NettyServer {

    private static final int BIND_PORT = 1010;

    public static void main(String[] args) {

        /**
         * 1. 线程模型
         * boosGroup线程组接收完客户端的连接后，交给workerGroup先程序进行处理
         * */

        // 监听端口，accept新连接的  线程组
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        // 处理每一条连接的数据读写的 线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 引导类 引导我们进行服务端的启动
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup) // 给引导类配置两个线程组，实现线程模型
                // 2. IO模型
                .channel(NioServerSocketChannel.class) // 指定服务端的IO模型 ：NIO
                .option(ChannelOption.SO_BACKLOG, 1024)

                // NioServerSocketChannel指定一些自定义属性
//                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                // 给每一条连接指定自定义属性
                //.childAttr(AttributeKey.newInstance("clientKey"), "client value")

                // 给每一条连接设置TCP底层相关的属性
                .childOption(ChannelOption.SO_KEEPALIVE, true) //开启TCP底层心跳机制
                // 如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                .childOption(ChannelOption.TCP_NODELAY, true) // 开启Nagle算法

                // 系统用于临时存放已完成三次握手的请求的队列的最大长度，
                // 如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                // .option(ChannelOption.SO_BACKLOG, 1024)

//                .handler(new ChannelInitializer<NioSocketChannel>() { // 指定服务端启动过程中的逻辑
//                    @Override
//                    protected void initChannel(NioSocketChannel ch) throws Exception {
//                        System.out.println("服务端启动中");
//                    }
//                })
                // 3. 每条连接的数据读写
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 处理新连接数据的读写处理逻辑

                    /**
                     *  {@link io.netty.channel.socket.nio.NioSocketChannel}
                     *   Netty对NIO类型的连接的抽象
                     * */
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        // 指定连接数据读写逻辑

//                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
//                                Integer.MAX_VALUE, 7, 4)
//                        );
                        // 添加逻辑处理器,负责读写客户端发来的数据
//                                .addLast(new FirstServerHandler());
//                                .addLast(new ServerHandler()) // 服务端处理客户端登录的逻辑处理器

//                        // inBound , 处理读数据的逻辑链
//                        ch.pipeline().addLast(new InBoundHandlerA());
//                        ch.pipeline().addLast(new InBoundHandlerB());
//                        ch.pipeline().addLast(new InBoundHandlerC());
//
//
//                        // outBound , 处理写数据的逻辑链
//                        ch.pipeline().addLast(new OutBoundHandlerA());
//                        ch.pipeline().addLast(new OutBoundHandlerB());
//                        ch.pipeline().addLast(new OutBoundHandlerC());

//                        ch.pipeline().addLast(new LifeCyCleTestHandler());
                        // 服务端空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());

                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
//                        ch.pipeline().addLast(new PacketDecoder());
                        // 登录请求处理器
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        // 用户认证handler
                        ch.pipeline().addLast(AuthHandler.INSTANCE);

                        /**
                         * 平行指令处理handler只的是，在这组平行指令处理器中，每次仅有一个 handler 会处理 指令packet，
                         * 也就是说只要有一个 handler 能处理指令 packet，其他的就不能再处理了，
                         * 然后这边登录和鉴权比较特殊（如果没有登录，
                         * 每次数据包过来都要鉴权，并且鉴权，无法加到平行处理器中，因为与后续的handler有个先后关系），
                         * 所以没有加到 IMhandler里
                         * */
                        ch.pipeline().addLast(IMHanlder.INSTANCE);
//                        // 单聊消息请求处理器
//                        ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
//                        // 创建群请求处理器
//                        ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
//                        // 加群请求处理器
//                        ch.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
//                        // 退群请求处理器
//                        ch.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
//                        // 获取群成员请求处理器
//                        ch.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE);
//                        // 群发消息请求处理器
//                        ch.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
                        // 登出请求处理器
//                        ch.pipeline().addLast(new LogoutRequestHandler());
//                        ch.pipeline().addLast(new PacketEncoder());
//                        ch.pipeline().addLast(new FirstServerHandler());

                    }
                });

        bind(serverBootstrap, BIND_PORT);


    }

    /**
     * 自动绑定递增端口，直到绑定成功
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {

        // 绑定端口，是一个异步方法
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
