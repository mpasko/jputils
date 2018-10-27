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
        FileIdMap map = new FileIdMap();
        putAllNodes(map, tree);
        putAllLeafs(tree, map);
        return map;
    }

    private static void putAllLeafs(Node tree, FileIdMap map) {
        List<Leaf> leafs = tree.getAllLeafs();
        leafs.stream().forEach(leaf -> map.put(leaf.name, leaf.fullPath));
    }

    private static void putAllNodes(FileIdMap map, Node tree) {
        List<Node> nodes = tree.getAllNodes();
        nodes.stream().forEach(node -> map.put(node.name, node.base+"/"+node.name));
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
