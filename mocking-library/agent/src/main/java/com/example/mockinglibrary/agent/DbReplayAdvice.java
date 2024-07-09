package com.example.mockinglibrary.agent;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.lang.reflect.Field;

public class DbReplayAdvice {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    public static Object interceptSave(@Advice.Argument(0) Object entity) throws IllegalAccessException, InstantiationException {
        System.out.println("Intercepted save method with entity: " + entity);
        return mockResult(entity);
    }

    //currently not in use
    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void onExit(@Advice.Enter Object mockEntity,
                              @Advice.Return(readOnly = false, typing = Assigner.Typing.DYNAMIC) Object result,
                              @Advice.Thrown Throwable throwable) {
        if (throwable != null) {
            System.out.println("DB Exception::" + throwable.getMessage());
        } else {
            result = mockEntity;
            System.out.println("Returning mocked entity: " + mockEntity);
        }
    }
    public static Object mockResult(Object result) throws IllegalAccessException, InstantiationException {
        System.out.println("Mocking result object: " + result.toString());
        //create mocked response object
        Object mockEntity = result.getClass().newInstance();
        for (Field field : mockEntity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            //setting id
            if (field.getName().equalsIgnoreCase("id") && field.getType() == Long.class) {
                field.set(mockEntity, 1L);
            } else if (field.getType() == String.class) {
                //setting name
                if (field.getName().equalsIgnoreCase("name")) {
                    field.set(mockEntity, "Test Post 1");
                } else if (field.getName().equalsIgnoreCase("contents")) { //setting contents
                    field.set(mockEntity, "This is a test post content.");
                } else {
                    field.set(mockEntity, "Mocked String"); //setting extra parameters if any
                }
            }
        }
        return mockEntity;
    }
}
