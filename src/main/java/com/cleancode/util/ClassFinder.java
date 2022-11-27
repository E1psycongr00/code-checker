package com.cleancode.util;

import java.util.List;

public interface ClassFinder<T> {

    List<Class<?>> findClasses(T object, String packageName);

    void setRecursive(boolean isRecursive);
}

