package org.mpasko.web.preview.treeGeneratorTests;

import mocks.filesystemMocks.*;
import org.junit.Assert;
import org.junit.Test;
import org.mpasko.fileTreeModel.Node;
import org.mpasko.web.preview.TreeGenerator;

public class generateTests {

    @Test
    public void whenOnlySubfiles() {
        TreeGenerator testable = new TreeGenerator(new OnlyFilesFilesystemMock());
        Node node = testable.generate("base");
        Assert.assertTrue("expected no subfolders", node.subnodes.isEmpty());
        Assert.assertTrue("expected some files", !node.subleafs.isEmpty());
    }
    @Test
    public void whenOnlySubfolders() {
        TreeGenerator testable = new TreeGenerator(new OnlyFoldersFilesystemMock("base"));
        Node node = testable.generate("base");
        Assert.assertTrue("expected some subfolders", !node.subnodes.isEmpty());
        Assert.assertTrue("expected no files", node.subleafs.isEmpty());
    }
}
