package com.ajin.serialize;

import com.ajin.serialize.impl.JSONSerializer;

/**
 * 序列化与反序列化接口
 *
 * @author: ajin
 */
public interface Serializer {


    /**
     * json序列化
     */
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 获取具体的序列化算法标识
     */
    byte getSerializerAlgorithm();


    /**
     * Java对象转化成二进制(序列化）
     */
    byte[] serilize(Object object);

    /**
     * 反序列化
     */
    <T> T deSerilize(Class<T> clazz, byte[] bytes);
}
