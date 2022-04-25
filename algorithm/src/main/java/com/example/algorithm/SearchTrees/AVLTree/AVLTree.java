package com.example.algorithm.SearchTrees.AVLTree;

import com.example.algorithm.SearchTrees.SearchTree;
import com.example.algorithm.SearchTrees.SearchTreeNode;

public class AVLTree extends SearchTree {
    public AVLTree(SearchTreeNode root) {
        super(root);
    }

    @Override
    public void add(int value) {
        if (getRoot() == null) {
            setRoot(new AVLTreeNode(value));
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
}
