package org.mpasko.japanese.runners.workflow;

import org.mpasko.dictionary.Dictionary;

public interface IDataSource {
    Dictionary getListeningWhitelist();
    Dictionary getReadingWhitelist();
    Dictionary getListeningBlacklist();
    Dictionary getReadingBlacklist();
    Dictionary getGlobalDictionary();
}
