package com.ajin.client.console;

import com.ajin.protocol.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author ajin
 */

public class QuitGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void execute(Scanner scanner, Channel channel) {

        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();

        System.out.print("输入 groupId，退出群聊：");
        String groupId = scanner.next();


        requestPacket.setGroupId(groupId);

        channel.writeAndFlush(requestPacket);

    }
}
