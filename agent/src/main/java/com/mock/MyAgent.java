package com.mock;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(ElementMatchers.named("java.net.HttpURLConnection"))
                .transform((builder, typeDescription, classLoader, module) ->
                        builder.method(ElementMatchers.named("getInputStream"))
                                .intercept(net.bytebuddy.implementation.MethodDelegation.to(HttpInterceptor.class)))
                .installOn(inst);

        new AgentBuilder.Default()
                .type(ElementMatchers.named("org.springframework.data.repository.CrudRepository"))
                .transform((builder, typeDescription, classLoader, module) ->
                        builder.method(ElementMatchers.named("save"))
                                .intercept(net.bytebuddy.implementation.MethodDelegation.to(DbInterceptor.class)))
                .installOn(inst);
    }
}
