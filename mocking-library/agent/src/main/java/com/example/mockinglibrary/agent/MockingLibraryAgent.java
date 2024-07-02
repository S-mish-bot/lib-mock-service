package com.example.mockinglibrary.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class MockingLibraryAgent {
    public static void premain(String arguments, Instrumentation instrumentation) {
        String mode = System.getenv("HT_MODE");

        new AgentBuilder.Default()
                .type(ElementMatchers.named("org.springframework.web.client.RestTemplate"))
                .transform((builder, typeDescription, classLoader, javaModule) -> builder
                        .method(ElementMatchers.named("doExecute"))
                        .intercept(Advice.to(mode.equals("REPLAY") ? HttpReplayAdvice.class : HttpRecordAdvice.class))
                ).installOn(instrumentation);

        new AgentBuilder.Default()
                .type(ElementMatchers.named("org.springframework.jdbc.core.JdbcTemplate"))
                .transform((builder, typeDescription, classLoader, javaModule) -> builder
                        .method(ElementMatchers.named("execute"))
                        .intercept(Advice.to(mode.equals("REPLAY") ? DbReplayAdvice.class : DbRecordAdvice.class))
                ).installOn(instrumentation);

//        new AgentBuilder.Default()
//                .type(ElementMatchers.nameContains("RestTemplate"))
//                .transform((builder, typeDescription, classLoader, javaModule) -> {
//                    if ("REPLAY".equalsIgnoreCase(mode)) {
//                        return builder.method(ElementMatchers.named("getForObject"))
//                                .intercept(Advice.to(HttpInterceptor.class));
////                                .intercept(FixedValue.value("{\"datetime\":\"2024-06-06T12:34:56.789Z\"}"));
//                    }
//                    return builder;
//                }).installOn(instrumentation);
//
//        new AgentBuilder.Default()
//                .type(ElementMatchers.nameContains("JdbcTemplate"))
//                .transform((builder, typeDescription, classLoader, javaModule) -> {
//                    if ("REPLAY".equalsIgnoreCase(mode)) {
//                        return builder.method(ElementMatchers.named("queryForObject"))
//                                .intercept(Advice.to(DbInterceptor.class));
////                                .intercept(FixedValue.value(new JdbcTemplateMock()));
//                    }
//                    return builder;
//                }).installOn(instrumentation);
    }

    public static class HttpInterceptor {
        @Advice.OnMethodEnter
        public static void enter(@Advice.Argument(0) String url) {
            System.out.println("HTTP Call intercepted: " + url);
        }

        @Advice.OnMethodExit
        public static void exit(@Advice.Return Object result) {
            System.out.println("HTTP Response: " + result);
        }
    }

    public static class DbInterceptor {
        @Advice.OnMethodEnter
        public static void enter(@Advice.Argument(0) Object post) {
            System.out.println("DB Save intercepted: " + post);
        }

        @Advice.OnMethodExit
        public static void exit(@Advice.Return Object result) {
            System.out.println("DB Response: " + result);
        }
    }

    private static class JdbcTemplateMock {
        public Object queryForObject(String sql, Class<?> requiredType, Object post) {
            // Mock response
//            return "Mocked response";
            return  post;
        }
    }

    private static class PostMock {
        public Object save(Object post) {
            // Mock response
            return post;
        }
    }
}