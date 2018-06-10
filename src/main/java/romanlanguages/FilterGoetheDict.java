/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package romanlanguages;

import org.mpasko.util.Filesystem;
import org.mpasko.util.FormatterUtil;
import romanlanguages.commons.SimpleDictionary;
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
        new Filesystem().saveFile("dictionaries/goethe_italiano_filtered.txt", dict.toString());
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
