package org.mpasko.japanese.runners.installation;

import org.mpasko.configuration.configurationModel.Configuration;
import org.mpasko.configuration.FileConfigLoader;
import org.mpasko.configuration.configurationModel.DictionarySources;
import org.mpasko.configuration.configurationModel.WordListLoadingMode;

public class InitializeConfigFile {
    public static void main(String[] args) {
        initialize();
    }

    public static void initialize() {
        Configuration config = new Configuration();
        config.wordListLoadingMode = WordListLoadingMode.FILESYSTEM_CACHE;
        config.dictionarySources = new DictionarySources();
        config.dictionarySources.enableNameDictionary = false;
        new FileConfigLoader().saveConfig(config);
    }
}
