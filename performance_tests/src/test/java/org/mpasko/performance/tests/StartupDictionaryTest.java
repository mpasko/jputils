package org.mpasko.performance.tests;

import org.junit.Test;
import org.mpasko.loadres.AllDictionaries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StartupDictionaryTest {

    public static final String USER_DIR_PROPERTY = "user.dir";

    @Test
    public void dictionary_should_load_in_reasonable_time() {
        System.out.println("dictionary_should_load_in_reasonable_time");

        String cwdPath = System.getProperty(USER_DIR_PROPERTY);
        System.out.printf("Working Directory = %s%n", cwdPath);
        String newPath = pathLevelUp(cwdPath);
        System.setProperty(USER_DIR_PROPERTY, newPath);
        System.out.printf("New Working Directory = %s%n", System.getProperty(USER_DIR_PROPERTY));
        AllDictionaries.load();
    }

    private String pathLevelUp(String cwdPath) {
        List<String> splited = new ArrayList<>(Arrays.asList(cwdPath.split("/|\\\\")));
        splited.remove(splited.size() - 1);
        return splited.stream().collect(Collectors.joining("/"));
    }
}
