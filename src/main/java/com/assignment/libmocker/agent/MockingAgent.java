package com.assignment.libmocker.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class MockingAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(ElementMatchers.nameEndsWith("RestTemplate"))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) ->
                        builder.method(ElementMatchers.named("getForObject"))
                                .intercept(MethodDelegation.to(MockInterceptor.class))
                ).installOn(inst);

        new AgentBuilder.Default()
                .type(ElementMatchers.nameEndsWith("PostRepository"))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) ->
                        builder.method(ElementMatchers.named("save"))
                                .intercept(MethodDelegation.to(MockInterceptor.class))
                ).installOn(inst);
    }
}
