package com.example.algorithm.SearchTrees;

public abstract class SearchTreeNode {
    private int value;
    private SearchTreeNode left;
    private SearchTreeNode right;
    private String color = "white";

    public SearchTreeNode(int value) {
        this.value = value;
    }
    public SearchTreeNode(int value, SearchTreeNode left, SearchTreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public abstract SearchTreeNode add(int newValue);

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
