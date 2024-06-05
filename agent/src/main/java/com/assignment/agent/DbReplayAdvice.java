package com.assignment.agent;

import net.bytebuddy.asm.Advice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbReplayAdvice {

    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Argument(0) String sql) {
        // No action needed
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Return(readOnly = false) Object result) {
        // Replace with hardcoded result

        // Simulating a hardcoded result as a List of Map, where each map represents a row of the result set.
        List<Map<String, Object>> hardcodedResult = new ArrayList<>();
        Map<String, Object> row = new HashMap<>();
        row.put("id", 1);  // Assuming the ID column with value 1
        row.put("name", "hardcoded-post-name");  // Example hardcoded post name
        row.put("contents", "hardcoded-contents");  // Example hardcoded post contents
        hardcodedResult.add(row);

        // The result is set to the hardcoded result
        result = hardcodedResult;
    }
}