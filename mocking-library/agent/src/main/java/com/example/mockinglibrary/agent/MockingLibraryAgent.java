package com.example.mockinglibrary.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

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

        System.out.println("DatabaseAgent loaded");
        new AgentBuilder.Default()
                //debugging purpose
                .with(new AgentBuilder.Listener() {
                    @Override
                    public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
                        System.out.println("Discovered: " + typeName);
                    }

                    @Override
                    public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
                        System.out.println("Transformed: " + typeDescription);
                    }

                    @Override
                    public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
                        System.out.println("Ignored: " + typeDescription);
                    }

                    @Override
                    public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
                        System.err.println("Error: " + typeName);
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
                        System.out.println("Complete: " + typeName);
                    }
                })
                .type(ElementMatchers.named("org.springframework.data.jpa.repository.support.SimpleJpaRepository"))
                .transform((builder, typeDescription, classLoader, javaModule) -> builder
                                .method(ElementMatchers.named("save"))
//                        todo: handle for findById, delete, findAll
//                                .or(ElementMatchers.named("findById"))
//                                .or(ElementMatchers.named("findAll"))
//                                .or(ElementMatchers.named("delete")))
                                .intercept(mode.equals("REPLAY") ? Advice.to(DbReplayAdvice.class) : Advice.to(DbRecordAdvice.class))
                ).installOn(instrumentation);
    }
//        new AgentBuilder.Default()
//                .type(ElementMatchers.named("org.springframework.jdbc.core.JdbcTemplate"))
//                .transform((builder, typeDescription, classLoader, javaModule) -> builder
//                        .method(ElementMatchers.named("execute"))
//                        .intercept(Advice.to(mode.equals("REPLAY") ? DbReplayAdvice.class : DbRecordAdvice.class))
//                ).installOn(instrumentation);
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




//        new AgentBuilder.Default()
//                .type(ElementMatchers.nameContains("RestTemplate"))
//                .transform((builder, typeDescription, classLoader, javaModule) -> {
//                    if ("REPLAY".equalsIgnoreCase(mode)) {
//                        return builder.method(ElementMatchers.named("getForObject"))
//                                .intercept(FixedValue.value("{\"datetime\":\"2024-06-06T12:34:56.789Z\"}"));
//                    }
//                    return builder;
//                }).installOn(instrumentation);
//
//        new AgentBuilder.Default()
//                .type(ElementMatchers.nameContains("JdbcTemplate"))
//                .transform((builder, typeDescription, classLoader, javaModule) -> {
//                    if ("REPLAY".equalsIgnoreCase(mode)) {
//                        return builder.method(ElementMatchers.named("queryForObject"))
//                                .intercept(FixedValue.value(new JdbcTemplateMock()));
//                    }
//                    return builder;
//                }).installOn(instrumentation);
//    }
//
//    public static class HttpInterceptor {
//        @Advice.OnMethodEnter
//        public static void enter(@Advice.Argument(0) String url) {
//            System.out.println("HTTP Call intercepted: " + url);
//        }
//
//        @Advice.OnMethodExit
//        public static void exit(@Advice.Return Object result) {
//            System.out.println("HTTP Response: " + result);
//        }
//    }
//
//    public static class DbInterceptor {
//        @Advice.OnMethodEnter
//        public static void enter(@Advice.Argument(0) Object post) {
//            System.out.println("DB Save intercepted: " + post);
//        }
//
//        @Advice.OnMethodExit
//        public static void exit(@Advice.Return Object result) {
//            System.out.println("DB Response: " + result);
//        }
//    }
//
//    private static class JdbcTemplateMock {
//        public Object queryForObject(String sql, Class<?> requiredType, Object post) {
//            // Mock response
////            return "Mocked response";
//            return  post;
//        }
//    }

//    private static class PostMock {
//        public Object save(Object post) {
//            // Mock response
//            return post;
//        }
//    }
}