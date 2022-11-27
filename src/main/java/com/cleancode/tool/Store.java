package com.cleancode.tool;


/**
 * Store is the most important data type in the Code-Checker project.
 * this is a generic class that has a packageName and a value.
 * because it has a type parameter V. The type parameter is used in the declaration of the value field.
 */
public class Store<V> {

    String packageName;
    V value;

    public Store(String packageName, V value) {
        this.packageName = packageName;
        this.value = value;
    }

    public static <V> Store<V> of(String packageName, V value) {
        return new Store<>(packageName, value);
    }

    public String getPackageName() {
        return packageName;
    }

    public V getValue() {
        return value;
    }
}
