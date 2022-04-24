package com.example.algorithm.SearchTrees.BinarySearchTree;

import com.example.algorithm.SearchTrees.SearchTreeNode;

public class BSTNode extends SearchTreeNode {
    public BSTNode(int value) {
        super(value);
    }

    public BSTNode(int value, BSTNode left, BSTNode right) {
        super(value, left, right);
    }

    @Override
    public void add(int newValue) {
        if (newValue < getValue()) {
            if (getLeft() != null) {
                getLeft().add(newValue);
            } else {
                setLeft(new BSTNode(newValue));
            }
        } else if (newValue > getValue()) {
            if (getRight() != null) {
                getRight().add(newValue);
            } else {
                setRight(new BSTNode(newValue));
            }
        }
    }

    @Override
    public BSTNode remove(int newValue) {
        if (newValue < getValue()) {
            setLeft(getLeft() != null ? getLeft().remove(newValue) : null);
            return this;
        } else if (newValue > getValue()) {
            setRight(getRight() != null ? getRight().remove(newValue) : null);
            return this;
        }

        if (getLeft() == null) {
            return getRight();
        } else if (getRight() == null) {
            return getLeft();
        }

        BSTNode prev = this;
        BSTNode next = getRight();
        for (;;) {
            if (next.getLeft() != null) {
                prev = next;
                next = next.getLeft();
            } else {
                if (prev.getLeft() == next) setLeft(next.getRight());
                else setRight(next.getRight());
                next.setLeft(getLeft());
                next.setRight(getRight());
                return next;
            }
        }
    }
    @Override
    public BSTNode getLeft() {
        return (BSTNode) super.getLeft();
    }

    @Override
    public BSTNode getRight() {
        return (BSTNode) super.getRight();
    }
}
