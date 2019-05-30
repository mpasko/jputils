package org.mpasko.dictionary;

import org.mpasko.commons.DictEntry;

import java.util.ArrayList;
import java.util.Comparator;

public class LightDictionary extends AbstractDictionary {

    public LightDictionary() {
        this.dict = new ArrayList<>();
    }

    @Override
    public DictEntry find(String kanji, String reading) {
        sortWhenNeeded();
        return findBisect(kanji, 0, dict.size()-1);
    }

    private void sortWhenNeeded() {
        if (!pristine) {
            this.dict.sort(Comparator.comparing(item -> item.kanji));
        }
        pristine = true;
    }

    @Override
    public DictEntry findStrict(String kanji, String reading) {
        DictEntry standard = find(kanji, reading);
        return standard.writing.equalsIgnoreCase(reading) ? standard : null;
    }

    private DictEntry findBisect(String keyword, int start, int stop) {
        DictEntry startValue = this.dict.get(start);
        DictEntry stopValue = this.dict.get(stop);
        if (start >= stop - 1) {
            if (startValue.kanji.equalsIgnoreCase(keyword)) {
                return startValue;
            } else if (stopValue.kanji.equalsIgnoreCase(keyword)) {
                return stopValue;
            } else {
                return null;
            }
        }
        int medium = (start + stop) / 2;
        DictEntry mediumValue = this.dict.get(medium);
        if (keyword.compareTo(mediumValue.kanji)<0) {
            return findBisect(keyword, start, medium - 1);
        } else if (keyword.compareTo(mediumValue.kanji)>0) {
            return findBisect(keyword, medium + 1, stop);
        } else if (mediumValue.kanji.equalsIgnoreCase(keyword)) {
            return mediumValue;
        } else {
            return null;
        }
    }
}
