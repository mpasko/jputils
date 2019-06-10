package org.mpasko.performance.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mpasko.loadres.AllDictionaries;

public class StartupDictionaryPerformanceTest {

    private final int TIMEOUT = 2 * TimeUnits.MINUTE;

    @Rule
    public Timeout globalTimeout = new Timeout(TIMEOUT);

    @Test
    public void dictionary_should_load_in_reasonable_time() {
        System.out.println("dictionary_should_load_in_reasonable_time");
        AllDictionaries.load();
    }
}
