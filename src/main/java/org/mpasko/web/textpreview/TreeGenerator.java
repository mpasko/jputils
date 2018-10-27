package org.mpasko.web.textpreview;

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
        prepareSubfiles(node, path);
        prepareSubdirectories(node, path);
        return node;
    }

    private void prepareSubdirectories(Node node, String path) {
        List<String> subfolders = filesystem.getSubdirectories(path);
        node.subnodes = subfolders.stream()
                .map(name -> generateNodeStartingAt(path, name))
                .collect(Collectors.toList());
    }

    private void prepareSubfiles(Node node, String path) {
        List<String> subfiles = filesystem.getSubfiles(path)
                .stream()
                .filter(TreeGenerator::filter)
                .collect(Collectors.toList());
        node.subleafs = Leaf.WrapLeafs(path, subfiles);
    }

    private static boolean filter(String path) {
        return !path.contains(".eng.");
    }
}
