package com.ajin.protocol.request;

import com.ajin.protocol.Packet;

import static com.ajin.protocol.command.Command.LOGOUT_REQUEST;

/**
 * @author ajin
 */

public class LogoutRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
