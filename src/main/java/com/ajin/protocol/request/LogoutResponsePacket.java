package com.ajin.protocol.request;

import com.ajin.protocol.Packet;

import static com.ajin.protocol.command.Command.LOGOUT_RESPONSE;

/**
 * @author ajin
 */

public class LogoutResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
