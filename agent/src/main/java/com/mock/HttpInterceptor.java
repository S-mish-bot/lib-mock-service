package com.mock;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Callable;

public class HttpInterceptor {
    public static Object intercept(Callable<?> zuper) throws Exception {
        if ("REPLAY".equals(System.getenv("HT_MODE"))) {
            return new ByteArrayInputStream("{\"datetime\":\"2024-06-08T12:00:00+05:30\"}".getBytes());
        } else {
            Object result = zuper.call();
            // Log the result in RECORD mode
            System.out.println("HTTP Response: " + result);
            return result;
        }
    }
}
