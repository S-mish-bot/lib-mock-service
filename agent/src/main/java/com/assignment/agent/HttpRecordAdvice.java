package com.assignment.agent;


import net.bytebuddy.asm.Advice;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.logging.Logger;

public class HttpRecordAdvice {

    private static final Logger logger = Logger.getLogger(HttpRecordAdvice.class.getName());

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Argument(0) ClientHttpRequest request) {
        try {
            logger.info("HTTP Request: " + request.getURI().toString() + " Method: " + request.getMethod().toString());
        } catch (Exception e) {
            logger.severe("Failed to log HTTP request: " + e.getMessage());
        }
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Return ClientHttpResponse response) {
        try {
            logger.info("HTTP Response Status: " + response.getStatusCode().toString());
            logger.info("HTTP Response Body: " + new String(response.getBody().readAllBytes()));
        } catch (IOException e) {
            logger.severe("Failed to log HTTP response: " + e.getMessage());
        }
    }
}
