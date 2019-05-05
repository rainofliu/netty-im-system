package com.ajin.utils;

import java.util.UUID;

/**
 * @author ajin
 */

public class IDUtil {

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
