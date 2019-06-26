package org.mpasko.repository.dataSource;

import org.mpasko.configuration.FileConfigLoader;
import org.mpasko.configuration.configurationModel.Configuration;

public class DataSourceFactory {
    public static IDataSource build() {
        Configuration config = new FileConfigLoader().loadConfig();
        switch(config.wordListLoadingMode) {
            case FILESYSTEM_CACHE:
                return new DataSourcesFilesystem();
            case REGENERATE:
                return new DataSources();
            case IN_MEMORY_CACHE:
                return new DataSourceCache();
            default:
                return new DataSourceCache();
        }
    }
}
