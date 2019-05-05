package com.ajin.protocol.request;

import com.ajin.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.ajin.protocol.command.Command.GROUP_MESSAGE_REQUEST;

/**
 * 群发消息的数据包
 *
 * @author ajin
 */
@Data
@AllArgsConstructor
public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;
    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
