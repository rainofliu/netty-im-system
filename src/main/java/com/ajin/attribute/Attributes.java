package com.ajin.attribute;

import com.ajin.session.Session;
import io.netty.util.AttributeKey;

/**
 * 是否登录成功的标志位
 *
 * @author: ajin
 */

public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
