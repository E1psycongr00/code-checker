package org.lhg.codechecker.tool.message.bool;

import org.lhg.codechecker.tool.Store;
import java.lang.reflect.Method;

public class GetterMessageGenerator implements BooleanMessageGenerator<Method> {

    private static final String INFO_FORMAT = "getter method: %s.\n";
    private static final String MESSAGE = "getter method not allowed in package -> %s.\n\n";


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

