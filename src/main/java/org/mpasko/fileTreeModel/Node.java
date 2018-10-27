package org.mpasko.fileTreeModel;

import java.util.LinkedList;
import java.util.List;

public class Node {
    public String base;
    public String name;
    public List<Node> subnodes;
    public List<Leaf> subleafs;

    public List<Leaf> getAllLeafs() {
        LinkedList<Leaf> result = new LinkedList<>();
        result.addAll(subleafs);
        subnodes.forEach(subnode -> result.addAll(subnode.getAllLeafs()));
        return result;
    }

    public List<Node> getAllNodes() {
        LinkedList<Node> result = new LinkedList<>();
        result.addAll(subnodes);
        subnodes.forEach(subnode -> result.addAll(subnode.getAllNodes()));
        return result;
    }
}
