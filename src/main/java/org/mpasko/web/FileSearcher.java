/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class FileSearcher {

    private static List<String> roots = Arrays.asList("improved_workflow/words");

    Map<String, String> search(String query) {
        Map<String, String> results = new TreeMap<>();
        searchRecursively(query, roots, results);
        return results;
    }

    private void searchRecursively(String query, List<String> roots, Map<String, String> results) {
        roots.stream().forEach(root -> searchRecursivelyInFolder(query, root, results));
    }

    private void searchRecursivelyInFolder(String query, String root, Map<String, String> results) {
        Util.getSubfiles(root)
                .stream()
                .forEach(file -> searchInFile(query, root + "/" + file, results));
        List<String> subdirPaths = Util.getSubdirectories(root)
                .stream()
                .map(subdir -> root + "/" + subdir)
                .collect(Collectors.toList());
        searchRecursively(query, subdirPaths, results);
    }

    private void searchInFile(String query, String path, Map<String, String> results) {
        System.out.println("Searching in: " + path);
        String content = Util.loadFile(path);
        Stream.of(content.split("\n"))
                .filter(compared -> compared.contains(query))
                .forEach(matched -> results.put(path, matched));
    }

}
