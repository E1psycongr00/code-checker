package org.lhg.codechecker.tool;

public interface CountChecker {

    boolean checkParameterCount(String basePackageName, boolean isRecursive);

    boolean checkMethodCount(String basePackageName, boolean isRecursive);

    boolean checkConstructorCount(String basePackageName, boolean isRecursive);

    boolean checkFieldCount(String basePackageName, boolean isRecursive);
}
