package org.mpasko.repository.dataSource;

import org.mpasko.dictionary.Dictionary;

public interface IDataSource {
    Dictionary getListeningWhitelist();
    Dictionary getReadingWhitelist();
    Dictionary getListeningBlacklist();
    Dictionary getReadingBlacklist();
    Dictionary getGlobalDictionary();
    void reloadDataSources();
}
