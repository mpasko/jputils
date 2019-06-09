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
        return findDefault(kanji);
    }

    private void sortWhenNeeded() {
        if (!pristine) {
            this.dict.sort(Comparator.comparing(item -> item.serializedKeywords()));
        }
        pristine = true;
    }

    @Override
    public DictEntry findStrict(String kanji, String reading) {
        DictEntry standard = find(kanji, reading);
        return standard.serializedReadings().equalsIgnoreCase(reading) ? standard : null;
    }

    @Override
    public DictEntry findDefault(String kanji) {
        sortWhenNeeded();
        return findBisect(kanji, 0, dict.size()-1);
    }

    private DictEntry findBisect(String keyword, int start, int stop) {
        DictEntry startValue = this.dict.get(start);
        DictEntry stopValue = this.dict.get(stop);
        if (start >= stop - 1) {
            if (startValue.serializedKeywords().equalsIgnoreCase(keyword)) {
                return startValue;
            } else if (stopValue.serializedKeywords().equalsIgnoreCase(keyword)) {
                return stopValue;
            } else {
                return null;
            }
        }
        int medium = (start + stop) / 2;
        DictEntry mediumValue = this.dict.get(medium);
        if (keyword.compareTo(mediumValue.serializedKeywords())<0) {
            return findBisect(keyword, start, medium - 1);
        } else if (keyword.compareTo(mediumValue.serializedKeywords())>0) {
            return findBisect(keyword, medium + 1, stop);
        } else if (mediumValue.serializedKeywords().equalsIgnoreCase(keyword)) {
            return mediumValue;
        } else {
            return null;
        }
    }
}
