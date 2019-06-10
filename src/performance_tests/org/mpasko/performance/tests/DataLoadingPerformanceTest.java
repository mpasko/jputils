package org.mpasko.performance.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mpasko.web.DataSourceCache;

public class DataLoadingPerformanceTest {
    private final int TIMEOUT = 2 * TimeUnits.MINUTE;

    @Rule
    public Timeout globalTimeout = new Timeout(2 * TIMEOUT);

    @Test
    public void data_container_should_load_in_reasonable_time() {
        System.out.println("data_container_should_load_in_reasonable_time");
        new DataSourceCache();
    }
}
