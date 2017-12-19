/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.parsers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.wordcomparison.AndComparer;
import org.mpasko.japanese.wordcomparison.GrammaticalComparer;
import org.mpasko.japanese.wordcomparison.SynonimeComparer;
import org.mpasko.japanese.wordfilters.DuplicateFilter;
import org.mpasko.japanese.wordfilters.KnownWordsFilter;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.loadres.JmDictLoader.DefaultFilter;
import org.mpasko.util.LangUtils;
import org.mpasko.util.SimpleUtils;
import org.mpasko.util.StringUtils;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class ParseJishoOutputs {

    public static void main(String args[]) {
        System.getProperties().setProperty("jdk.xml.entityExpansionLimit", "0");
        processAllSongs();
    }

    public static void processAllSongs() {
        String baseDir = "inputs/songs/";
        Dictionary dict = loadDIctionary();
        processSongsFrom(baseDir + "song_list.txt", baseDir, "all_songs", dict);
        for (String str : Arrays.asList(Util.loadFile(baseDir + "folderlist.txt").split("\n"))) {
            processSongsFrom(baseDir + str + "/song_list.txt", baseDir + str + "/", str + "_songs", dict);
        }
        //processSongsFrom(baseDir + "yousei_teikoku/song_list.txt", baseDir + "yousei_teikoku/", "yousei_teikoku_songs", dict);
    }

    public static Dictionary loadDIctionary() {
        final DefaultFilter filter = new DefaultFilter();
        Dictionary dict = new JmDictLoader().load(filter);
        return dict;
    }

    public static void processSongsFrom(String listfile, String inputPath, String outputName, Dictionary dict) {
        List<String> songs = Arrays.asList(Util.loadFile(listfile).split("\n"));
        Dictionary merged = new Dictionary();
        StringBuilder plaintext = new StringBuilder();
        plaintext.append(songs.toString()).append("\n");
        for (String songName : songs) {
            String filename = String.format(inputPath + "%s", songName);
            processSingleSong(filename, songName, dict, merged, plaintext);
        }
        Util.saveFile("texts/" + outputName + ".txt", plaintext.toString());
        merged.write("dictionaries/" + outputName + ".txt");
    }

    private static void processSingleSong(String filename, String songName, Dictionary dict, Dictionary merged, StringBuilder plaintext) {
        try {
            final String rawText = loadRawText(filename);
            final String english = Util.loadFile(filename.replace(".htm", ".txt"));
            //TreeMap<String, String> items = loadSpecifiedOutputs(filename);
            List<String> items = loadWordsInTheirNaturalForm(filename);
            Dictionary found = findAndFilterItemsFromDictionary(items, rawText, dict);
            merged.addAll(found);
            found.write("dictionaries/" + songName + ".txt");
            //GenerateExams.processTripleDict(dict_filename);
            plaintext.append(formatSong(songName, rawText, found, english));
        } catch (Exception e) {
            throw new RuntimeException(String.format("Trouble processing song:%s", filename), e);
        }
    }

    private static Dictionary findAndFilterItemsFromDictionary(List<String> items, final String rawText, Dictionary dict) {
        //ArrayList<Entry<String, String>> itemsOrdered = new ArrayList(items.entrySet());
        //itemsOrdered.sort(new TextPositionComparator(rawText));
        Dictionary found = findAllItems(items, dict);
        DuplicateFilter duplicateFilter = DuplicateFilter.outputDictionaryDuplicateFilter();
        found = duplicateFilter.filter(found);
        found = KnownWordsFilter.build().filter(found);
        return found;
    }


    public static String formatSong(String song, final String rawText, Dictionary found, String english) {
        StringBuilder plaintext = new StringBuilder();
        plaintext.append(song.replace(".htm", "")).append("\n");
        plaintext.append(rawText).append("\n");
        plaintext.append(english).append("\n");
        plaintext.append(found.toString()).append("\n\n");
        return plaintext.toString();
    }

    public static Dictionary findAllItems(List<String> itemsOrdered, Dictionary dict) {
        Dictionary found = new Dictionary();
        for (String pair : itemsOrdered) {
            DictEntry item = dict.find(pair, pair);
            if ((item != null)) {
                found.put(item.kanji, item.writing, SimpleUtils.limitComas(item.english));
            } else {
                System.out.println(pair + " -not found in dict!");
            }
        }
        return found;
    }

    public static List<String> loadWordsInTheirNaturalForm(String filename) {
        String entityString = Util.loadFile(filename);
        Document doc = Jsoup.parse(entityString);
        Elements words_html = doc.getElementById("zen_bar").getElementsByTag("li");
        LinkedList<String> items = new LinkedList<>();
        for (Element word_htm : words_html) {
            String all = StringUtils.clear(locateFullElement(word_htm));
            if (!isUnlinkedOrParticle(word_htm)) {
                items.add(all);
            } else {
                System.out.println(all + " -unlinked or particle");
            }
        }
        return items;
    }

    public static TreeMap<String, String> loadSpecifiedOutputs(String filename) {
        String entityString = Util.loadFile(filename);
        Document doc = Jsoup.parse(entityString);
        Elements words_html = doc.getElementById("zen_bar").getElementsByTag("li");
        TreeMap<String, String> items = new TreeMap<String, String>();
        for (Element word_htm : words_html) {
            String furigana = locateFuriganaElement(word_htm);
            String all = StringUtils.clear(locateFullElement(word_htm));
            String kana = all.replace(furigana, "");
            if (!isUnlinkedOrParticle(word_htm)) {
                //System.out.println(kana+"\n");
                items.put(kana, mergeFuriganaAndKana(kana, furigana));
            } else {
                System.out.println(kana + " -unlinked or particle");
            }
        }
        return items;
    }

    private static boolean isUnlinkedOrParticle(Element word_htm) {
        boolean unlinked = word_htm.getElementsByClass("unlinked").size() > 0;
        boolean particle = (word_htm.getElementsByAttributeValue("title", "Particle").size() + word_htm.getElementsByAttributeValue("data-pos", "Particle").size()) > 0;
        return (unlinked || particle);
    }

    private static String locateFullElement(Element word_htm) {
        String text = "";
        //Elements modern_elements = word_htm.getElementsByClass("japanese_word__furigana_wrapper");
        Elements modern_elements = word_htm.getElementsByClass("japanese_word__text_wrapper");
        Elements legacy_elements = word_htm.getElementsByTag("a");
        return tryLocateOneOfElements(modern_elements, legacy_elements);
    }

    private static String locateFuriganaElement(Element word_htm) {
        String furigana = "";
        Elements modern_elements = word_htm.getElementsByClass("japanese_word__furigana");
        Elements legacy_elements = word_htm.getElementsByClass("furigana");
        return tryLocateOneOfElements(modern_elements, legacy_elements);
    }

    private static String tryLocateOneOfElements(Elements... elements) {
        Stream<Elements> stream = Arrays.stream(elements);
        final Optional<Elements> optional = stream.filter(elems -> !elems.isEmpty()).findFirst();
        if (optional.isPresent()) {
            final Elements nonemptyElements = optional.get();
            return nonemptyElements.get(0).text();
        } else {
            return "";
        }
    }

    public static String mergeFuriganaAndKana(String kana, String furigana) {
        char[] ckana = kana.toCharArray();
        StringBuilder out = new StringBuilder();
        int k = stripPrefix(ckana, out);
        out.append(furigana);
        out.append(LangUtils.stripSuffix(ckana, k));
        return out.toString();
    }

    private static int stripPrefix(char[] ckana, StringBuilder out) {
        int k;
        for (k = 0; k < ckana.length; ++k) {
            if (Classifier.classify(ckana[k]).containsKanji()) {
                break;
            } else {
                out.append(ckana[k]);
            }
        }
        return k;
    }

    private static String loadRawText(String filename) {
        String entityString = Util.loadFile(filename);
        Document doc = Jsoup.parse(entityString);
        return doc.getElementById("keyword").attr("value");
    }

    private static class TextPositionComparator implements Comparator<Entry<String, String>> {

        private final String text;

        public TextPositionComparator(String rawText) {
            this.text = rawText;
        }

        private int getPosition(Entry<String, String> entry) {
            int keyIndex = text.indexOf(entry.getKey());
            if (keyIndex < 0) {
                keyIndex = text.indexOf(entry.getValue());
            }
            return keyIndex;
        }

        @Override
        public int compare(Entry<String, String> o1, Entry<String, String> o2) {
            return text.indexOf(o1.getKey()) - text.indexOf(o2.getKey());
        }
    }
}
