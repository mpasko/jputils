package org.mpasko.web;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.runners.workflow.DataSources;
import org.mpasko.japanese.runners.workflow.GenerateOnyomiWhitelist;
import org.mpasko.japanese.runners.workflow.IDataSource;

public class DataSourceCache implements IDataSource {
    private Dictionary listeningWhitelist;
    private Dictionary readingWhitelist;
    private Dictionary listeningBlacklist;
    private Dictionary readingBlacklist;
    private DataSources dataSources;

    public DataSourceCache() {
        reloadDataSources();
    }

    public void reloadDataSources() {
        new GenerateOnyomiWhitelist().start();
        setDataSources(new DataSources());
        setReadingBlacklist(getDataSources().getReadingBlacklist());
        setListeningBlacklist(getDataSources().getListeningBlacklist());
        setReadingWhitelist(getDataSources().getReadingWhitelist());
        setListeningWhitelist(getDataSources().getListeningWhitelist());
    }

    @Override
    public Dictionary getListeningWhitelist() {
        return listeningWhitelist;
    }

    public void setListeningWhitelist(Dictionary listeningWhitelist) {
        this.listeningWhitelist = listeningWhitelist;
    }

    @Override
    public Dictionary getReadingWhitelist() {
        return readingWhitelist;
    }

    public void setReadingWhitelist(Dictionary readingWhitelist) {
        this.readingWhitelist = readingWhitelist;
    }

    @Override
    public Dictionary getListeningBlacklist() {
        return listeningBlacklist;
    }

    public void setListeningBlacklist(Dictionary listeningBlacklist) {
        this.listeningBlacklist = listeningBlacklist;
    }

    @Override
    public Dictionary getReadingBlacklist() {
        return readingBlacklist;
    }

    @Override
    public Dictionary getGlobalDictionary() {
        return dataSources.getGlobalDictionary();
    }

    public void setReadingBlacklist(Dictionary readingBlacklist) {
        this.readingBlacklist = readingBlacklist;
    }

    public DataSources getDataSources() {
        return dataSources;
    }

    public void setDataSources(DataSources dataSources) {
        this.dataSources = dataSources;
    }
}
