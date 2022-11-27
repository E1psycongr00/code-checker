package com.cleancode.tool;

import com.cleancode.tool.builder.CodeCheckerBuilder;

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

    @Override
    public boolean checkParameterCount(String basePackageName, boolean isRecursive) {
        return false;
    }

    @Override
    public boolean checkMethodCount(String basePackageName, boolean isRecursive) {
        return false;
    }

    @Override
    public boolean checkConstructorCount(String basePackageName, boolean isRecursive) {
        return false;
    }

    @Override
    public boolean checkFieldCount(String basePackageName, boolean isRecursive) {
        return false;
    }

    @Override
    public boolean checkGetterMethod(String basePackageName, boolean isRecursive) {
        return false;
    }

    @Override
    public boolean checkSetterMethod(String basePackageName, boolean isRecursive) {
        return false;
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
