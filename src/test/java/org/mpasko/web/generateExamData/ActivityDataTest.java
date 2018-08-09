package org.mpasko.web.generateExamData;

import org.junit.Before;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ActivityDataTest {

    @Before
    public void setUp() throws Exception {

    }

    /* *x/
    @Test
    public void getBlack() {
    }

    @Test
    public void getUnprocessed() {
    }

    @Test
    public void getWhite() {
    }
    /* */

    @Test
    public void bugReproduction () {
        Dictionary blacklist = new Dictionary();
        blacklist.put("左様","さよう","like that,that's right,indeed,let me see...");
        Dictionary whitelist = new Dictionary();
        whitelist.put("作用","さよう","action,operation,effect,function");

        //Dictionary examDict = new Sum(blacklist, whitelist).result();
        Dictionary examDict = new Dictionary();
        examDict.put("左様","さよう","like that,that's right,indeed,let me see...");
        examDict.put("作用","さよう","action,operation,effect,function");
        ActivityData testable = new ActivityData(examDict, blacklist, whitelist);
        List<DictEntry> unprocessed = testable.getUnprocessed();
        assertEquals("Expected unprocessed array to be empty",0, unprocessed.size());
    }
}