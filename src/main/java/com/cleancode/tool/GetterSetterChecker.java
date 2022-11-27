package com.cleancode.tool;

public interface GetterSetterChecker {

    boolean checkNotUsingGetterMethod(String basePackageName, boolean isRecursive);

    boolean checkNotUsingSetterMethod(String basePackageName, boolean isRecursive);
}
