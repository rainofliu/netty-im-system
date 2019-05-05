package com.ajin.protocol.request;

import com.ajin.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ajin.protocol.command.Command.LOGIN_REQUEST;

/**
 * 客户端登录请求的二进制数据包
 * @author: ajin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestPacket extends Packet {

    /**
     * 用户ID
     */
    private String  userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
