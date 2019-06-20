package org.mpasko.japanese.runners.workflow;

import org.mpasko.repository.dataSource.DataSourceCache;
import org.mpasko.repository.dataSource.DataSourcesFilesystem;

public class RegenerateSourcesCache {
    public static void main(String[] args) {
        new RegenerateSourcesCache().start();
    }

    private void start() {
        DataSourceCache cache = new DataSourceCache();
        cache.reloadDataSources();
        cache.getGlobalDictionary().write(DataSourcesFilesystem.GLOBAL_DICTIONARY);
        cache.getListeningBlacklist().write(DataSourcesFilesystem.LISTENING_BLACKLIST);
        cache.getReadingBlacklist().write(DataSourcesFilesystem.READING_BLACKLIST);
        cache.getListeningWhitelist().write(DataSourcesFilesystem.LISTENING_WHITELIST);
        cache.getReadingWhitelist().write(DataSourcesFilesystem.READING_WHITELIST);
    }
}
