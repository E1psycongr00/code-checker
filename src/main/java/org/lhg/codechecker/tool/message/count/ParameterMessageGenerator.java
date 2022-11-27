package org.lhg.codechecker.tool.message.count;

import org.lhg.codechecker.tool.Store;
import java.lang.reflect.Method;

public class ParameterMessageGenerator implements MessageGenerator<Method> {

    private static final String INFO_FORMAT = "parameters: %s, count: %d.\n";
    private static final String MESSAGE = "parameters count must be %d but %d.\n\n";

    @Override
    public String generateMessage(Store<Method> store, int limitCount) {
        String packageName = store.getPackageName();
        Method method = store.getValue();
        String methodsInfo = String.format(INFO_FORMAT,
                packageName + " # " + method.getName(),
                method.getParameterCount());
        String message = String.format(MESSAGE, limitCount, method.getParameterCount());
        return methodsInfo + message;
    }
}