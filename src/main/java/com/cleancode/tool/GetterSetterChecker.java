package com.cleancode.tool;

public interface GetterSetterChecker {

    boolean checkGetterMethod(String basePackageName, boolean isRecursive, boolean isAllow);

    boolean checkSetterMethod(String basePackageName, boolean isRecursive, boolean isAllow);
}
