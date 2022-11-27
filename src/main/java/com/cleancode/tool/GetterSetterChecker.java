package com.cleancode.tool;

public interface GetterSetterChecker {

    boolean checkGetterMethod(String basePackageName, boolean isRecursive);

    boolean checkSetterMethod(String basePackageName, boolean isRecursive);
}
