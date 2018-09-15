package org.mpasko.web.textpreview.treeGeneratorTests;

import mocks.filesystemMocks.*;
import org.junit.Assert;
import org.junit.Test;
import org.mpasko.fileTreeModel.Leaf;
import org.mpasko.fileTreeModel.Node;
import org.mpasko.web.textpreview.TreeGenerator;
import testutils.ListUtils;

import java.util.List;

public class generateTests {

    @Test
    public void whenOnlySubfiles() {
        TreeGenerator testable = new TreeGenerator(new OnlyFilesFilesystemMock());
        Node node = testable.generate("base");
        shouldHave2FilesAndNoFolders(node);
        shouldPreservePath(node);
    }

    private void shouldHave2FilesAndNoFolders(Node node) {
        Assert.assertTrue("expected no subfolders", node.subnodes.isEmpty());
        Assert.assertTrue("expected 2 files", node.subleafs.size() == 2);
    }

    private void shouldPreservePath(Node node) {
        List<Leaf> leafs = node.getAllLeafs();
        Assert.assertTrue("expected file1",
                ListUtils.existsItemMatchingCondition(leafs, item -> item.fullPath.equals("base/file1")));
        Assert.assertTrue("expected file2",
                ListUtils.existsItemMatchingCondition(leafs, item -> item.fullPath.equals("base/file2")));
    }

    @Test
    public void whenOnlySubfolders() {
        TreeGenerator testable = new TreeGenerator(new OnlyFoldersFilesystemMock("base"));
        Node node = testable.generate("base");
        Assert.assertTrue("expected some subfolders", !node.subnodes.isEmpty());
        Assert.assertTrue("expected no files", node.subleafs.isEmpty());
    }

    @Test
    public void whenNestedFileSHouldProduceFullPath() {
        TreeGenerator testable = new TreeGenerator(generateDefaultNestedFilesystem());
        Node node = testable.generate("base");
        shouldProduceFullPath(node);
    }

    private NestedFilesystem generateDefaultNestedFilesystem() {
        NestedFilesystem filesystem = new NestedFilesystem();
        filesystem.nest("base", new OnlyFoldersFilesystemMock("base"));
        filesystem.nest("base/dir1", new OnlyFilesFilesystemMock());
        filesystem.nest("base/dir2", new EmptyDirectory());
        return filesystem;
    }

    private void shouldProduceFullPath(Node node) {
        List<Leaf> leafs = node.getAllLeafs();
        //System.out.println(leafs.stream().map(leaf -> leaf.fullPath).collect(Collectors.joining("\n")));
        Assert.assertTrue("expected 2 files", leafs.size() == 2);
        Assert.assertTrue("expected file1",
                ListUtils.existsItemMatchingCondition(leafs, item -> item.fullPath.equals("base/dir1/file1")));
        Assert.assertTrue("expected file2",
                ListUtils.existsItemMatchingCondition(leafs, item -> item.fullPath.equals("base/dir1/file2")));
    }
}
