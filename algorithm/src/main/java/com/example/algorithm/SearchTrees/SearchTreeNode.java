package com.example.algorithm.SearchTrees;

public abstract class SearchTreeNode {
    private Integer value;
    private SearchTreeNode left;
    private SearchTreeNode right;
    private String color = "white";

    public SearchTreeNode(Integer value) {
        this.value = value;
    }
    public SearchTreeNode(Integer value, SearchTreeNode left, SearchTreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public abstract SearchTreeNode add(Integer newValue);

    public abstract SearchTreeNode remove(Integer newValue);

    public Integer getValue() {
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

    public int getHeight() {
        int maxHeight = 1;
        if (left != null) {
            maxHeight = Math.max(left.getHeight() + 1, maxHeight);
        }
        if (right != null) {
            maxHeight = Math.max(right.getHeight() + 1, maxHeight);
        }
        return maxHeight;
    }
}
