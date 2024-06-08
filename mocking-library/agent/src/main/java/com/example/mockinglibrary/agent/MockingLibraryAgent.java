package com.example.mockinglibrary.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class MockingLibraryAgent {
    public static void premain(String arguments, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.nameContains("RestTemplate"))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                            TypeDescription typeDescription,
                                                            ClassLoader classLoader,
                                                            JavaModule module) {
                        return builder.method(ElementMatchers.named("getForObject"))
                                .intercept(Advice.to(HttpInterceptor.class));
                    }
                })
                .installOn(instrumentation);

        new AgentBuilder.Default()
                .type(ElementMatchers.nameContains("PostRepository"))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                            TypeDescription typeDescription,
                                                            ClassLoader classLoader,
                                                            JavaModule module) {
                        return builder.method(ElementMatchers.named("save"))
                                .intercept(Advice.to(DbInterceptor.class));
                    }
                })
                .installOn(instrumentation);
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
}
