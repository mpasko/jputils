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
        this.dict.sort(Comparator.comparing(item -> item.kanji));
        return findBisect(kanji, 0, dict.size()-1);
    }

    @Override
    public DictEntry findStrict(String kanji, String reading) {
        return find(kanji, reading);
    }

    private DictEntry findBisect(String keyword, int start, int stop) {
        DictEntry startValue = this.dict.get(start);
        DictEntry stopValue = this.dict.get(start);
        if (start >= stop - 1) {
            if (startValue.kanji.equalsIgnoreCase(keyword)) {
                return startValue;
            } else if (stopValue.kanji.equalsIgnoreCase(keyword)) {
                return stopValue;
            } else {
                return null;
            }
        }
        int medium = start + stop / 2;
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
