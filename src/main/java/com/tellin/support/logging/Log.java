package com.tellin.support.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    private static final Logger LOGGER = LogManager.getLogger(Log.class);
    private static String message;
    private static Object[] objArgs;

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static <T> void info(String message, T... args) {
        LOGGER.info(message, (Object[]) args);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }

    public static void debug(String message, Object... objArg) {
        LOGGER.debug(message, objArg);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(String message, Object... objArg) {
        LOGGER.error(message, objArg);
    }
}
