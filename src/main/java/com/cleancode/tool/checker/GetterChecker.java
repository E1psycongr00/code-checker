package com.cleancode.tool.checker;

import com.cleancode.tool.Store;
import com.cleancode.tool.message.bool.BooleanMessageGenerator;
import com.cleancode.tool.message.bool.GetterMessageGenerator;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class GetterChecker implements Checker {

    private static final String VALIDATION_KEYWORD = "get";
    private final BooleanMessageGenerator<Method> messageGenerator;


    public GetterChecker() {
        this.messageGenerator = new GetterMessageGenerator();
    }

    @Override
    public boolean check(List<Store<Object>> scanned, StringBuilder buffer) {
        List<Store<Method[]>> methodsCollect = scanned.stream()
                .map(object -> Store.of(object.getPackageName(), (Method[]) object.getValue()))
                .collect(Collectors.toList());

        boolean flag = true;
        for (Store<Method[]> methodsStore : methodsCollect) {
            boolean hasGetter = checkGetterMethod(methodsStore, buffer);
            if (hasGetter) {
                flag = false;
            }
        }
        return flag;
    }

    private boolean checkGetterMethod(Store<Method[]> methodsStore, StringBuilder buffer) {
        boolean flag = false;
        Method[] methods = methodsStore.getValue();
        for (Method method : methods) {
            if (isPublic(method) && method.getName().startsWith(VALIDATION_KEYWORD)) {
                Store<Method> methodStore = Store.of(methodsStore.getPackageName(), method);
                buffer.append(messageGenerator.generateMessage(methodStore, false));
                flag = true;
            }
        }
        return flag;
    }

    private boolean isPublic(Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers);
    }
}
