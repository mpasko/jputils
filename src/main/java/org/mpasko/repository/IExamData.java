package org.mpasko.repository;

import org.mpasko.dictionary.Dictionary;

public interface IExamData {
    Dictionary getExamData(String userId, String activity, String phase);
}
