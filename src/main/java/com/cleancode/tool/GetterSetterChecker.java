package com.cleancode.tool;

public interface GetterSetterChecker {

    boolean checkNotUsingGetterMethod(Class<?> clazz);

    boolean checkNotUsingGetterMethod(String basePackageName, boolean isRecursive);

    boolean checkNotUsingSetterMethod(Class<?> clazz);

    boolean checkNotUsingSetterMethod(String basePackageName, boolean isRecursive);
}
