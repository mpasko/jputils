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
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
class ExamsCatalogue {

    private Dictionary listeningWhitelist;
    private Dictionary readingWhitelist;

    public ExamsCatalogue() {
        reloadDataSources();
    }

    private void reloadDataSources() {
        final DataSources dataSources = new DataSources();
        dataSources.init();
        listeningWhitelist = dataSources.listeningWhitelist();
        readingWhitelist = dataSources.readingWhitelist();
    }

    public List<String> getItems() {
        List<String> directories = Util.getSubdirectories(DefaultConfig.globalSources);
        return directories;
    }

    public List<String> getSubItems(String id) {
        List<String> files = Util.getSubfiles(DefaultConfig.globalSources + "/" + id);
        return files;
    }

    ExamData getDataAbout(String id) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDictFromFolder(DefaultConfig.globalSources + "/" + id);
        return new ExamData(dict, listeningWhitelist, readingWhitelist);
    }

    private ExamData getDataAboutSubitem(String params) {
        String path = findFileWithName(DefaultConfig.globalSources, params);
        Dictionary dict = new DictionaryFileLoader().loadTripleDict(path);
        return new ExamData(dict, listeningWhitelist, readingWhitelist);
    }

    ExamData getDataAbout(String id, String subid) {
        Dictionary dict = new DictionaryFileLoader()
                .loadTripleDict(DefaultConfig.globalSources + "/" + id + "/" + subid);
        return new ExamData(dict, listeningWhitelist, readingWhitelist);
    }

    List<ExamItem> generateReadingExam(String params) {
        return getDataAbout(params)
                .getReadingBlack()
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

    List<ExamItem> generateListeningExam(String params) {
        return getDataAbout(params)
                .getListeningBlack()
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

    List<ExamItem> generateReadingSubExam(String params) {
        return getDataAboutSubitem(params)
                .getReadingBlack()
                .stream()
                .map(dict -> getReadingItem(dict))
                .collect(Collectors.toList());
    }

    List<ExamItem> generateListeningSubExam(String params) {
        return getDataAboutSubitem(params)
                .getListeningBlack()
                .stream()
                .map(dict -> getListeningItem(dict))
                .collect(Collectors.toList());
    }

    public String saveWhiteListeningResults(String id, String content) {
        return saveGeneric(DefaultConfig.listeningWhitelist, id, content);
    }

    public String saveWhiteReadingResults(String id, String content) {
        return saveGeneric(DefaultConfig.readingWhitelist, id, content);
    }

    public String saveBlackListeningResults(String id, String content) {
        return saveGeneric(DefaultConfig.listeningBlacklist, id, content);
    }

    public String saveBlackReadingResults(String id, String content) {
        return saveGeneric(DefaultConfig.readingBlacklist, id, content);
    }

    private String saveGeneric(final String directory, String id, String content) {
        String filename = directory + id + formatTimestamp() + ".txt";
        Util.saveFile(filename, content);
        reloadDataSources();
        return "";
    }

    private String formatTimestamp() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HHmmss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        return reportDate;
    }

    private String findFileWithName(String root, String name) {
        String containing = Util.getSubdirectories(root)
                .stream()
                .filter(subdir -> Util.getSubfiles(root + "/" + subdir).contains(name))
                .findFirst()
                .get();
        return root + "/" + containing + "/" + name;
    }
}
