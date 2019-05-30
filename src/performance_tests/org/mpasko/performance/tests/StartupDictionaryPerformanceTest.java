package org.mpasko.performance.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mpasko.loadres.AllDictionaries;

public class StartupDictionaryPerformanceTest {

    public static final String USER_DIR_PROPERTY = "user.dir";

    private final int SECOND = 1000;
    private final int MINUTE = 60 * SECOND;
    private final int TIMEOUT = 2 * MINUTE;

    @Rule
    public Timeout globalTimeout= new Timeout(2 * TIMEOUT);

    @Test
    public void dictionary_should_load_in_reasonable_time() {
        System.out.println("dictionary_should_load_in_reasonable_time");
        AllDictionaries.load();
    }
}
