package com.assignment.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class MockingAgent {

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
    }
}
