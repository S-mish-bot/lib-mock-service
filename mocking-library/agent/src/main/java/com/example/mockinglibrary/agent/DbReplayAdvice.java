package com.example.mockinglibrary.agent;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.lang.reflect.Field;

public class DbReplayAdvice {
//    public static Object interceptSave(@Advice.Argument(0) Object entity) throws IllegalAccessException, InstantiationException {
//        System.out.println("Intercepted save method with entity: " + entity);
//        // Return a mock entity instead of saving the real entity
//        return mockResult(entity);
//    }

//    @Advice.OnMethodEnter
//    public static void onEnter(@Advice.Origin String method,
//                               @Advice.AllArguments Object[] args) {
//        System.out.println("DB Query::" + method);
//    }
//
//    @Advice.OnMethodExit(onThrowable = Throwable.class)
//    public static void onExit(@Advice.Return(readOnly = false, typing = Assigner.Typing.DYNAMIC) Object result,
//                              @Advice.Origin String method,
//                              @Advice.Thrown Throwable throwable) {
//        if (throwable != null) {
//            System.out.println("DB Exception::" + throwable.getMessage());
//        } else {
//            if (method.contains("save")) {
//                System.out.println("DB Result Before Mock::" + result.toString());
//                try {
//                    result = mockResult(result);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println("DB Result After Mock::" + result);
//            }
//        }
//    }

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    public static Object interceptSave(@Advice.Argument(0) Object entity) throws IllegalAccessException, InstantiationException {
        System.out.println("Intercepted save method with entity: " + entity);
        // Return a mock entity instead of saving the real entity
        return mockResult(entity);
    }

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
//        Class<?> clazz = result.getClass();
        Object mockEntity = result.getClass().newInstance();
        for (Field field : mockEntity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object originalValue = field.get(result);
            if (field.getName().equalsIgnoreCase("id") && field.getType() == Long.class) {
                field.set(mockEntity, 1L);
            } else if (field.getType() == String.class) {
                if (field.getName().equalsIgnoreCase("name")) {
                    field.set(mockEntity, "Test Post 1");
                } else if (field.getName().equalsIgnoreCase("contents")) {
                    field.set(mockEntity, "This is a test post content.");
                } else {
                    field.set(mockEntity, "Mocked String");
                }
            } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                field.set(mockEntity, true);
            }
            System.out.println("Field: " + field.getName() + " changed from " + originalValue + " to " + field.get(result));
        }
        return mockEntity;
    }
}
