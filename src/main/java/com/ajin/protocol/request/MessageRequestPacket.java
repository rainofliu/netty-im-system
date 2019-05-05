package com.ajin.protocol.request;

import com.ajin.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ajin.protocol.command.Command.MESSAGE_REQUEST;

/**
 * 客户端发送到服务端的消息对象
 *
 * @author ajin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestPacket extends Packet {

    /**
     * 消息发给哪个客户端 15岁孩神所在客户端吗？
     */
    private String toUserId;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
