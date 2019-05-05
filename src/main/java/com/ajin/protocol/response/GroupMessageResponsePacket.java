package com.ajin.protocol.response;

import com.ajin.protocol.Packet;
import lombok.Data;

import static com.ajin.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

/**
 * 群聊发消息的响应数据包
 *
 * @author ajin
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private String groupId;

    private String fromUserId;

    private String fromUserName;

    private String message;


    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
