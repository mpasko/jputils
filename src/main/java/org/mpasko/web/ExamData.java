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


    private final ActivityData reading;
    private final ActivityData listening;

    ExamData(ActivityData reading, ActivityData listening) {
        this.reading = reading;
        this.listening = listening;
    }

    public ActivityData getReading() {
        return reading;
    }

    public ActivityData getListening() {
        return listening;
    }
}
