package org.mpasko.util;

import java.util.List;

public class Traverser {

    public static void traverse(String basePath, FileTraverser traverser) {
        traverse(basePath, "", traverser);
    }

    private static void traverse(String basePath, String relativePath, FileTraverser traverser) {
        String actualPath = basePath+"/"+relativePath;
        List<String> subDirs = new Filesystem().getSubdirectories(actualPath);
        subDirs.stream().forEach(subdir -> traverse(basePath, relativePath+"/"+subdir, traverser));
        List<String> subFiles = new Filesystem().getSubfiles(actualPath);
        subFiles.stream().forEach(subfile -> traverser.traverse(basePath, relativePath, subfile));
    }

    @FunctionalInterface
    public interface DirectoryTraverser {
        void traverse(String basePath, String relativePath);
    }

    @FunctionalInterface
    public interface FileTraverser {
        void traverse(String basePath, String relativePath, String filename);
    }
}
