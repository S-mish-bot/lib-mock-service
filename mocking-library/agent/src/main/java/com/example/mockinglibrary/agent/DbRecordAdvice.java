package com.example.mockinglibrary.agent;

import net.bytebuddy.asm.Advice;

import java.util.logging.Logger;

public class DbRecordAdvice {

//    private static final Logger logger = Logger.getLogger(DbRecordAdvice.class.getName());

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Argument(0) String sql) {
        System.out.println("DB Query: " + sql);
//        logger.info("DB Query: " + sql);
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Return Object result) {
        System.out.println("DB Result: " + result.toString());
//        logger.info("DB Result: " + result.toString());
    }
}
