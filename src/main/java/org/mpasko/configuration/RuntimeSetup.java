package org.mpasko.configuration;

public class RuntimeSetup {
    public static void configure() {
        configureLogger();
        requiredToLoadLargeXml();
    }

    private static void configureLogger() {

    }

    private static void requiredToLoadLargeXml() {
        System.getProperties().setProperty("jdk.xml.entityExpansionLimit", "0");
    }
}
