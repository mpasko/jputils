/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web.legacyApi.generateExamData;

import org.mpasko.exams.*;
import org.mpasko.management.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.util.Filesystem;
import org.mpasko.repository.filesystem.DictionaryUniversalLoader;
import org.mpasko.web.DataSourceCache;
import org.mpasko.exams.ExamItem;

import java.util.List;

/**
 *
 * @author marcin
 */
public class ExamsPreparer {

    private DataSourceCache data;
    public ExamsPreparer(DataSourceCache data) {
        this.data = data;
    }

    public List<ExamItem> generateExamByPath(String path, String activity, String phase) {
        return new ExamBuilder().generateExam(getDataByPath(path), activity, phase);
    }

    public ExamData getDataByPath(String path) {
        Dictionary dict = new DictionaryUniversalLoader()
                .getDictionaryByPath(path);
        return new ExamDataBuilder(this.data)
                .buildExamData(dict);
    }

    @Deprecated
    public ExamData getDataAbout(String id, String subid) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDict(DefaultConfig.globalSources + "/" + id + "/" + subid);
        return new ExamDataBuilder(this.data).buildExamData(dict);
    }

    @Deprecated
    List<ExamItem> generateExam(String params, String activity, String phase) {
        return new ExamBuilder().generateExam(getDataAbout(params), activity, phase);
    }

    @Deprecated
    List<ExamItem> generateSubExam(String params, String activity, String phase) {
        return new ExamBuilder().generateExam(getDataAboutSubitem(params), activity, phase);
    }

    @Deprecated
    public ExamData getDataAbout(String id) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDictFromFolder(DefaultConfig.globalSources + "/" + id);
        return new ExamDataBuilder(this.data).buildExamData(dict);
    }

    @Deprecated
    public ExamData getDataAboutSubitem(String params) {
        System.out.println(params);
        String path = findFileWithName(DefaultConfig.globalSources, params);
        Dictionary dict = new DictionaryFileLoader().loadTripleDict(path);
        return new ExamDataBuilder(this.data).buildExamData(dict);
    }

    private String findFileWithName(String root, String name) {
        String containing = new Filesystem().getSubdirectories(root)
                .stream()
                .filter(subdir -> new Filesystem().getSubfiles(root + "/" + subdir).contains(name))
                .findFirst()
                .get();
        return root + "/" + containing + "/" + name;
    }
}
