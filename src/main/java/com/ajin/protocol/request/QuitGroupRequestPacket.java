package com.ajin.protocol.request;

import com.ajin.protocol.Packet;
import lombok.Data;

import static com.ajin.protocol.command.Command.QUIT_GROUP_REQUEST;

/**
 * @author ajin
 */
@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
