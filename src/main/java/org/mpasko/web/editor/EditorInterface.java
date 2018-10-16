package org.mpasko.web.editor;

import org.mpasko.editor.Asset;
import org.mpasko.util.StringUtils;
import org.mpasko.web.textpreview.FileIdMap;

public class EditorInterface {
    public Asset open(String id, FileIdMap idCache) {
        String filename = idCache.search(id);
        return Asset.load(filename);
    }

    public void save(String path, String name, Asset asset, FileIdMap idCache) {
        String full_path = StringUtils.joinPath(path, name);
        idCache.put(name+".txt", full_path+".txt");
        Asset.save(path, name, asset);
    }
}
