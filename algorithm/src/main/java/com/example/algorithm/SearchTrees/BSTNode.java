package com.example.algorithm.SearchTrees;

public class BSTNode {
    private int value;
    private BSTNode left;
    private BSTNode right;

    public BSTNode(int value) {
        this.value = value;
    }

    public BSTNode(int value, BSTNode left, BSTNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public void add(int newValue) {
        if (newValue < value) {
            if (left != null) {
                left.add(newValue);
            } else {
                left = new BSTNode(newValue);
            }
        } else if (newValue > value) {
            if (right != null) {
                right.add(newValue);
            } else {
                right = new BSTNode(newValue);
            }
        }
    }

    public void remove(int value) {

    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BSTNode getLeft() {
        return left;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }
}
