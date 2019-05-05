package com.ajin.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 客户端 {@link com.ajin.client.NettyClient} 控制台命令执行器接口
 *
 * @author ajin
 */
public interface ConsoleCommand {

    /**
     * 客户端控制台执行操作
     */
    void execute(Scanner scanner, Channel channel);
}
