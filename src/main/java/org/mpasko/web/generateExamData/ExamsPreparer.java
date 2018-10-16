/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web.generateExamData;

import org.mpasko.commons.DictEntry;
import org.mpasko.management.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.RomajiWritingChooser;
import org.mpasko.dictionary.formatters.WritingChooser;
import org.mpasko.util.Filesystem;
import org.mpasko.web.DataSourceCache;
import org.mpasko.web.ExamItem;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author marcin
 */
public class ExamsPreparer {

    private DataSourceCache data;
    public ExamsPreparer(DataSourceCache data) {
        this.data = data;
    }

    public ExamData getDataAbout(String id, String subid) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDict(DefaultConfig.globalSources + "/" + id + "/" + subid);
        return buildExamData(dict);
    }

    List<ExamItem> generateExam(String params, String activity, String phase) {
        return generateExamFromData(getDataAbout(params), activity, phase);
    }

    List<ExamItem> generateSubExam(String params, String activity, String phase) {
        return generateExamFromData(getDataAboutSubitem(params), activity, phase);
    }

    public ExamData getDataAbout(String id) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDictFromFolder(DefaultConfig.globalSources + "/" + id);
        return buildExamData(dict);
    }

    public ExamData getDataByPath(String path) {
        System.out.println(path);
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDict(path);
        System.out.println(dict.items().size());
        return buildExamData(dict);
    }

    public ExamData getDataAboutSubitem(String params) {
        System.out.println(params);
        String path = findFileWithName(DefaultConfig.globalSources, params);
        Dictionary dict = new DictionaryFileLoader().loadTripleDict(path);
        return buildExamData(dict);
    }

    private ExamData buildExamData(Dictionary dict) {
        ActivityData reading = new ActivityData(dict, data.readingBlacklist, data.readingWhitelist);
        ActivityData listening = new ActivityData(dict, data.listeningBlacklist, data.listeningWhitelist);
        return new ExamData(reading, listening);
    }

    private String findFileWithName(String root, String name) {
        String containing = new Filesystem().getSubdirectories(root)
                .stream()
                .filter(subdir -> new Filesystem().getSubfiles(root + "/" + subdir).contains(name))
                .findFirst()
                .get();
        return root + "/" + containing + "/" + name;
    }

    private List<ExamItem> generateExamFromData(ExamData data, String activity, String phase) {
        return switchSource(data, activity, phase)
                .stream()
                .map(dict -> getItemByActivity(dict, activity))
                .collect(Collectors.toList());
    }

    private ExamItem getItemByActivity(DictEntry dict, String activity) {
        if (activity.toLowerCase().equals("listening")) {
            return getListeningItem(dict);
        } else if (activity.toLowerCase().equals("reading")) {
            return getReadingItem(dict);
        } else {
            throwUnknownActivity(activity);
            return null;
        }
    }

    private ExamItem getReadingItem(DictEntry dict) {
        ExamItem item = new ExamItem();
        item.question = new KanjiChooser().choose(dict);
        item.corectAnswer = new RomajiWritingChooser().choose(dict);
        return item;
    }

    private ExamItem getListeningItem(DictEntry dict) {
        ExamItem item = new ExamItem();
        item.question = new WritingChooser().choose(dict);
        item.corectAnswer = new MeaningChooser().choose(dict);
        return item;
    }

    private List<DictEntry> switchSource(ExamData data, String activity, String phase) {
        return switchPhase(switchActivity(data, activity), phase);
    }

    private ActivityData switchActivity(ExamData data, String activity) {
        switch (activity.toLowerCase()) {
            case "listening":
                return data.getListening();
            case "reading":
                return data.getReading();
            default:
                throwUnknownActivity(activity);
                return null;
        }
    }

    private List<DictEntry> switchPhase(ActivityData activity, String phase) {
        switch (phase.toLowerCase()) {
            case "unprocessed":
                return activity.getUnprocessed();
            case "black":
                return activity.getBlack();
            case "white":
                return activity.getWhite();
            default:
                throwUnknownPhase(phase);
                return null;
        }
    }

    private Object throwUnknownActivity(String activity) {
        String message = String.format("Unknown activity:%s", activity);
        throw new RuntimeException(message);
    }

    private Object throwUnknownPhase(String phase) {
        String message = String.format("Unknown phase:%s", phase);
        throw new RuntimeException(message);
    }
}
