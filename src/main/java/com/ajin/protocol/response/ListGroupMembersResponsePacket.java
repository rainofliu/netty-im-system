package com.ajin.protocol.response;

import com.ajin.protocol.Packet;
import com.ajin.session.Session;
import lombok.Data;

import java.util.List;

import static com.ajin.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

/**
 * @author ajin
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
