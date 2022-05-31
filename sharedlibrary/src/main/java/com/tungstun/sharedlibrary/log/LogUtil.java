package com.tungstun.sharedlibrary.log;

import org.apache.commons.logging.Log;

public class LogUtil {
    public static void logInfo(Log log, String format, Object... arguments) {
        try {
            log.info(String.format(format, arguments));
        } catch (Exception e) {
            log.error(String.format("!! info(format=%s): catch %s", format, e));
        }
    }

    public static void logDebug(Log log, String format, Object... arguments) {
        try {
            log.debug(String.format(format, arguments));
        } catch (Exception e) {
            log.error(String.format("!! debug(format=%s): catch %s", format, e));
        }
    }

    public static void logError(Log log, String format, Object... arguments) {
        try {
            log.error(String.format(format, arguments));
        } catch (Exception e) {
            log.error(String.format("!! error(format=%s): catch %s", format, e));
        }
    }

    public static void logError(Log log, Throwable ex, String format, Object... arguments) {
        try {
            log.error(String.format(format, arguments), ex);
        } catch (Exception e) {
            log.error(String.format("!! error(ex, format=%s): catch %s", format, e));
        }
    }

    public static void logTrace(Log log, String format, Object... arguments) {
        try {
            log.trace(String.format(format, arguments));
        } catch (Exception e) {
            log.error(String.format("!! trace(format=%s): catch %s", format, e));
        }
    }

    public static void logWarn(Log log, String format, Object... arguments) {
        try {
            log.warn(String.format(format, arguments));
        } catch (Exception e) {
            log.error(String.format("!! warn(format=%s): catch %s", format, e));
        }
    }

    public static void logWarn(Log log, Exception ex, String format, Object... arguments) {
        try {
            log.warn(String.format(format, arguments), ex);
        } catch (Exception e) {
            log.error(String.format("!! warn(ex, format=%s): catch %s", format, e));
        }
    }

    private LogUtil(){}
}
