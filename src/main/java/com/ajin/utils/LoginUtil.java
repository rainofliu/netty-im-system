package com.ajin.utils;

import com.ajin.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author: ajin
 */

public class LoginUtil {


    /**
     * 设置登录标志位
     */
    public static void markAsLogin(Channel channel) {

        channel.attr(Attributes.LOGIN).set(true);

    }

    /**
     * 判断是否有登录标志位
     */
    public static boolean hasLogin(Channel channel) {

        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
