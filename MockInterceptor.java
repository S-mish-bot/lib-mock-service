//package com.assignment.libmocker.agent;
//
//import com.assignment.libmocker.entity.Post;
//import lombok.extern.slf4j.Slf4j;
//import net.bytebuddy.implementation.bind.annotation.AllArguments;
//import net.bytebuddy.implementation.bind.annotation.Origin;
//import net.bytebuddy.implementation.bind.annotation.RuntimeType;
//
//import java.lang.reflect.Method;
//import java.util.Arrays;
//
//@Slf4j
//public class MockInterceptor {
//    @RuntimeType
//    public static Object intercept(@Origin Method method, @AllArguments Object[] args) throws Exception {
//        if (System.getenv("HT_MODE").equals("RECORD")) {
//            System.out.println("Recording mode: Intercepting " + method.getName());
//            // Log the request/response here
//            return method.invoke(args);
//        } else {
//            System.out.println("Replay mode: Returning mock response for " + method.getName());
//            // Return a mocked response
//            if (method.getName().equals("getForObject")) {
//                return "{\"timezone\":\"Asia/Kolkata\",\"datetime\":\"2024-06-02T12:00:00.000Z\"}";
//            }
//            return null;
//        }
//    }
//}
//
