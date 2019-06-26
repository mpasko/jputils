package org.mpasko.configuration;

import org.mpasko.configuration.configurationModel.Configuration;

public interface IConfigLoader {
    Configuration loadConfig();

    void saveConfig(Configuration config);
}
