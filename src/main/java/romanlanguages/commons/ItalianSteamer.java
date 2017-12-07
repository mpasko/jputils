/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package romanlanguages.commons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marcin
 */
public class ItalianSteamer {
    
    public List<String> articles(){
        return Arrays.asList("la", "il", "lo", "un", "una");
    }
    
    public List<String> shortenedArticles(){
        return Arrays.asList("l", "un");
    }
    
    public Map<String, String> phonetics() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("gio", "jo");
        map.put("sio", "tio");
        map.put("zio", "tio");
        map.put("nz", "nc");
        return map;
    }
    
    public String removeArticle(String word) {
        for (String article : articles()) {
            String prefix = article+" ";
            if (word.startsWith(prefix)){
                return word.replaceFirst(prefix, "");
            }
        }
        for (String article : shortenedArticles()) {
            String prefix = article+"'";
            if (word.startsWith(prefix)){
                return word.replaceFirst(prefix, "");
            }
        }
        return word;
    }
    
    public String doSteam(String word){
        String partial = this.removeArticle(word);
        Map<String, String> pho = phonetics();
        for (String key : pho.keySet()) {
            partial = partial.replace(key, pho.get(key));
        }
        return partial;
    }
}
