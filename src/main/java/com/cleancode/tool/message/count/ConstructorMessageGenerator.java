package com.cleancode.tool.message.count;

import com.cleancode.tool.Store;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class ConstructorMessageGenerator implements MessageGenerator<Constructor<?>[]> {

    private static final String INFO_FORMAT = "constructors: %s, count: %d.\n";
    private static final String MESSAGE = "constructors count must be %d but %d.\n\n";

    @Override
    public String generateMessage(Store<Constructor<?>[]> store, int limitCount) {
        String packageName = store.getPackageName();
        Constructor<?>[] constructors = store.getValue();
        String constructorsInfo = String.format(INFO_FORMAT,
                packageName + "#" + Arrays.deepToString(constructors),
                constructors.length);
        String message = String.format(MESSAGE, limitCount, constructors.length);
        return constructorsInfo + message;
    }
}