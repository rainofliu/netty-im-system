package com.ajin.protocol.request;

import com.ajin.protocol.Packet;
import lombok.Data;

import static com.ajin.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

/**
 * @author ajin
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
