/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.mpasko.commons.DictEntry;
import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.RomajiWritingChooser;
import org.mpasko.dictionary.formatters.WritingChooser;
import org.mpasko.japanese.runners.workflow.DataSources;
import org.mpasko.japanese.runners.workflow.GenerateOnyomiWhitelist;
import org.mpasko.japanese.runners.workflow.Remover;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
class ExamsCatalogue {

    private DataSourceCache data;
    public ExamsCatalogue(DataSourceCache data) {
        this.data = data;
    }

    public List<String> getItems() {
        List<String> directories = new Filesystem().getSubdirectories(DefaultConfig.globalSources);
        return directories;
    }

    public List<String> getSubItems(String id) {
        List<String> files = new Filesystem().getSubfiles(DefaultConfig.globalSources + "/" + id);
        return files;
    }

    ExamData getDataAbout(String id) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDictFromFolder(DefaultConfig.globalSources + "/" + id);
        return buildExamData(dict);
    }

    private ExamData getDataAboutSubitem(String params) {
        String path = findFileWithName(DefaultConfig.globalSources, params);
        Dictionary dict = new DictionaryFileLoader().loadTripleDict(path);
        return buildExamData(dict);
    }

    ExamData getDataAbout(String id, String subid) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDict(DefaultConfig.globalSources + "/" + id + "/" + subid);
        return buildExamData(dict);
    }

    private ExamData buildExamData(Dictionary dict) {
        ActivityData reading = new ActivityData(dict, data.readingBlacklist, data.readingWhitelist);
        ActivityData listening = new ActivityData(dict, data.listeningBlacklist, data.listeningWhitelist);
        return new ExamData(reading, listening);
    }

    List<ExamItem> generateReadingExam(String params, String phase) {
        return switchSource(getDataAbout(params), "reading", phase)
                .stream()
                .map(dict -> getReadingItem(dict))
                .collect(Collectors.toList());
    }

    private ExamItem getReadingItem(DictEntry dict) {
        ExamItem item = new ExamItem();
        item.question = new KanjiChooser().choose(dict);
        item.corectAnswer = new RomajiWritingChooser().choose(dict);
        return item;
    }

    List<ExamItem> generateListeningExam(String params, String phase) {
        return switchSource(getDataAbout(params), "listening", phase)
                .stream()
                .map(dict -> getListeningItem(dict))
                .collect(Collectors.toList());
    }

    private ExamItem getListeningItem(DictEntry dict) {
        ExamItem item = new ExamItem();
        item.question = new WritingChooser().choose(dict);
        item.corectAnswer = new MeaningChooser().choose(dict);
        return item;
    }

    List<ExamItem> generateReadingSubExam(String params, String phase) {
        return switchSource(getDataAboutSubitem(params), "reading", phase)
                .stream()
                .map(dict -> getReadingItem(dict))
                .collect(Collectors.toList());
    }

    List<ExamItem> generateListeningSubExam(String params, String phase) {
        return switchSource(getDataAboutSubitem(params), "listening", phase)
                .stream()
                .map(dict -> getListeningItem(dict))
                .collect(Collectors.toList());
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
                return null;
        }
    }

    /* *x/
    @Deprecated
    public String saveResults(String source, String type, String id, String content) {
        final Remover remover = new Remover(data.dataSources.getGlobalDict());
        try {
            remover.removeRedundancy(oppositeOf(source), type, content);
        }catch(RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        return saveGeneric(Remover.switchSourcePath(source, type), id, content);
    }

    private String oppositeOf(String source) {
        if (source.equalsIgnoreCase("white")) {
            return "black";
        } else {
            return "white";
        }
    }

    private String saveGeneric(String directory, String id, String content) {
        String filename = directory + id + formatTimestamp() + ".txt";
        new Filesystem().saveFile(filename, content);
        data.reloadDataSources();
        return "";
    }
    private String formatTimestamp() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HHmmss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        return reportDate;
    }
    /* */

    private String findFileWithName(String root, String name) {
        String containing = new Filesystem().getSubdirectories(root)
                .stream()
                .filter(subdir -> new Filesystem().getSubfiles(root + "/" + subdir).contains(name))
                .findFirst()
                .get();
        return root + "/" + containing + "/" + name;
    }
}
