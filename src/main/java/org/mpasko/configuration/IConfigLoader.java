package org.mpasko.configuration;

public interface IConfigLoader {
    Configuration loadConfig();

    void saveConfig(Configuration config);
}
