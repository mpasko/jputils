/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web;

import java.util.List;

import org.mpasko.management.console.DefaultConfig;
import org.mpasko.util.Filesystem;

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
        List<String> directories = new Filesystem().getSubdirectories(DefaultConfig.wordsGlobalSources);
        return directories;
    }

    public List<String> getSubItems(String id) {
        List<String> files = new Filesystem().getSubfiles(DefaultConfig.wordsGlobalSources + "/" + id);
        return files;
    }
}
