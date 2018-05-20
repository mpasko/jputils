package org.mpasko.web;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.wordfilters.GenericFilter;
import org.mpasko.japanese.wordfilters.InversionOf;
import org.mpasko.japanese.wordfilters.ItemExistsInDictionary;

import java.util.List;

public class ActivityData {
    private final Dictionary examDict;
    private final Dictionary whitelist;
    private final Dictionary blacklist;

    ActivityData(Dictionary examDict,
             Dictionary blacklist,
             Dictionary whitelist) {
        this.examDict = examDict;
        this.blacklist = blacklist;
        this.whitelist = whitelist;
    }

    public List<DictEntry> getBlack() {
        return in(blacklist).filter(examDict).getDict();
    }

    public List<DictEntry> getUnprocessed() {
        final Dictionary notInWhite = notIn(whitelist).filter(examDict);
        return notIn(blacklist).filter(notInWhite).getDict();
    }

    public List<DictEntry> getWhite() {
        return in(whitelist).filter(examDict).getDict();
    }

    private GenericFilter notIn(Dictionary dict) {
        return new InversionOf(in(dict));
    }

    private GenericFilter in(Dictionary dict) {
        return new ItemExistsInDictionary(dict.getDict());
    }
}
