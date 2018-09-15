package org.mpasko.fileTreeModel;

import org.mpasko.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Leaf {
    public String id;
    public String name;
    public String fullPath;

    public static List<Leaf> WrapLeafs(String base, List<String> listNames) {
        return listNames.stream()
                .map(name -> WrapLeaf(base, name))
                .collect(Collectors.toList());
    }

    public static Leaf WrapLeaf(String base, String name) {
        Leaf leaf = new Leaf();
        leaf.id = name;
        leaf.name = name;
        leaf.fullPath = StringUtils.joinPath(base, name);
        return leaf;
    }
}
