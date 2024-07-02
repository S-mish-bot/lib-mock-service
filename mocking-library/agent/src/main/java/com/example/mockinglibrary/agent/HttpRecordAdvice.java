package com.example.mockinglibrary.agent;

import net.bytebuddy.asm.Advice;


public class HttpRecordAdvice {

//    private static final Logger logger = Logger.getLogger(HttpRecordAdvice.class.getName());

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Argument(0) Object request) {
        System.out.println("HTTP Call intercepted: " + request.toString());
//        logger.info("HTTP Request: " + request.toString());
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Return Object response) {
        System.out.println("HTTP Response: " + response.toString());
//        logger.info("HTTP Response: " + response.toString());
    }
}
