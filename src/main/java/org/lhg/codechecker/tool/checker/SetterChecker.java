package org.lhg.codechecker.tool.checker;

import org.lhg.codechecker.tool.Store;
import org.lhg.codechecker.tool.message.bool.BooleanMessageGenerator;
import org.lhg.codechecker.tool.message.bool.SetterMessageGenerator;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class SetterChecker implements Checker {

    private static final String VALIDATION_KEYWORD = "set";
    private final BooleanMessageGenerator<Method> messageGenerator;


    public SetterChecker() {
        this.messageGenerator = new SetterMessageGenerator();
    }

    @Override
    public boolean check(List<Store<Object>> scanned, StringBuilder buffer) {
        List<Store<Method[]>> methodsCollect = scanned.stream()
                .map(object -> Store.of(object.getPackageName(), (Method[]) object.getValue()))
                .collect(Collectors.toList());

        boolean flag = true;
        for (Store<Method[]> methodsStore : methodsCollect) {
            boolean hasSetter = checkSetterMethod(methodsStore, buffer);
            if (hasSetter) {
                flag = false;
            }
        }
        return flag;
    }

    private boolean checkSetterMethod(Store<Method[]> methodsStore, StringBuilder buffer) {
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
