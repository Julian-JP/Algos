package com.example.algorithm.SearchTrees.BinarySearchTree;

import com.example.algorithm.SearchTrees.SearchTree;
import com.example.algorithm.SearchTrees.SearchTreeNode;

public class BinarySearchTree extends SearchTree {

    public BinarySearchTree(SearchTreeNode root) {
        super(root);
    }

    public void add(int value) {
        if (getRoot() != null) {
            getRoot().add(value);
        } else {
            setRoot(new BSTNode(value));
        }
    }

    public void remove(int value) {
        if (getRoot() != null) {
            setRoot(getRoot().remove(value));
        }
    }
}
