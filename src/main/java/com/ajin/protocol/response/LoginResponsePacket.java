package com.ajin.protocol.response;

import com.ajin.protocol.Packet;
import lombok.Data;

import static com.ajin.protocol.command.Command.LOGIN_RESPONSE;

/**
 * 服务端响应给客户端的数据包(登录相关）
 *
 * @author: ajin
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
