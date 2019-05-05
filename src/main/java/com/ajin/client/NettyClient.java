package com.ajin.client;

import com.ajin.client.console.ConsoleCommandManager;
import com.ajin.client.console.LoginConsoleCommand;
import com.ajin.client.handler.*;
import com.ajin.codec.PacketDecoder;
import com.ajin.codec.PacketEncoder;
import com.ajin.codec.Spliter;
import com.ajin.server.handler.IMIdleStateHandler;
import com.ajin.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 客户端
 *
 * @author ajin
 */
@SuppressWarnings("all")
public class NettyClient {
    /**
     * 定义最大重试次数
     */
    private static final int MAX_RETRY = 10;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1010;

    public static void main(String[] args) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                // 1. 指定线程模型
                .group(workerGroup)
                // 2. 指定IO类型为NIO
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 3. IO处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 指定连接数据读写逻辑
//                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
//                                Integer.MAX_VALUE, 7, 4));
//                        ch.pipeline() // 获取ChannelPipeline，也就是和这条连接相关的责任处理链（责任链模式）
//                                // 添加一个逻辑处理器，用于和服务端建立连接后向服务端写入数据
////                                .addLast(new FirstClientHandler());
//                                .addLast(new ClientHandler()); // 用来登录的逻辑处理器

                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        // 添加一个新的 handler 来处理创建群聊成功响应的指令
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        // 加群响应处理器
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        // 获取群成员响应处理器
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        // 退群响应处理器
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        // 群发消息响应处理器
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
//                        ch.pipeline().addLast(new FirstClientHandler());

                    }
                });

        // 4.建立连接
        connect(bootstrap, HOST, PORT, MAX_RETRY);

    }

    /**
     * 因为多种因素，会出现连接失败的情况，所以需要提供自动重连的功能完成连接
     *
     * @param bootstrap 客户端启动类
     * @param host      服务端主机名
     * @param port      服务端端口
     * @param retry     连接失败后重试次数
     */
    public static void connect(Bootstrap bootstrap, String host, int port, int retry) {

        bootstrap.connect(host, port).addListener(future -> {
            // 连接成功，打印消息
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程...");

                Channel channel = ((ChannelFuture) future).channel();

                // 连接成功后，启动控制台线程
                startConsoleThread(channel);

            } else if (retry == 0) {
                // 连接失败但连接次数已经用完 ，放弃连接
                System.err.println("重试次数已经用完，放弃连接");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败 ， 第" + order + "次重连");

                // 进行递归重试
                bootstrap.config().group().schedule(() ->
                        connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);

            }
        });
    }

    /**
     * 启动控制台线程
     */
    private static void startConsoleThread(Channel channel) {

        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();

        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {

            while (!Thread.interrupted()) {
                // 如果没登录
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.execute(scanner, channel);

                } else { // 如果已经登录
                    consoleCommandManager.execute(scanner, channel);
//                    String toUserId = sc.next();
//                    String message = sc.next();
//                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                }
            }
        }).start();
    }


}
