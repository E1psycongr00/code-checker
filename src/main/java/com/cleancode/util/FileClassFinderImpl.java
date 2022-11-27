package com.cleancode.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileClassFinderImpl implements ClassFinder<File> {

    private boolean isRecursive = true;

    @Override
    public void setRecursive(boolean recursive) {
        isRecursive = recursive;
    }

    @Override
    public List<Class<?>> findClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        searchFiles(classes, packageName, directory);
        return classes;
    }

    private void searchFiles(List<Class<?>> classes, String packageName, File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory() && isRecursive) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
                continue;
            }
            if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                addClasses(classes, className, classLoader);
            }
        }
    }

    private void addClasses(List<Class<?>> classes, String className, ClassLoader classLoader) {
        try {
            classes.add(Class.forName(className, false, classLoader));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
