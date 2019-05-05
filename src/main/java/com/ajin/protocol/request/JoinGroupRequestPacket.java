package com.ajin.protocol.request;

import com.ajin.protocol.Packet;
import lombok.Data;

import static com.ajin.protocol.command.Command.JOIN_GROUP_REQUEST;

/**
 * @author ajin
 */
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
