/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web;

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
import org.mpasko.util.Filesystem;
import org.mpasko.web.generateExamData.ActivityData;
import org.mpasko.web.generateExamData.ExamData;

/**
 *
 * @author marcin
 */
class SourceDirectory {

    private DataSourceCache data;
    public SourceDirectory(DataSourceCache data) {
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
}
