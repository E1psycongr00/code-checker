package com.cleancode.tool.message.count;

import com.cleancode.tool.Store;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodMessageGenerator implements MessageGenerator<Method[]> {

    private static final String INFO_FORMAT = "methods: %s, count: %d.\n";
    private static final String MESSAGE = "methods count must be %d but %d.\n\n";

    @Override
    public String generateMessage(Store<Method[]> store, int limitCount) {
        String packageName = store.getPackageName();
        Method[] methods = store.getValue();
        String methodsInfo = String.format(INFO_FORMAT,
                packageName + " # " + Arrays.deepToString(methods),
                methods.length);
        String message = String.format(MESSAGE, limitCount, methods.length);
        return methodsInfo + message;
    }
}
