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
    public BSTNode add(Integer newValue) {
        if (newValue < getValue()) {
            if (getLeft() == null) {
                setLeft(new BSTNode(newValue));
            } else {
                setLeft(getLeft().add(newValue));
            }
        } else if (newValue > getValue()) {
            if (getRight() == null) {
                setRight(new BSTNode(newValue));
            } else {
                setRight(getRight().add(newValue));
            }
        }
        return this;
    }

    @Override
    public SearchTreeNode remove(Integer deleteValue) {
        if (deleteValue.equals(getValue())) {
            return removeThisNode();
        }

        if (deleteValue < getValue() && getLeft() != null) {
            setLeft(getLeft().remove(deleteValue));
            return this;
        }
        if (deleteValue > getValue() && getRight() != null) {
            setRight(getRight().remove(deleteValue));
            return this;
        }
        return this;
    }

    private BSTNode removeThisNode() {
        if (getLeft() == null) {
            return getRight();
        } else if (getRight() == null) {
            return getLeft();
        } else {
            BSTNode successor = getRight().getSmallestNode();
            setValue(successor.getValue());
            setRight(getRight().remove(successor.getValue()));
            return this;
        }
    }

    private BSTNode getSmallestNode() {
        if (getLeft() != null) {
            return getLeft().getSmallestNode();
        } else {
            return this;
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
