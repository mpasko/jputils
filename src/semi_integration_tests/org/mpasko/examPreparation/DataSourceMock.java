package org.mpasko.examPreparation;

import org.mpasko.japanese.runners.workflow.IDataSource;
import org.mpasko.dictionary.Dictionary;

public class DataSourceMock implements IDataSource {
    public Dictionary getListeningWhitelist() {
        return null;
    }

    public Dictionary getReadingWhitelist() {
        return null;
    }

    public Dictionary getListeningBlacklist() {
        return null;
    }

    public Dictionary getReadingBlacklist() {
        return null;
    }

    public Dictionary getGlobalDictionary() {
        return null;
    }
}