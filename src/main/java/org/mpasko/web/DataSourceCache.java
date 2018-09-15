package org.mpasko.web;

import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.runners.workflow.DataSources;
import org.mpasko.japanese.runners.workflow.GenerateOnyomiWhitelist;
import org.mpasko.web.textpreview.FileIdMap;

public class DataSourceCache {
    public Dictionary listeningWhitelist;
    public Dictionary readingWhitelist;
    public Dictionary listeningBlacklist;
    public Dictionary readingBlacklist;
    public DataSources dataSources;

    public DataSourceCache() {
        reloadDataSources();
    }

    public void reloadDataSources() {
        new GenerateOnyomiWhitelist().start();
        dataSources = new DataSources();
        readingBlacklist = dataSources.readingBlacklist();
        listeningBlacklist = dataSources.listeningBlacklist();
        readingWhitelist = dataSources.readingWhitelist();
        listeningWhitelist = dataSources.listeningWhitelist();
    }
}
