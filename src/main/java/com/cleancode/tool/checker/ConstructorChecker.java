package com.cleancode.tool.checker;

import com.cleancode.tool.Store;
import com.cleancode.tool.message.count.ConstructorMessageGenerator;
import com.cleancode.tool.message.count.MessageGenerator;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorChecker implements Checker {

    private static final String ERROR_INVALID_INIT = "Cannot initialize to 0 or negative number.";

    private final MessageGenerator<Constructor<?>[]> messageGenerator;
    private final int limitConstructors;

    public ConstructorChecker(int limitConstructors) {
        validateLimitConstructors(limitConstructors);
        this.limitConstructors = limitConstructors;
        this.messageGenerator = new ConstructorMessageGenerator();
    }

    @Override
    public boolean check(List<Store<Object>> scanned, StringBuilder buffer) {
        List<Store<Constructor<?>[]>> constructorsCollect = scanned.stream()
                .map(object -> Store.of(object.getPackageName(), (Constructor<?>[]) object.getValue()))
                .collect(Collectors.toList());
        boolean flag = true;
        for (Store<Constructor<?>[]> constructorsStore : constructorsCollect) {
            Constructor<?>[] constructors = constructorsStore.getValue();
            if (constructors.length > limitConstructors) {
                flag = false;
                buffer.append(messageGenerator.generateMessage(constructorsStore, limitConstructors));
            }
        }
        return flag;
    }

    void validateLimitConstructors(int limitConstructors) {
        if (limitConstructors <= 0) {
            throw new IllegalArgumentException(ERROR_INVALID_INIT);
        }
    }
}
