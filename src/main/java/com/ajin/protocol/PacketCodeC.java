package com.ajin.protocol;

import com.ajin.protocol.request.*;
import com.ajin.protocol.response.*;
import com.ajin.serialize.Serializer;
import com.ajin.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.ajin.protocol.command.Command.*;

/**
 * @author: ajin
 */
@SuppressWarnings("all")
public class PacketCodeC {

    /**
     * 定义魔数，过滤不符合要求的通信
     */
    public static final int MAGIC_NUMBER = 0x12345678;

    /**
     * 单例模式
     */
    public static final PacketCodeC INSTANCE = new PacketCodeC();


    /**
     * key： 指令
     * value ： 数据包对象
     */
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    /**
     * key： 序列化算法
     * value： 序列化器
     */
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(GROUP_MESSAGE_RESPONSE,GroupMessageResponsePacket.class);

        serializerMap = new HashMap<>();
        // 序列化器
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);

    }

    /**
     * 将Java对象封装成二进制数组 编码
     */
    public void encode(ByteBuf byteBuf, Packet packet) {

        // 1. 创建ByteBuf对象
        /**
         * ioBuffer() 方法会返回适配 io 读写相关的内存，
         *
         * 它会尽可能创建一个直接内存，
         * 直接内存可以理解为不受 jvm 堆管理的内存空间，写到 IO 缓冲区的效果更高。
         * */
//        ByteBuf byteBuf = byteBufAllocator.ioBuffer();


        // 2. 序列化Java对象
        byte[] bytes = Serializer.DEFAULT.serilize(packet);


        // 3. 实际编码过程

        // 魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        // 版本号
        byteBuf.writeByte(packet.getVersion());
        // 序列化算法
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        // 指令
        byteBuf.writeByte(packet.getCommand());
        // 数据长度
        byteBuf.writeInt(bytes.length);
        // 数据
        byteBuf.writeBytes(bytes);


    }

    /**
     * 服务端解码过程
     */
    public Packet decode(ByteBuf byteBuf) {

        // 跳过魔数
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serilizeAlgorithm = byteBuf.readByte();

        // 获取指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        // 数据包内容
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerilizer(serilizeAlgorithm);

        /**
         * 判断数据包是否合法 并且客户端传输的序列化算法是否对应正确的序列化工具类
         * */
        if (requestType != null && serializer != null) {
            return serializer.deSerilize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerilizer(byte serilizeAlgorithm) {
        return serializerMap.get(serilizeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
