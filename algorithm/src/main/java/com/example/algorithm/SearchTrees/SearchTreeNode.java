package com.example.algorithm.SearchTrees;

public abstract class SearchTreeNode {
    private int value;
    private SearchTreeNode left;
    private SearchTreeNode right;

    public SearchTreeNode(int value) {
        this.value = value;
    }
    public SearchTreeNode(int value, SearchTreeNode left, SearchTreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public abstract void add(int newValue);

    public abstract SearchTreeNode remove(int newValue);

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public SearchTreeNode getLeft() {
        return left;
    }

    public void setLeft(SearchTreeNode left) {
        this.left = left;
    }

    public SearchTreeNode getRight() {
        return right;
    }

    public void setRight(SearchTreeNode right) {
        this.right = right;
    }
}
