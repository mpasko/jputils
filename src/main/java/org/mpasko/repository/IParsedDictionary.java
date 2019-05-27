package org.mpasko.repository;

import org.mpasko.dictionary.IDictionary;

public interface IParsedDictionary {
    IDictionary getParsedDictionary(String userId, String resourceId);
}
