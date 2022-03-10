package com.example.algorithm.SearchTrees;

import lombok.Getter;
import lombok.Setter;

public class BinarySearchTree {

    @Getter @Setter private BSTNode root;

    public BinarySearchTree(BSTNode root) {
        this.root = root;
    }

    public void add(int value) {
        root.add(value);
    }

    public void remove(int value) {

    }
}
