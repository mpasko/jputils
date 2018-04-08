/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web;

import java.util.List;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
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

    ExamData(Dictionary examDict, Dictionary listeningWhitelist, Dictionary readingWhitelist) {
        this.examDict = examDict;
        this.listeningWhitelist = listeningWhitelist;
        this.readingWhitelist = readingWhitelist;
    }

    public int getTotal() {
        return examDict.size();
    }

    public List<DictEntry> getReadingBlack() {
        ItemExistsInDictionary filter = new ItemExistsInDictionary(readingWhitelist.getDict());
        return new InversionOf(filter).filter(examDict).getDict();
    }

    public List<DictEntry> getListeningBlack() {
        ItemExistsInDictionary filter = new ItemExistsInDictionary(listeningWhitelist.getDict());
        return new InversionOf(filter).filter(examDict).getDict();
    }

    public List<DictEntry> getReadingWhite() {
        ItemExistsInDictionary filter = new ItemExistsInDictionary(readingWhitelist.getDict());
        return filter.filter(examDict).getDict();
    }

    public List<DictEntry> getListeningWhite() {
        ItemExistsInDictionary filter = new ItemExistsInDictionary(listeningWhitelist.getDict());
        return filter.filter(examDict).getDict();
    }
}
