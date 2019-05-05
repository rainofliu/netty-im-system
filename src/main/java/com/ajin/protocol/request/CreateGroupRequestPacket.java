package com.ajin.protocol.request;

import com.ajin.protocol.Packet;
import com.ajin.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * 群聊创建请求数据包
 *
 * @author ajin
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    /**
     * 需要拉取群聊的用户列表
     */
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
