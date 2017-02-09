package com.frc.utility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * @author songbin
 */
public class FRCLogger {
    private final String        FQCN        = getClass().getName();

    private Logger              logger      = (Logger) LoggerFactory.getLogger(this.getClass());
    private final int           LEVEL_TRACE = 0;
    private final int           LEVEL_DEBUG = 10;
    private final int           LEVEL_INFO  = 20;
    private final int           LEVEL_WARN  = 30;
    private final int           LEVEL_ERROR = 40;
    private static FRCLogger    instance    = null;
    /** 日志索引 + flag */
    private static final String MSG_PREFIX  = "[req:{}]-[{}]";

    private FRCLogger() {
    }

    public static synchronized FRCLogger getInstance() {
        if (null == instance) {
            instance = new FRCLogger();
        }
        return instance;
    }

    /**
     * 基础
     * 
     * @param logIndex
     *            long 日志索引
     * @param level
     *            int 日志等级
     * @param msg
     *            String 日志内容
     * @param t
     *            Throwable 异常内容
     * @param flag
     *            String 日志标识(运行的函�?)
     * @param args
     *            Object 变长参数，与{@linkplain msg}中的{}�?�?对应
     * */
    private void log(long logIndex, int level, String msg, Throwable t, String flag, Object... args) {
        try {
            Object argArray[];
            int len = args.length;
            if (len > 0) {
                argArray = new Object[len + 2];
                argArray[0] = logIndex;
                argArray[1] = flag;
                for (int index = 0; index < len; index++) {
                    argArray[index + 2] = args[index];
                }
            } else {
                argArray = new Object[2];
                argArray[0] = logIndex;
                argArray[1] = flag;
            }
            
            StringBuilder finalMsg = new StringBuilder(MSG_PREFIX);
            if(StringUtils.isNoneBlank(msg)){
                finalMsg.append(msg);
            }
            this.logger.log(null, FQCN, level, finalMsg.toString(), argArray, t);
        } catch (Exception e) {
            this.logger.log(null, FQCN, LEVEL_WARN, msg, args, e);
        }
    }

    public void info(long logIndex, String flag, String msg, Throwable t, Object... args) {
        this.log(logIndex, this.LEVEL_INFO, msg, t, flag, args);
    }

    public void debug(long logIndex, String flag, String msg, Throwable t, Object... args) {
        this.log(logIndex, this.LEVEL_DEBUG, msg, t, flag, args);
    }

    public void warn(long logIndex, String flag, String msg, Throwable t, Object... args) {
        this.log(logIndex, this.LEVEL_WARN, msg, t, flag, args);
    }

    public void error(long logIndex, String flag, String msg, Throwable t, Object... args) {
        this.log(logIndex, this.LEVEL_ERROR, msg, t, flag, args);
    }

    public void trace(long logIndex, String flag, String msg, Throwable t, Object... args) {
        this.log(logIndex, this.LEVEL_TRACE, msg, t, flag, args);
    }

}
