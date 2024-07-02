package com.example.mockinglibrary.agent;

import net.bytebuddy.asm.Advice;

public class HttpReplayAdvice {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Argument(0) Object request) {
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Return(readOnly = false) Object response) {
        response = "{ \"datetime\": \"2023-06-05T12:34:56.789Z\" }";
    }
}
