package com.cleancode.tool.checker;

import com.cleancode.tool.Store;
import com.cleancode.tool.message.count.MessageGenerator;
import com.cleancode.tool.message.count.MethodMessageGenerator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class MethodChecker implements Checker {

    private static final String ERROR_INVALID_INIT = "Cannot initialize to 0 or negative number";

    private final MessageGenerator<Method[]> messageGenerator;
    private final int limitMethods;

    public MethodChecker(int limitMethods) {
        validateLimitMethods(limitMethods);
        this.limitMethods = limitMethods;
        this.messageGenerator = new MethodMessageGenerator();
    }

    @Override
    public boolean check(List<Store<Object>> scanned, StringBuilder buffer) {
        List<Store<Method[]>> methodsCollect = scanned.stream()
                .map(object -> Store.of(object.getPackageName(), (Method[]) object.getValue()))
                .collect(Collectors.toList());

        boolean flag = true;
        for (Store<Method[]> methodsStore : methodsCollect) {
            Method[] methods = methodsStore.getValue();
            if (methods.length > limitMethods) {
                flag = false;
                buffer.append(messageGenerator.generateMessage(methodsStore, limitMethods));
            }
        }
        return flag;
    }

    void validateLimitMethods(int limitMethods) {
        if (limitMethods <= 0) {
            throw new IllegalArgumentException(ERROR_INVALID_INIT);
        }
    }
}