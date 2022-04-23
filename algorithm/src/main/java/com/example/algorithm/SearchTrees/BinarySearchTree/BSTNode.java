package com.example.algorithm.SearchTrees.BinarySearchTree;

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

    public BSTNode remove(int newValue) {
        if (newValue < value) {
            left = left != null ? left.remove(newValue) : null;
            return this;
        } else if (newValue > value) {
            right = right != null ? right.remove(newValue) : null;
            return this;
        }

        if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        }

        BSTNode prev = this;
        BSTNode next = right;
        for (;;) {
            if (next.left != null) {
                prev = next;
                next = next.left;
            } else {
                if (prev.left == next) prev.left = next.right;
                else prev.right = next.right;
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
