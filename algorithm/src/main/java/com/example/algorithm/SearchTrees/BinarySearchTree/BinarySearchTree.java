package com.example.algorithm.SearchTrees.BinarySearchTree;

import lombok.Getter;
import lombok.Setter;

public class BinarySearchTree {

    @Getter @Setter private BSTNode root;

    public BinarySearchTree(BSTNode root) {
        this.root = root;
    }

    public void add(int value) {
        if (root != null) {
            root.add(value);
        } else {
            root = new BSTNode(value);
        }
    }

    public void remove(int value) {
        if (root != null) {
            root = root.remove(value);
        }
    }
}
