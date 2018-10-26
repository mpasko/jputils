package org.mpasko.parseTexts.extractors;

import org.mpasko.dictionary.Dictionary;

import java.util.List;

public interface IExtractor {
    List<Inflection> extract(String text);
}
