package org.lhg.codechecker.tool;

import org.lhg.codechecker.util.FileScanner;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * The scanner is an enum that extracts the necessary reflection information.
 * Extract constructors, methods, fields, and parameters information
 * from the extracted class and provide it in {@link Store} format.
 */
public enum Scanners {
    CONSTRUCTOR {
        @Override
        void scan(String basePackageName, List<Store<Object>> list) {
            List<Class<?>> classes = fileScanner.scan(basePackageName);
            classes.forEach(clazz -> list.add(Store.of(clazz.getName(), clazz.getDeclaredConstructors())));
        }
    },
    METHODS {
        @Override
        void scan(String basePackageName, List<Store<Object>> list) {
            List<Class<?>> classes = fileScanner.scan(basePackageName);
            classes.forEach(clazz -> list.add(Store.of(clazz.getName(), clazz.getDeclaredMethods())));
        }
    },
    FIELDS {
        @Override
        void scan(String basePackageName, List<Store<Object>> list) {
            List<Class<?>> classes = fileScanner.scan(basePackageName);
            classes.forEach(clazz -> list.add(Store.of(clazz.getName(), clazz.getDeclaredFields())));
        }
    },
    @Deprecated
    PARAMETERS {
        @Override
        void scan(String basePackageName, List<Store<Object>> list) {
            List<Class<?>> classes = fileScanner.scan(basePackageName);
            for (Class<?> clazz : classes) {
                Stream.of(clazz.getDeclaredMethods()).forEach(
                        method -> list.add(Store.of(clazz.getName(), method.getParameters())));
            }
        }
    };

    private static final FileScanner fileScanner = new FileScanner(true);

    abstract void scan(String basePackageName, List<Store<Object>> list);

    public List<Store<Object>> scan(String basePackageName) {
        List<Store<Object>> stores = new ArrayList<>();
        scan(basePackageName, stores);
        validateStores(stores);
        return stores;
    }

    void validateStores(List<Store<Object>> stores) {
        if (stores.isEmpty()) {
            throw new IllegalArgumentException("Unable to find class to check code. please input valid package path");
        }
    }

    public void setRecursive(boolean isRecursive) {
        fileScanner.setRecursive(isRecursive);
    }
}
