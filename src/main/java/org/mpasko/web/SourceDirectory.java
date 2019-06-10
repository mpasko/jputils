/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web;

import java.util.List;

import org.mpasko.configuration.DefaultPaths;
import org.mpasko.japanese.runners.workflow.IDataSource;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
class SourceDirectory {

    private IDataSource data;
    public SourceDirectory(IDataSource data) {
        this.data = data;
    }

    public List<String> getItems() {
        List<String> directories = new Filesystem().getSubdirectories(DefaultPaths.wordsGlobalSources);
        return directories;
    }

    public List<String> getSubItems(String id) {
        List<String> files = new Filesystem().getSubfiles(DefaultPaths.wordsGlobalSources + "/" + id);
        return files;
    }
}
