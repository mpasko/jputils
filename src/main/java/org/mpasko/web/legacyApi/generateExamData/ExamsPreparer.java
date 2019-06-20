/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web.legacyApi.generateExamData;

import org.mpasko.exams.*;
import org.mpasko.repository.dataSource.IDataSource;
import org.mpasko.configuration.DefaultPaths;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.util.Filesystem;
import org.mpasko.repository.filesystem.DictionaryUniversalLoader;
import org.mpasko.exams.ExamItem;

import java.util.List;

/**
 *
 * @author marcin
 */
public class ExamsPreparer {

    private IDataSource data;
    public ExamsPreparer(IDataSource data) {
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

    /**
     * Todo: use getDataByPath instead
     */
    @Deprecated
    public ExamData getDataAbout(String id, String subid) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDict(DefaultPaths.wordsGlobalSources + "/" + id + "/" + subid);
        return new ExamDataBuilder(this.data).buildExamData(dict);
    }


    /**
     * Todo: -forgot to add label. Speculation: probably we need something that returns @return ExamData
     */
    @Deprecated
    List<ExamItem> generateExam(String params, String activity, String phase) {
        return new ExamBuilder().generateExam(getDataAbout(params), activity, phase);
    }

    /**
     * Todo: -forgot to add label. Speculation: probably we need something that returns @return ExamData
     */
    @Deprecated
    List<ExamItem> generateSubExam(String params, String activity, String phase) {
        return new ExamBuilder().generateExam(getDataAboutSubitem(params), activity, phase);
    }

    @Deprecated
    public ExamData getDataAbout(String id) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDictFromFolder(DefaultPaths.wordsGlobalSources + "/" + id);
        return new ExamDataBuilder(this.data).buildExamData(dict);
    }

    @Deprecated
    public ExamData getDataAboutSubitem(String params) {
        System.out.println(params);
        String path = findFileWithName(DefaultPaths.wordsGlobalSources, params);
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
