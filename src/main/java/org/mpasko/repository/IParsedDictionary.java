package org.mpasko.repository;

import org.mpasko.dictionary.Dictionary;

public interface IParsedDictionary {
    Dictionary getParsedDictionary(String userId, String resourceId);
}
