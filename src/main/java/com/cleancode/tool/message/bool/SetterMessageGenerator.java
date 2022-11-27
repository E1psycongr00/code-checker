package com.cleancode.tool.message.bool;

import com.cleancode.tool.Store;
import java.lang.reflect.Method;

public class SetterMessageGenerator implements BooleanMessageGenerator<Method> {

    private static final String INFO_FORMAT = "setter method: %s.\n";
    private static final String MESSAGE = "setter method not allowed in package -> %s.\n\n";

    @Override
    public String generateMessage(Store<Method> store, boolean isAllow) {
        String packageName = store.getPackageName();
        Method method = store.getValue();
        String getterMethodInfo = String.format(INFO_FORMAT,
                packageName + " # " + method.getName());
        String message = String.format(MESSAGE, packageName);
        return getterMethodInfo + message;
    }
}