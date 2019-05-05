package com.ajin.client.console;

import com.ajin.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author ajin
 */

public class SendToGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void execute(Scanner scanner, Channel channel) {

        System.out.print("发送消息给某个群组：");

        String toGroupId = scanner.next();
        String message = scanner.next();

        channel.writeAndFlush(new
                GroupMessageRequestPacket(toGroupId, message)
        );
    }
}
