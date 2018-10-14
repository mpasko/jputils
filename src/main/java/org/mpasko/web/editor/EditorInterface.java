package org.mpasko.web.editor;

import org.mpasko.editor.Asset;
import org.mpasko.web.textpreview.FileIdMap;

public class EditorInterface {
    public Asset open(String id, FileIdMap idCache) {
        String filename = idCache.search(id);
        return Asset.load(filename);
    }
}
