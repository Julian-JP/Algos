package com.example.algorithm.searchTrees;

import com.example.algorithm.SearchTrees.BinarySearchTree.BSTNode;
import com.example.algorithm.SearchTrees.BinarySearchTree.BinarySearchTree;
import com.example.algorithm.SearchTrees.BinarySearchTree.BinarySearchTreeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest {
    BinarySearchTreeService binarySearchTreeService = new BinarySearchTreeService();

    @Test
    void create1DeepTree() {
        BinarySearchTree tree = new BinarySearchTree(new BSTNode(0));

        assertNull(tree.getRoot().getLeft());
        assertEquals(0, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());

    }

    @Test
    void addLeftNode() {
        BinarySearchTree tree = new BinarySearchTree(new BSTNode(1));
        tree.add(0);

        assertEquals(0, tree.getRoot().getLeft().getValue());
        assertEquals(1, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());
    }

    @Test
    void addRightNode() {
        BinarySearchTree tree = new BinarySearchTree(new BSTNode(0));
        tree.add(1);

        assertNull(tree.getRoot().getLeft());
        assertEquals(0, tree.getRoot().getValue());
        assertEquals(1, tree.getRoot().getRight().getValue());
    }

    @Test
    void removeChildNode() {
        BinarySearchTree tree = new BinarySearchTree(new BSTNode(1));
        tree.add(0);
        tree.remove(0);

        assertNull(tree.getRoot().getLeft());
        assertEquals(1, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());


        tree.add(2);
        tree.remove(2);

        assertNull(tree.getRoot().getLeft());
        assertEquals(1, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());
    }

    @Test
    void removeRootNode() {
        BinarySearchTree tree = new BinarySearchTree(new BSTNode(1));
        tree.add(0);
        tree.add(2);

        tree.remove(1);


         assertEquals(0, tree.getRoot().getLeft().getValue());
        assertEquals(2, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());

        assertNull(tree.getRoot().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getRight());
    }

    @Test
    void addAndRemove1000Nodes() {
        BinarySearchTree tree = new BinarySearchTree(new BSTNode(0));
        SearchTreeTestUtils.addAndRemove1000Nodes(tree, searchTreeNode -> true);
    }

    @Test
    void addInEmptyTree() {
        BinarySearchTree tree = new BinarySearchTree(new BSTNode(0));
        tree.remove(0);
        assertNull(tree.getRoot());
        tree.add(0);
        assertEquals(0, tree.getRoot().getValue());
    }
}
