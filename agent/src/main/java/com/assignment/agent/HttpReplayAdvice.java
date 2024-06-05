package com.assignment.agent;

import net.bytebuddy.asm.Advice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpReplayAdvice {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Argument(0) ClientHttpRequest request) {
        // No action needed
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Return(readOnly = false) ClientHttpResponse response) {
        response = new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                String hardcodedResponse = "{ \"datetime\": \"2023-06-05T12:34:56.789Z\" }";
                return new ByteArrayInputStream(hardcodedResponse.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                return headers;
            }
        };
    }
}