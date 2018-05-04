/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web;

import java.util.List;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.wordfilters.GenericFilter;
import org.mpasko.japanese.wordfilters.InversionOf;
import org.mpasko.japanese.wordfilters.ItemExistsInDictionary;

/**
 *
 * @author marcin
 */
public class ExamData {

    private final Dictionary examDict;
    private final Dictionary listeningWhitelist;
    private final Dictionary readingWhitelist;
    private final Dictionary listeningBlacklist;
    private final Dictionary readingBlacklist;

    ExamData(Dictionary examDict,
            Dictionary listeningBlacklist,
            Dictionary readingBlacklist,
            Dictionary listeningWhitelist,
            Dictionary readingWhitelist) {
        this.examDict = examDict;
        this.listeningBlacklist = listeningBlacklist;
        this.readingBlacklist = readingBlacklist;
        this.listeningWhitelist = listeningWhitelist;
        this.readingWhitelist = readingWhitelist;
    }

    public int getTotal() {
        return examDict.size();
    }

    public List<DictEntry> getReadingBlack() {
        return in(readingBlacklist).filter(examDict).getDict();
    }

    public List<DictEntry> getListeningBlack() {
        return in(listeningBlacklist).filter(examDict).getDict();
    }

    public List<DictEntry> getListeningUnprocessed() {
        final Dictionary notInWhite = notIn(listeningWhitelist).filter(examDict);
        return notIn(listeningBlacklist).filter(notInWhite).getDict();
    }

    public List<DictEntry> getReadingUnprocessed() {
        final Dictionary notInWhite = notIn(readingWhitelist).filter(examDict);
        return notIn(readingBlacklist).filter(notInWhite).getDict();
    }

    public List<DictEntry> getReadingWhite() {
        return in(readingWhitelist).filter(examDict).getDict();
    }

    public List<DictEntry> getListeningWhite() {
        return in(listeningWhitelist).filter(examDict).getDict();
    }

    private GenericFilter notIn(Dictionary dict) {
        return new InversionOf(in(dict));
    }

    private GenericFilter in(Dictionary dict) {
        return new ItemExistsInDictionary(dict.getDict());
    }
}
