package org.lhg.codechecker.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * It scans the file system for classes in a given package
 */
public class FileScanner implements Scanner<Class<?>> {

    private final ClassFinder<File> classFinder = new FileClassFinderImpl();
    private boolean isRecursive;

    public FileScanner(boolean isRecursive) {
        this.isRecursive = isRecursive;
        classFinder.setRecursive(isRecursive);
    }

    private List<File> getFiles(ClassLoader classLoader, String path) throws IOException {
        List<File> files = new ArrayList<>();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            files.add(new File(resource.getFile()));
        }
        return files;
    }

    public void setRecursive(boolean recursive) {
        isRecursive = recursive;
        classFinder.setRecursive(recursive);
    }

    @Override
    public List<Class<?>> scan(String basePackageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = basePackageName.replace('.', '/');
        List<Class<?>> classes = new ArrayList<>();
        try {
            classes =  addClasses(basePackageName, classLoader, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private List<Class<?>> addClasses(String basePackageName, ClassLoader classLoader, String path) throws IOException {
        List<Class<?>> classes = new ArrayList<>();
        List<File> files = getFiles(classLoader, path);
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(classFinder.findClasses(file, basePackageName));
            }
        }
        return classes;
    }
}
