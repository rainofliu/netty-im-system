package com.ajin.protocol.response;

import com.ajin.protocol.Packet;
import com.ajin.protocol.command.Command;
import lombok.Data;

import java.util.List;

import static com.ajin.protocol.command.Command.CREATE_GROUP_RESPONSE;

/**
 * 创建群聊响应数据包
 *
 * @author ajin
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
