package com.ajin.serialize.impl;

import com.ajin.serialize.Serializer;
import com.ajin.serialize.SerializerAlgorithm;
import com.alibaba.fastjson.JSON;

/**
 * @author: ajin
 */

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    /**
     * 使用fastjson序列化
     */
    @Override
    public byte[] serilize(Object object) {

        return JSON.toJSONBytes(object);
    }

    /**
     * 反序列化
     */
    @Override
    public <T> T deSerilize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
