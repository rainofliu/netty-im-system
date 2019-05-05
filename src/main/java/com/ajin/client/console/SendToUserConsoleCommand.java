package com.ajin.client.console;

import com.ajin.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author ajin
 */

public class SendToUserConsoleCommand implements ConsoleCommand {


    @Override
    public void execute(Scanner scanner, Channel channel) {

        System.out.print("发送消息给某个用户：");

        String toUserId = scanner.next();
        String message = scanner.next();

        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
