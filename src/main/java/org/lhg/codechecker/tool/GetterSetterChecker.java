package org.lhg.codechecker.tool;

public interface GetterSetterChecker {

    boolean checkNotUsingGetterMethod(Class<?> ...classes);

    boolean checkNotUsingGetterMethod(String basePackageName, boolean isRecursive);

    boolean checkNotUsingSetterMethod(Class<?> ...classes);

    boolean checkNotUsingSetterMethod(String basePackageName, boolean isRecursive);
}
