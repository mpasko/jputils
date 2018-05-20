/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import org.mpasko.commons.DictEntry;
import org.mpasko.commons.KanjiDictionary;
import org.mpasko.commons.KanjiEntry;
import org.mpasko.loadres.KanjiDictLoader;
import org.mpasko.loadres.KanjiDictLoader.Filter;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class GradeFilter extends GenericFilter{
    public String darklist_filename = "inputs/dark_list.txt";
    String acceptedKanji = "";
    int from = 4;
    int to = 8;
    boolean allMatch = true;
    
    public static GradeFilter build(int from, int to, boolean allMatch) {
        GradeFilter gradeFilter = new GradeFilter();
        gradeFilter.from = from;
        gradeFilter.to = to;
        gradeFilter.allMatch = allMatch;
        gradeFilter.initializeKanjiList();
        return gradeFilter;
    }
    
    public static GradeFilter build() {
        return build(4, 8, true);
    }
    
    public static GradeFilter buildBlacklist() {
        GradeFilter gradeFilter = new GradeFilter();
        gradeFilter.initializeFromBlackList();
        return gradeFilter;
    }
    
    public static GradeFilter buildBlacklist(String filename) {
        GradeFilter gradeFilter = buildBlacklist();
        gradeFilter.darklist_filename = filename;
        return gradeFilter;
    }
    
    public void initializeKanjiList() {
        KanjiDictLoader loader = new KanjiDictLoader();
        Filter filter = new Filter();
        filter.gradeFrom = from;
        filter.gradeTo = to;
        KanjiDictionary dict = loader.load(filter);
        StringBuilder builder = new StringBuilder();
        for (KanjiEntry item : dict.getItems()) {
            builder.append(item.character);
        }
        acceptedKanji = builder.toString();
        System.out.println(String.format("Kanji dict initialized:%n%s%n", acceptedKanji));
    }
    
    public void initializeFromBlackList() {
        acceptedKanji = Filesystem.loadFile(darklist_filename).replaceAll("\n", "");
    }

    @Override
    public boolean itemMatches(DictEntry item) {
        boolean all = true;
        boolean atLeastOne = false;
        for (char k : item.kanji.toCharArray()) {
            if (acceptedKanji.indexOf(k)>=0) {
                atLeastOne = true;
            } else {
                all = false;
            }
        }
        return allMatch?all:atLeastOne;
    }
}
