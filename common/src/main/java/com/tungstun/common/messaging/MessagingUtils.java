package com.tungstun.common.messaging;

public class MessagingUtils {
    public static String createTypeMapping(Class<?> clazz) {
        return String.format("%s:%s", clazz.getSimpleName(), clazz.getCanonicalName());
    }

//   Disable instantiation
    private MessagingUtils() {}
}
