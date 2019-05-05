package com.ajin.protocol.command;

/**
 * 指令 类似于一个枚举类
 *
 * @author: ajin
 */
public interface Command {

    // 登录
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;

    // 发消息
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;

    // 创建群聊
    Byte CREATE_GROUP_REQUEST = 5;
    Byte CREATE_GROUP_RESPONSE = 6;

    // 登出
    Byte LOGOUT_REQUEST = 7;
    Byte LOGOUT_RESPONSE = 8;

    // 列出群聊成员
    Byte LIST_GROUP_MEMBERS_REQUEST = 9;
    Byte LIST_GROUP_MEMBERS_RESPONSE = 10;

    // 加入群聊
    Byte JOIN_GROUP_REQUEST = 11;
    Byte JOIN_GROUP_RESPONSE = 12;

    // 退出群聊
    Byte QUIT_GROUP_REQUEST = 13;
    Byte QUIT_GROUP_RESPONSE = 14;

    // 群聊发消息
    Byte GROUP_MESSAGE_REQUEST = 15;
    Byte GROUP_MESSAGE_RESPONSE = 16;

}
