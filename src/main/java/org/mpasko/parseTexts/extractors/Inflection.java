package org.mpasko.parseTexts.extractors;

import org.mpasko.commons.DictEntry;

public class Inflection {
    public DictEntry dictionaryWord;
    public String originalForm;
    public String comment;

    public Inflection(DictEntry dictForm, String original) {
        this(dictForm, original, "");
    }

    public Inflection(DictEntry dictForm, String original, String comment) {
        this.dictionaryWord = dictForm;
        this.originalForm = original;
        this.comment = comment;
    }
}
