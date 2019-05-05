package com.ajin.protocol.response;

import com.ajin.protocol.Packet;
import lombok.Data;

import static com.ajin.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * 服务端发送到客户端的消息对象
 *
 * @author: ajin
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
