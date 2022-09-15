package com.example.algorithm.SearchTrees.RedBlackTree;

import com.example.algorithm.SearchTrees.SearchTree;
import com.example.algorithm.SearchTrees.SearchTreeNode;

public class RedBlackTree extends SearchTree {
    public RedBlackTree(SearchTreeNode root) {
        super(root);
    }

    @Override
    public void add(int value) {
        if (getRoot() == null) {
            setRoot(new RedBlackTreeNode(value, false, null));
        } else {
            setRoot(getRoot().add(value));
        }
    }

    @Override
    public void remove(int value) {
        if (getRoot() != null) {
            setRoot(getRoot().remove(value));
        }
    }

    public void parents() {
        ((RedBlackTreeNode) getRoot()).updateParents(null);

    }
}
