/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.kanjifilters;

import java.util.HashSet;
import java.util.Set;
import org.mpasko.commons.Classifier;
import org.mpasko.commons.KanjiEntry;
import org.mpasko.loadres.KanjiDictLoader;

/**
 *
 * @author marcin
 */
public class LoaderFilterByText {
    private Set<String> kanjiSet = new HashSet<String>();
    
    public LoaderFilterByText() {
        
    }
    
    public static LoaderFilterByText scanned(String text) {
        LoaderFilterByText loader = new LoaderFilterByText();
        loader.scan(text);
        return loader;
    }
    
    public final void scan(String text) {
        for (char c : text.toCharArray()) {
            if (Classifier.classify(c).containsKanji()) {
                kanjiSet.add(Character.toString(c));
            }
        }
    }
    
    public KanjiDictLoader.Filter getInstance() {
        return new Instance(kanjiSet);
    }
    
    public static class Instance extends KanjiDictLoader.Filter {
        
        Set<String> kanjiSet;
                
        public Instance(Set<String> kanjiSet) {
            this.kanjiSet = kanjiSet;
        }
        
        @Override
        public boolean matches(KanjiEntry kanji, int grade, int strokes, int freq) {
            return kanjiSet.contains(kanji.character)&&super.matches(kanji, grade, strokes, freq);
        }
        
    }
}
