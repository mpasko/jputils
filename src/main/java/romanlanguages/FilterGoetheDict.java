/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package romanlanguages;

import java.util.LinkedList;
import java.util.List;
import org.mpasko.commons.DictSplitter;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.quizgeneration.QuizFormatter;
import org.mpasko.util.FormatterUtil;
import org.mpasko.util.SimpleUtils;
import org.mpasko.util.Util;
import romanlanguages.commons.SimpleDictionary;
import romanlanguages.commons.SimpleEntry;
import romanlanguages.filters.SimillarityFilter;

/**
 *
 * @author marcin
 */
public class FilterGoetheDict {
    public static void main(String args[]) {
        SimpleDictionary dict = new SimpleDictionary();
        for (int i = 1; i<=42; ++i) { //42
            String alignedNumber = FormatterUtil.alignString(2, "0", String.valueOf(i));
            String filename = String.format("dictionaries/goethe%s_italiano.txt", alignedNumber);
            processDict(filename, dict);
        }
        dict = new SimillarityFilter().filter(dict);
        Util.saveFile("dictionaries/goethe_italiano_filtered.txt", dict.toString());
//        QuizFormatter quiz = new QuizFormatter();
//        for (SimpleEntry item : dict.items()) {
//            List<String> answers = new LinkedList<String>();
//            quiz.appendQuestion(item.word, item.word, answers);
//        }
    }

    private static void processDict(String filename, SimpleDictionary dict) {
        System.out.println(filename);
        SimpleDictionary partdict = SimpleDictionary.loadDict(filename);
        //String keyword = filename.split("/")[1].split("\\.")[0];
        dict.putAll(partdict);
    }
}
