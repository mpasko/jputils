/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons.analizers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;
import org.mpasko.commons.Furiganiser;
import org.mpasko.commons.KanjiDictionary;
import org.mpasko.commons.KanjiEntry;
import org.mpasko.commons.exceptions.CharacterNotFoundInDictionary;
import org.mpasko.loadres.KanjiDictLoader;
import org.mpasko.util.MapUtils;

/**
 *
 * @author marcin
 */
public class ReadingDecomposer {

    static String removeDots(String input) {
        int index = input.indexOf('.');
        if (index == -1) {
            index = input.length();
        }
        return input.substring(0, index);
    }

    private final KanjiDictionary kanjiDictionary;

    public ReadingDecomposer(KanjiDictionary kanjiDict) {
        this.kanjiDictionary = kanjiDict;
    }

    public static ReadingDecomposer initializeWithDefaultDict() {
        KanjiDictionary kanjiDict = new KanjiDictLoader().load(new KanjiDictLoader.Filter());
        return new ReadingDecomposer(kanjiDict);
    }

    public List<Entry<String, String>> decompose(List<DictEntry> entries) {
        final Optional<List<Entry<String, String>>> allItems = entries.stream()
                .map(entry -> decompose(entry))
                .reduce(ReadingDecomposer::mergeLists);
        if (!allItems.isPresent()) {
            return new LinkedList<>();
        }
        return allItems.get()
                .stream()
                .filter(item -> item.getValue() != null)
                .distinct()
                .collect(Collectors.toList());

    }

    public static <T> List<T> mergeLists(List<T> a, List<T> b) {
        LinkedList<T> result = new LinkedList<>();
        result.addAll(a);
        result.addAll(b);
        return result;
    }

    public List<Entry<String, String>> decompose(DictEntry entry) {
        final String word = entry.serializedKeywords();
        List<Character> containedKanji = Classifier.classify(word).getKanji();
        final LinkedList<Entry<String, String>> returnedList = new LinkedList<>();
        for (int index = 0; index < containedKanji.size(); ++index) {
            Character kanji = containedKanji.get(index);
            String reading = findReadingForFragment(word, entry.serializedReadings(), index, containedKanji);
            returnedList.add(MapUtils.entry(kanji.toString(), reading));
        }
        return returnedList;
    }

    private String findReadingForFragment(String word, String writing, int index, List<Character> containedKanji) {
        Character kanji = containedKanji.get(index);
        int start = word.indexOf(kanji);
        int stop = estimateStop(word, index, containedKanji);
        if (start < 0 || stop < 0 || start >= stop) {
            System.out.println(String.format("Something went wrong: %s, %s, %s", word, writing, containedKanji.get(index)));
            return null;
        }
        String fragment = word.substring(start, stop);
        String reading = findReadingInKanjiDict(kanji.toString(), fragment, writing);
        return reading;
    }

    private int estimateStop(String word, int index, List<Character> containedKanji) {
        boolean isLast = index == containedKanji.size() - 1;
        if (isLast) {
            return word.length();
        } else {
            return word.indexOf(containedKanji.get(index + 1));
        }
    }

    private String findReadingInKanjiDict(String kanji, String fragment, String writing) {
        KanjiEntry entry = findKanjiInDictionary(kanji);
        final Optional<String> optionalReading = mergeLists(entry.onyomi, entry.kunomi)
                .stream()
                .map(ReadingDecomposer::removeDots)
                .filter(seek -> writing.contains(fragment.replace(kanji, transformFragment(seek))))
                .findAny();
        if (optionalReading.isPresent()) {
            return optionalReading.get();
        } else {
            return null;
        }
    }

    private KanjiEntry findKanjiInDictionary(String kanji) throws CharacterNotFoundInDictionary {
        Optional<KanjiEntry> entryOptional = kanjiDictionary
                .getItems()
                .stream()
                .filter(seek -> seek.character.equals(kanji))
                .findAny();
        if (!entryOptional.isPresent()) {
            throw new CharacterNotFoundInDictionary();
        }
        final KanjiEntry entry = entryOptional.get();
        return entry;
    }

    private String transformFragment(String fragment) {
        String processed = new Furiganiser().katakanaToHiragana(fragment);
        return processed.replace("つ", "っ");
    }
}
