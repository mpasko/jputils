package org.mpasko.repository;

import org.mpasko.dictionary.IDictionary;

public interface IExamData {
    IDictionary getExamData(String userId, String activity, String phase);
}
