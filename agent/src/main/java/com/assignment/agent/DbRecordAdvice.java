package com.assignment.agent;

import net.bytebuddy.asm.Advice;

import java.util.logging.Logger;

public class DbRecordAdvice {

    private static final Logger logger = Logger.getLogger(DbRecordAdvice.class.getName());

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Argument(0) String sql) {
        logger.info("DB Query: " + sql);
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Return Object result) {
        logger.info("DB Result: " + result.toString());
    }
}
