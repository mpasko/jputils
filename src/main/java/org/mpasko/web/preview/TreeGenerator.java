package org.mpasko.web.preview;

import org.mpasko.fileTreeModel.Leaf;
import org.mpasko.fileTreeModel.Node;
import org.mpasko.util.Filesystem;
import org.mpasko.util.IFilesystem;
import org.mpasko.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TreeGenerator {

    private IFilesystem filesystem;

    public TreeGenerator() {
        this(new Filesystem());
    }

    public TreeGenerator(IFilesystem filesystem) {
        this.filesystem = filesystem;
    }

    public Node generate(String start) {
        return generateNodeStartingAt(start, "");
    }

    public Node generateNodeStartingAt(String base, String subpath) {
        Node node = new Node();
        String path = StringUtils.joinPath(base, subpath);
        node.base = base;
        node.name = subpath;
        List<String> subfiles = filesystem.getSubfiles(path);
        node.subleafs = Leaf.WrapLeafs(path, subfiles);
        List<String> subfolders = filesystem.getSubdirectories(path);
        node.subnodes = subfolders.stream()
                .map(name -> generateNodeStartingAt(path, name))
                .collect(Collectors.toList());
        return node;
    }

}
