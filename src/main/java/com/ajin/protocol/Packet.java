package com.ajin.protocol;

import lombok.Data;

/**
 * 通信过程中的Java对象
 *
 * @author: ajin
 */
@Data
public abstract class Packet {

    /**
     * 协议版本 ,默认为 1
     */
    private Byte version = 1;

    /**
     * 指令
     */
    public abstract Byte getCommand();

}
