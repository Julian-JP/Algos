package com.example.algorithm.SearchTrees;

import lombok.Getter;
import lombok.Setter;

public abstract class SearchTree {

    @Getter
    @Setter
    private SearchTreeNode root;

    public SearchTree(SearchTreeNode root) {
        this.setRoot(root);
    }
    public abstract void add(int value);
    public abstract void remove(int value);
    public abstract boolean contains(int value);
}
