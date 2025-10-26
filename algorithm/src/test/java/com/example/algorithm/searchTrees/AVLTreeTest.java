package com.example.algorithm.searchTrees;

import com.example.algorithm.SearchTrees.AVLTree.AVLTree;
import com.example.algorithm.SearchTrees.AVLTree.AVLTreeNode;
import com.example.algorithm.SearchTrees.AVLTree.AVLTreeService;
import com.example.algorithm.SearchTrees.SearchTreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {
    AVLTreeService avlTreeService = new AVLTreeService();

    @Test
    void leftLeftRotateInsert() {
        AVLTree tree = new AVLTree(new AVLTreeNode(6));

        tree.add(4);
        tree.add(7);
        tree.add(2);
        tree.add(5);
        tree.add(1);

        assertEquals(4, tree.getRoot().getValue());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(6, tree.getRoot().getRight().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals(5, tree.getRoot().getRight().getLeft().getValue());
        assertEquals(7, tree.getRoot().getRight().getRight().getValue());

        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight().getRight());
        assertNull(tree.getRoot().getRight().getLeft().getLeft());
        assertNull(tree.getRoot().getRight().getLeft().getRight());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
    }

    @Test
    void rightRightRotateInsert() {
        AVLTree tree = new AVLTree(new AVLTreeNode(2));

        tree.add(1);
        tree.add(4);
        tree.add(3);
        tree.add(5);
        tree.add(6);

        assertEquals(4, tree.getRoot().getValue());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(5, tree.getRoot().getRight().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(3, tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft());
        assertEquals(6, tree.getRoot().getRight().getRight().getValue());

        assertNull(tree.getRoot().getLeft().getRight().getLeft());
        assertNull(tree.getRoot().getLeft().getRight().getRight());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight().getRight());
    }

    @Test
    void leftRightRotateInsert() {
        AVLTree tree = new AVLTree(new AVLTreeNode(5));

        tree.add(2);
        tree.add(6);
        tree.add(1);
        tree.add(3);
        tree.add(4);

        assertEquals(3, tree.getRoot().getValue());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(5, tree.getRoot().getRight().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals(4, tree.getRoot().getRight().getLeft().getValue());
        assertEquals(6, tree.getRoot().getRight().getRight().getValue());

        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertNull(tree.getRoot().getRight().getLeft().getLeft());
        assertNull(tree.getRoot().getRight().getLeft().getRight());
        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight().getRight());
    }

    @Test
    void rightLeftRotateInsert() {
        AVLTree tree = new AVLTree(new AVLTreeNode(2));

        tree.add(1);
        tree.add(5);
        tree.add(4);
        tree.add(6);
        tree.add(3);

        assertEquals(4, tree.getRoot().getValue());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(5, tree.getRoot().getRight().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(3, tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft());
        assertEquals(6, tree.getRoot().getRight().getRight().getValue());

        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertNull(tree.getRoot().getLeft().getRight().getLeft());
        assertNull(tree.getRoot().getLeft().getRight().getRight());
        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight().getRight());
    }

    @Test
    void leftLeftRotateRemove() {
        AVLTree tree = new AVLTree(new AVLTreeNode(6));

        tree.add(4);
        tree.add(7);
        tree.add(2);
        tree.add(5);
        tree.add(8);
        tree.add(1);

        tree.remove(8);

        assertEquals(4, tree.getRoot().getValue());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(6, tree.getRoot().getRight().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals(5, tree.getRoot().getRight().getLeft().getValue());
        assertEquals(7, tree.getRoot().getRight().getRight().getValue());

        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight().getRight());
        assertNull(tree.getRoot().getRight().getLeft().getLeft());
        assertNull(tree.getRoot().getRight().getLeft().getRight());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
    }

    @Test
    void rightRightRotateRemove() {
        AVLTree tree = new AVLTree(new AVLTreeNode(2));

        tree.add(1);
        tree.add(3);
        tree.add(0);
        tree.add(4);
        tree.add(5);
        tree.add(6);

        tree.remove(0);


        assertEquals(4, tree.getRoot().getValue());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(5, tree.getRoot().getRight().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(3, tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft());
        assertEquals(6, tree.getRoot().getRight().getRight().getValue());

        assertNull(tree.getRoot().getLeft().getRight().getLeft());
        assertNull(tree.getRoot().getLeft().getRight().getRight());
        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight().getRight());
    }

    @Test
    void leftRightRotateRemove() {
        AVLTree tree = new AVLTree(new AVLTreeNode(5));

        tree.add(2);
        tree.add(6);
        tree.add(1);
        tree.add(3);
        tree.add(7);
        tree.add(4);

        tree.remove(7);

        assertEquals(3, tree.getRoot().getValue());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(5, tree.getRoot().getRight().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertNull(tree.getRoot().getLeft().getRight());
        assertEquals(4, tree.getRoot().getRight().getLeft().getValue());
        assertEquals(6, tree.getRoot().getRight().getRight().getValue());

        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertNull(tree.getRoot().getRight().getLeft().getLeft());
        assertNull(tree.getRoot().getRight().getLeft().getRight());
        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight().getRight());
    }

    @Test
    void rightLeftRotateRemove() {
        AVLTree tree = new AVLTree(new AVLTreeNode(2));

        tree.add(1);
        tree.add(5);
        tree.add(0);
        tree.add(4);
        tree.add(6);
        tree.add(3);

        tree.remove(0);

        assertEquals(4, tree.getRoot().getValue());

        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(5, tree.getRoot().getRight().getValue());

        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(3, tree.getRoot().getLeft().getRight().getValue());
        assertNull(tree.getRoot().getRight().getLeft());
        assertEquals(6, tree.getRoot().getRight().getRight().getValue());

        assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getLeft().getRight());
        assertNull(tree.getRoot().getLeft().getRight().getLeft());
        assertNull(tree.getRoot().getLeft().getRight().getRight());
        assertNull(tree.getRoot().getRight().getRight().getLeft());
        assertNull(tree.getRoot().getRight().getRight().getRight());
    }

    @Test
    void addAndRemove1000Nodes() {
        AVLTree tree = new AVLTree(new AVLTreeNode(0));

        SearchTreeTestUtils.addAndRemove1000Nodes(tree, this::satisfiesAVLInvariant);
    }

    @Test
    void addInEmptyTree() {
        AVLTree tree = new AVLTree(new AVLTreeNode(0));
        tree.remove(0);
        assertNull(tree.getRoot());
        tree.add(0);
        assertEquals(0, tree.getRoot().getValue());
    }

    boolean satisfiesAVLInvariant(SearchTreeNode avlTreeNode) {
        if (avlTreeNode == null) {
            return true;
        }

        int leftHeight = avlTreeNode.getLeft() == null ? 0 : avlTreeNode.getLeft().getHeight();
        int rightHeight = avlTreeNode.getRight() == null ? 0 : avlTreeNode.getRight().getHeight();

        if (Math.abs(leftHeight - rightHeight) >= 2) {
            return false;
        } else {
            return satisfiesAVLInvariant(avlTreeNode.getLeft()) && satisfiesAVLInvariant(avlTreeNode.getRight());
        }
    }
}
