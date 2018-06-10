/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.mpasko.commons.Furiganiser;
import org.mpasko.commons.KanjiDictionary;
import org.mpasko.commons.KanjiEntry;
import org.mpasko.loadres.KanjiDictLoader;
import org.mpasko.loadres.KanjiDictLoader.Filter;
import org.mpasko.util.Filesystem;
import org.mpasko.util.collectors.JoinList;

/**
 *
 * @author marcin
 */
public class FullKanjiDictionary {

    public static void main(String[] args) {
        generateCharacterOnMeaningDictionary();
        generateOnCharacterMeaningDictionary();
    }

    private static void generateCharacterOnMeaningDictionary() {
        final int from = 5;
        final int to = 8;
        String formattedDict = generateKanjiDictByGradesCHaracterOnMeaning(from, to);
        new Filesystem().saveFile("dictionaries\\kanjidict\\grades_" + from + "_" + to + ".txt", formattedDict);
    }

    private static void generateOnCharacterMeaningDictionary() {
        final int from = 3;
        final int to = 8;
        String formattedDict = generateKanjiDictByGradesOnCharacter(from, to);
        new Filesystem().saveFile("dictionaries\\kanjidict\\on_grades_" + from + "_" + to + ".txt", formattedDict);
    }

    private static String generateKanjiDictByGradesCHaracterOnMeaning(final int from, final int to) {
        KanjiDictionary dict = loadKanjiByGrade(from, to);
        String formattedDict;
        String whitelist = new Filesystem().loadFile("inputs/phonetic_compounds/phonetic_compounds.txt");
        formattedDict = dict.getItems()
                .stream()
                .filter(t -> isNotInWhitelist(whitelist, t.character))
                .map(t -> t.character + " - " + t.onyomi + " -" + t.meaning.split(",")[0])
                .sorted()
                .collect(Collectors.joining("\n"));
        return formattedDict;
    }

    private static String generateKanjiDictByGradesOnCharacter(final int from, final int to) {
        KanjiDictionary dict = loadKanjiByGrade(from, to);
        LinkedList<String> entryList;
        String whitelist = new Filesystem().loadFile("inputs/phonetic_compounds/phonetic_compounds.txt");
        entryList = dict.getItems()
                .stream()
                .filter(t -> isNotInWhitelist(whitelist, t.character))
                .map(t -> groupByOnyomi(t))
                .collect(new JoinList<String>());
        String formattedDict;
        formattedDict = entryList.stream()
                .sorted(new FirstPartReverse())
                .collect(Collectors.joining("\n"));
        return formattedDict;
    }

    private static KanjiDictionary loadKanjiByGrade(final int from, final int to) {
        KanjiDictLoader loader = new KanjiDictLoader();
        final Filter filter = new Filter();
        filter.gradeFrom = from;
        filter.gradeTo = to;
        KanjiDictionary dict = loader.load(filter);
        return dict;
    }

    private static List<String> groupByOnyomi(KanjiEntry entry) {
        List<String> formattedPart;
        Furiganiser furiganiser = new Furiganiser();
        formattedPart = entry.onyomi
                .stream()
                .map(on -> furiganiser.romanize(on))
                .map(on -> on + " - " + entry.character)
                .collect(Collectors.toList());
        return formattedPart;
    }

    private static boolean isNotInWhitelist(String whitelist, String character) {
        return !whitelist.contains(character);
    }

    private static class FirstPartReverse implements Comparator<String> {

        public FirstPartReverse() {
        }

        @Override
        public int compare(String o1, String o2) {
            final String firstToCompare = reverse(firstPartOf(o1));
            final String secondToCompare = reverse(firstPartOf(o2));
            return firstToCompare.compareTo(secondToCompare);
        }

        private static String firstPartOf(String line) {
            return line.split("-")[0].trim();
        }

        private static String reverse(String o1) {
            return new StringBuilder(o1).reverse().toString();
        }
    }
}
