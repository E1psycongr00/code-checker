package com.cleancode.tool;

import com.cleancode.tool.builder.CodeCheckerBuilder;
import com.cleancode.tool.checker.Checker;
import com.cleancode.tool.checker.ConstructorChecker;
import com.cleancode.tool.checker.FieldChecker;
import com.cleancode.tool.checker.GetterChecker;
import com.cleancode.tool.checker.MethodChecker;
import com.cleancode.tool.checker.ParameterChecker;
import com.cleancode.tool.checker.SetterChecker;
import java.util.ArrayList;
import java.util.List;

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
    public boolean checkNotUsingGetterMethod(Class<?> clazz) {
        List<Store<Object>> stores = new ArrayList<>();
        stores.add(Store.of(clazz.getPackageName(), clazz.getDeclaredMethods()));
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
    public boolean checkNotUsingSetterMethod(Class<?> clazz) {
        List<Store<Object>> stores = new ArrayList<>();
        stores.add(Store.of(clazz.getPackageName(), clazz.getDeclaredMethods()));
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