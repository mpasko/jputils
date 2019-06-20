package org.mpasko.repository.dataSource;

import org.mpasko.configuration.DefaultPaths;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;

public class DataSourcesFilesystem implements IDataSource {

    public static final String LISTENING_WHITELIST = DefaultPaths.sourceCaches + "/listening_whitelist.txt";
    public static final String LISTENING_BLACKLIST = DefaultPaths.sourceCaches + "/listening_blacklist.txt";
    public static final String READING_WHITELIST = DefaultPaths.sourceCaches + "/reading_whitelist.txt";
    public static final String READING_BLACKLIST = DefaultPaths.sourceCaches + "/reading_blacklist.txt";
    public static final String GLOBAL_DICTIONARY = DefaultPaths.sourceCaches + "/global_dictionary.txt";

    @Override
    public Dictionary getListeningWhitelist() {
        return new DictionaryFileLoader().loadTripleDict(LISTENING_WHITELIST);
    }

    @Override
    public Dictionary getReadingWhitelist() {
        return new DictionaryFileLoader().loadTripleDict(READING_WHITELIST);
    }

    @Override
    public Dictionary getListeningBlacklist() {
        return new DictionaryFileLoader().loadTripleDict(LISTENING_BLACKLIST);
    }

    @Override
    public Dictionary getReadingBlacklist() {
        return new DictionaryFileLoader().loadTripleDict(READING_BLACKLIST);
    }

    @Override
    public Dictionary getGlobalDictionary() {
        return new DictionaryFileLoader().loadTripleDict(GLOBAL_DICTIONARY);
    }

    @Override
    public void reloadDataSources() {

    }
}
