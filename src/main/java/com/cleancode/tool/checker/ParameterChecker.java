package com.cleancode.tool.checker;

import com.cleancode.tool.Store;
import com.cleancode.tool.message.count.MessageGenerator;
import com.cleancode.tool.message.count.ParameterMessageGenerator;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParameterChecker implements Checker {

    private static final String ERROR_INVALID_INIT = "Cannot initialize to 0 or negative number";

    private final MessageGenerator<Method> messageGenerator;
    private final int limitParameters;

    public ParameterChecker(int limitParameters) {
        validateLimitParameters(limitParameters);
        this.limitParameters = limitParameters;
        this.messageGenerator = new ParameterMessageGenerator();
    }

    @Override
    public boolean check(List<Store<Object>> scanned, StringBuilder buffer) {
        List<Store<Method[]>> methodCollect = scanned.stream()
                .map(object -> Store.of(object.getPackageName(), (Method[]) object.getValue()))
                .collect(Collectors.toList());
        boolean flag = true;
        for (Store<Method[]> methodsStore : methodCollect) {
            List<Store<Method>> invalidParameters = getInvalidParameters(methodsStore);
            if (!invalidParameters.isEmpty()) {
                flag = false;
            }
            saveBuffer(invalidParameters, buffer);
        }
        return flag;
    }

    private List<Store<Method>> getInvalidParameters(Store<Method[]> methodsStore) {
        List<Store<Method>> stores = new ArrayList<>();
        for (Method method : methodsStore.getValue()) {
            if (method.getParameterCount() > limitParameters) {
                stores.add(Store.of(methodsStore.getPackageName(), method));
            }
        }
        return stores;
    }

    private void saveBuffer(List<Store<Method>> invalidParameters, StringBuilder buffer) {
        for (Store<Method> invalidParameter : invalidParameters) {
            buffer.append(messageGenerator.generateMessage(invalidParameter, limitParameters));
        }
    }

    void validateLimitParameters(int limitParameters) {
        if (limitParameters <= 0) {
            throw new IllegalArgumentException(ERROR_INVALID_INIT);
        }
    }
}
