package com.example.algorithm.SearchTrees.AVLTree;

import com.example.algorithm.SearchTrees.SearchTreeNode;

public class AVLTreeNode extends SearchTreeNode {

    public AVLTreeNode(int value) {
        super(value);
    }
    public AVLTreeNode(int value, AVLTreeNode left, AVLTreeNode right) {
        super(value, left, right);
    }

    @Override
    public void add(int newValue) {

    }

    @Override
    public SearchTreeNode remove(int newValue) {
        return null;
    }
}
