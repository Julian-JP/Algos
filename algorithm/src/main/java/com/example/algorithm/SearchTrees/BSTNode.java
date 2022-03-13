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

    public BSTNode remove(int newValue, BSTNode exchg) {
        if (newValue < value) {
            left = left != null ? left.remove(newValue, exchg) : null;
            return this;
        } else if (newValue > value) {
            right = right != null ? right.remove(newValue, exchg) : null;
            return this;
        }

        if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        }

        BSTNode next = right;
        for (;;) {
            if (next.left != null) {
                next = next.left;
            } else if (next.right != null) {
                next = next.right;
            } else {
                next.left = left;
                next.right = right;
                return next;
            }
        }
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
