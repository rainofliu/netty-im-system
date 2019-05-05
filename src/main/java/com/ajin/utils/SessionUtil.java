package com.ajin.utils;

import com.ajin.attribute.Attributes;
import com.ajin.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于服务端对已经登录的客户端的Session进行管理
 *
 * <h2>登录的时候保存会话信息，登出的时候删除会话信息</h2>
 *
 * @author ajin
 */

public class SessionUtil {


    /**
     * userId --> Channel 映射关系
     */
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();


    /**
     * groupId --> ChannelGroup 映射关系
     */
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();


    /**
     * 用于客户端登录后绑定Session
     */
    public static void bindSession(Session session, Channel channel) {

        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);

    }

    /**
     * 用于客户端登出的时候 删除Session信息
     */
    public static void unBindSession(Channel channel) {

        if (hasLogin(channel)) {

            userIdChannelMap.remove(getSession(channel).getUserId());

            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 判断客户端是否登录
     */
    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * 获取Session
     */
    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 获取 Channel
     */
    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }


    public static void bindChannelGroup(String grouId, ChannelGroup channelGroup) {

        groupIdChannelGroupMap.put(grouId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {

        return groupIdChannelGroupMap.get(groupId);

    }
}
