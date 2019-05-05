package com.ajin.protocol.response;

import com.ajin.protocol.Packet;
import lombok.Data;

import static com.ajin.protocol.command.Command.QUIT_GROUP_RESPONSE;

/**
 * @author ajin
 */
@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
