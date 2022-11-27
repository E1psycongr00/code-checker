package org.lhg.codechecker.tool;

import org.lhg.codechecker.tool.builder.CodeCheckerBuilder;
import org.lhg.codechecker.tool.checker.Checker;
import org.lhg.codechecker.tool.checker.ConstructorChecker;
import org.lhg.codechecker.tool.checker.FieldChecker;
import org.lhg.codechecker.tool.checker.GetterChecker;
import org.lhg.codechecker.tool.checker.MethodChecker;
import org.lhg.codechecker.tool.checker.ParameterChecker;
import org.lhg.codechecker.tool.checker.SetterChecker;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodeChecker implements CountChecker, GetterSetterChecker {

    private static final int INF = Integer.MAX_VALUE;

    private final StringBuilder buffer = new StringBuilder();
    private final int limitParameters;
    private final int limitFields;
    private final int limitConstructors;
    private final int limitMethods;

    private CodeChecker(int limitParameters, int limitFields, int limitConstructors, int limitMethods) {
        this.limitParameters = limitParameters;
        this.limitFields = limitFields;
        this.limitConstructors = limitConstructors;
        this.limitMethods = limitMethods;
    }

    public static ConfigurationBuilder rules() {
        return new ConfigurationBuilder();
    }

    public boolean checkAll(String basePackageName, boolean isRecursive) {
        boolean checkParams = checkParameterCount(basePackageName, isRecursive);
        boolean checkConstructors = checkConstructorCount(basePackageName, isRecursive);
        boolean checkMethods = checkMethodCount(basePackageName, isRecursive);
        boolean checkFields = checkFieldCount(basePackageName, isRecursive);
        return checkFields && checkMethods && checkConstructors && checkParams;
    }

    @Override
    public boolean checkParameterCount(String basePackageName, boolean isRecursive) {
        Scanners.METHODS.setRecursive(isRecursive);
        List<Store<Object>> scanned = Scanners.METHODS.scan(basePackageName);
        Checker checker = new ParameterChecker(limitParameters);
        return checker.check(scanned, buffer);
    }

    @Override
    public boolean checkMethodCount(String basePackageName, boolean isRecursive) {
        Scanners.METHODS.setRecursive(isRecursive);
        List<Store<Object>> scanned = Scanners.METHODS.scan(basePackageName);
        Checker checker = new MethodChecker(limitMethods);
        return checker.check(scanned, buffer);
    }

    @Override
    public boolean checkConstructorCount(String basePackageName, boolean isRecursive) {
        Scanners.CONSTRUCTOR.setRecursive(isRecursive);
        List<Store<Object>> scanned = Scanners.CONSTRUCTOR.scan(basePackageName);
        Checker checker = new ConstructorChecker(limitConstructors);
        return checker.check(scanned, buffer);
    }

    @Override
    public boolean checkFieldCount(String basePackageName, boolean isRecursive) {
        Scanners.FIELDS.setRecursive(isRecursive);
        List<Store<Object>> scanned = Scanners.FIELDS.scan(basePackageName);
        Checker checker = new FieldChecker(limitFields);
        return checker.check(scanned, buffer);
    }

    @Override
    public boolean checkNotUsingGetterMethod(Class<?>... classes) {
        List<Store<Object>> stores = Stream.of(classes)
                .map(clazz -> Store.of(clazz.getPackageName(), (Object) clazz.getDeclaredMethods()))
                .collect(Collectors.toList());
        Checker checker = new GetterChecker();
        return checker.check(stores, buffer);
    }

    @Override
    public boolean checkNotUsingGetterMethod(String basePackageName, boolean isRecursive) {
        Scanners.METHODS.setRecursive(isRecursive);
        List<Store<Object>> scanned = Scanners.METHODS.scan(basePackageName);
        Checker checker = new GetterChecker();
        return checker.check(scanned, buffer);
    }

    @Override
    public boolean checkNotUsingSetterMethod(Class<?>... classes) {
        List<Store<Object>> stores = Stream.of(classes)
                .map(clazz -> Store.of(clazz.getPackageName(), (Object) clazz.getDeclaredMethods()))
                .collect(Collectors.toList());
        Checker checker = new SetterChecker();
        return checker.check(stores, buffer);
    }

    @Override
    public boolean checkNotUsingSetterMethod(String basePackageName, boolean isRecursive) {
        Scanners.METHODS.setRecursive(isRecursive);
        List<Store<Object>> scanned = Scanners.METHODS.scan(basePackageName);
        Checker checker = new SetterChecker();
        return checker.check(scanned, buffer);
    }

    public String getMessage() {
        return buffer.toString();
    }

    public static class ConfigurationBuilder implements CodeCheckerBuilder {
        private int limitParameters = INF;
        private int limitFields = INF;
        private int limitConstructors = INF;
        private int limitMethods = INF;

        public ConfigurationBuilder limitParameters(int count) {
            this.limitParameters = count;
            return this;
        }

        public ConfigurationBuilder limitFields(int count) {
            this.limitFields = count;
            return this;
        }

        public ConfigurationBuilder limitConstructors(int count) {
            this.limitConstructors = count;
            return this;
        }

        public ConfigurationBuilder limitMethods(int count) {
            this.limitMethods = count;
            return this;
        }

        @Override
        public CodeChecker build() {
            return new CodeChecker(limitParameters, limitFields, limitConstructors, limitMethods);
        }
    }
}