package org.mpasko.fileTreeModel;

import java.util.List;
import java.util.stream.Collectors;

public class Leaf {
    public String id;
    public String name;

    public static List<Leaf> WrapLeafs(List<String> listNames) {
        return listNames.stream()
                .map(name -> WrapLeaf(name))
                .collect(Collectors.toList());
    }

    public static Leaf WrapLeaf(String name) {
        Leaf leaf = new Leaf();
        leaf.id = name;
        leaf.name = name;
        return leaf;
    }
}
