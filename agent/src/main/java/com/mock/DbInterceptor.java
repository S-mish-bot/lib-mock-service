package com.mock;

import com.mock.model.Post;

import java.util.concurrent.Callable;

public class DbInterceptor {
    public static Object intercept(Callable<?> zuper) throws Exception {
        if ("REPLAY".equals(System.getenv("HT_MODE"))) {
            // Return hardcoded response
            return new Post("replay-post", "This is a replayed post.");
        } else {
            Object result = zuper.call();
            // Log the result in RECORD mode
            System.out.println("DB Response: " + result);
            return result;
        }
    }
}
