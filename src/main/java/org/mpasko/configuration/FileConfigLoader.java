package org.mpasko.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mpasko.configuration.configurationModel.Configuration;
import org.mpasko.util.Filesystem;
import org.mpasko.web.server.JsonParser;
import org.mpasko.web.server.JsonTransformer;

import java.io.IOException;

public class FileConfigLoader implements IConfigLoader {
    private static final Logger LOGGER = LogManager.getLogger(FileConfigLoader.class.getName());

    @Override
    public Configuration loadConfig() {
        String source = DefaultPaths.configFile;
        try {
            String raw = new Filesystem().loadFile(source);
            return JsonParser.parse(raw, Configuration.class);
        } catch (IOException e) {
            String message = String.format("Error during loading configuration from: %s", source);
            LOGGER.error(message, e);
            return null;
        }
    }

    @Override
    public void saveConfig(Configuration config) {
        String destination = DefaultPaths.configFile;
        try {
            String raw = new JsonTransformer().render(config);
            new Filesystem().saveFile(destination, raw);
        } catch (Exception e) {
            String message = String.format("Error during saving configuration to: %s", destination);
            LOGGER.error(message, e);
        }
    }
}
