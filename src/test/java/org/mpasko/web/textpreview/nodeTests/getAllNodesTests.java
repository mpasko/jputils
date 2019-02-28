package org.mpasko.web.textpreview.nodeTests;

import mocks.filesystemMocks.EmptyDirectory;
import mocks.filesystemMocks.NestedFilesystem;
import mocks.filesystemMocks.OnlyFilesFilesystemMock;
import mocks.filesystemMocks.OnlyFoldersFilesystemMock;
import org.junit.Assert;
import org.junit.Test;
import org.mpasko.fileTreeModel.Node;
import org.mpasko.fileTreeModel.TreeGenerator;

import java.util.List;

public class getAllNodesTests {


    private NestedFilesystem generateDefaultNestedFilesystem() {
        NestedFilesystem filesystem = new NestedFilesystem();
        filesystem.nest("base", new OnlyFoldersFilesystemMock("base"));
        filesystem.nest("base/dir1", new OnlyFilesFilesystemMock());
        filesystem.nest("base/dir2", new EmptyDirectory());
        return filesystem;
    }

    @Test
    public void itShouldListAllNodes() {
        Node testableNode = new TreeGenerator(generateDefaultNestedFilesystem())
                .generate("base");
        List<Node> nodes = testableNode.getAllNodes();
        Assert.assertEquals("Expecting all 2 subdirectories",2, nodes.size());
    }
}
