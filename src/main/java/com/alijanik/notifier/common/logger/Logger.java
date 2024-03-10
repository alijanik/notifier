package com.alijanik.notifier.common.logger;

public class Logger {
    // can be replaced by a logger!

    public static void log(String message, Object... args) {
        System.out.printf(message, args);
        System.out.println();
    }
}
