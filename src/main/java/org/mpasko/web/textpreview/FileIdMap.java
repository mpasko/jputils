package org.mpasko.web.textpreview;

import org.mpasko.fileTreeModel.Leaf;
import org.mpasko.fileTreeModel.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileIdMap {
    private Map<String, String> map = new HashMap<>();

    public static FileIdMap generateDefault(String base) {
        Node tree = new TreeGenerator().generate(base);
        List<Leaf> leafs = tree.getAllLeafs();
        FileIdMap map = new FileIdMap();
        leafs.stream().forEach(leaf -> map.put(leaf.id, leaf.fullPath));
        return map;
    }

    public void put(String key, String path) {
        if (classify(key)) {
            map.put(normalize(key), path);
        }
    }

    public String search(String key) {
        return map.get(normalize(key));
    }

    private String normalize(String key) {
        return key;
    }

    private boolean classify(String key) {
        return !key.contains(".eng");
    }
}
