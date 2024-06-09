package com.example.mockinglibrary.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import net.bytebuddy.utility.nullability.NeverNull;

import java.lang.instrument.Instrumentation;

public class MockingLibraryAgent {
    public static void premain(String arguments, Instrumentation instrumentation) {
        String mode = System.getenv("HT_MODE");

        new AgentBuilder.Default()
                .type(ElementMatchers.nameContains("RestTemplate"))
                .transform((builder, typeDescription, classLoader, javaModule) -> {
                    if ("REPLAY".equalsIgnoreCase(mode)) {
                        return builder.method(ElementMatchers.named("getForObject"))
                                .intercept(FixedValue.value("{\"datetime\":\"2024-06-06T12:34:56.789Z\"}"));
                    }
                    return builder;
                }).installOn(instrumentation);

        new AgentBuilder.Default()
                .type(ElementMatchers.nameContains("PostRepository"))
                .transform((builder, typeDescription, classLoader, javaModule) -> {
                    if ("REPLAY".equalsIgnoreCase(mode)) {
                        return builder.method(ElementMatchers.named("save"))
                                .intercept(FixedValue.value(new PostMock()));
                    }
                    return builder;
                }).installOn(instrumentation);
    }

    private static class PostMock {
        public Object save(Object post) {
            // Mock response
            return post;
        }
    }
}