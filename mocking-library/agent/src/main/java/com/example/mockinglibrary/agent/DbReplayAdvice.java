package com.example.mockinglibrary.agent;

import net.bytebuddy.asm.Advice;

public class DbReplayAdvice {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Argument(0) String sql) {
        // Do nothing
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Return(readOnly = false) Object result) {
        // Replace with hardcoded result
        result = 1;  // Assuming ID 1 for replay
    }
}