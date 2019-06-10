package org.mpasko.japanese.runners.installation;

import org.mpasko.configuration.Configuration;
import org.mpasko.configuration.FileConfigLoader;

public class InitializeConfigFile {
    public static void main(String[] args) {
        initialize();
    }

    public static void initialize() {
        Configuration config = new Configuration();
        config.wordListLoadingMode = "regenerate";
        new FileConfigLoader().saveConfig(config);
    }
}
