/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package romanlanguages.filters;

import org.mpasko.util.Filesystem;
import romanlanguages.commons.ItalianSteamer;
import romanlanguages.commons.SimpleDictionary;
import romanlanguages.commons.SimpleEntry;

/**
 *
 * @author marcin
 */
public class SimillarityFilter {
    private StringBuilder log = new StringBuilder();
    
    public SimillarityFilter() {
        
    }

    private void saveLog() {
        new Filesystem().saveFile("interproducts/filter_logs.txt", log.toString());
    }

    private void log(String line) {
        log.append(line).append("\n");
    }
    
    public SimpleDictionary filter(SimpleDictionary in) {
        SimpleDictionary result = new SimpleDictionary();
        for (SimpleEntry item : in.items()) {
            if (!isSimillar(item.word, item.translation)) {
                result.put(item);
            } else {
                String line = String.format("filtered: %s - %s", item.word, item.translation);
                log(line);
            }
        }
        saveLog();
        return result;
    }

    private boolean isSimillar(String word, String translation) {
        String italiano = new ItalianSteamer().doSteam(word);
        int size = Math.min(italiano.length(), translation.length());
        return size/2 > levenshteinDistance(italiano, translation);
    }
    
    /*
    public int levenshteinDistance(String lhs, String rhs) {
        return levenshteinDistance(lhs, rhs);
    }
    */

    public int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances                                                       
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0                                 
        for (int i = 0; i < len0; i++) {
            cost[i] = i;
        }

        // dynamically computing the array of distances                                  

        // transformation cost for each letter in s1                                    
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1                             
            newcost[0] = j;

            // transformation cost for each letter in s0                                
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings                             
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation                               
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost                                                    
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays                                                 
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings        
        return cost[len0 - 1];
    }
}
