package org.mpasko.repository.filesystem;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.util.Filesystem;
import org.mpasko.util.IFilesystem;

public class DictionaryUniversalLoader {
    public Dictionary getDictionaryByPath(String path) {
        System.out.println(path);
        IFilesystem filesystem = new Filesystem();
        Dictionary dict;
        if (filesystem.isFile(path)) {
            dict = new DictionaryFileLoader()
                    .loadTripleDict(path);
        } else if (filesystem.isDirectory(path)) {
            dict = new DictionaryFileLoader()
                    .loadTripleDictFromFolder(path);
        } else {
            throw new RuntimeException("Invalid path: "+path);
        }
        System.out.println(dict.items().size());
        return dict;
    }
}
