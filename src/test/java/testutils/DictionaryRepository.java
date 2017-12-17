/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testutils;

import org.mpasko.dictionary.Dictionary;

/**
 *
 * @author marcin
 */
public class DictionaryRepository {

    public static Dictionary threeHomonymes() {
        Dictionary sourceDict = new Dictionary();
        sourceDict.put("付加語", "ふかご", "adjunct word");
        sourceDict.put("不可誤", "ふかご", "infallability");
        sourceDict.put("狂言", "きょうげん", "farse, kabuki, performance");
        sourceDict.put("虚言", "きょげん", "falsehood, lie");
        sourceDict.put("倒錯", "とうさく", "perversion, inversion");
        sourceDict.put("盗作", "とうさく", "plagiarism");
        sourceDict.put("印象", "いんしょう", "impression");
        sourceDict.put("印章", "いんしょう", "stamp seal");
        return sourceDict;
    }
}
