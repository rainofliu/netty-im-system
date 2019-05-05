package com.ajin.client.console;

import com.ajin.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 登录命令行操作
 *
 * @author ajin
 */
@SuppressWarnings("all")
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void execute(Scanner scanner, Channel channel) {

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();


        System.out.print("输入用户名登录: ");
        String username = scanner.nextLine();
        loginRequestPacket.setUserName(username);


        // 这里使用默认密码
        loginRequestPacket.setPassword("pwd");


        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);

        waitForLoginResponse();

    }

    private static void waitForLoginResponse() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
