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
    public BSTNode add(int newValue) {
        if (newValue < getValue()) {
            if (getLeft() != null) {
                getLeft().add(newValue);
            } else {
                setLeft(new BSTNode(newValue));
                return this;
            }
        } else if (newValue > getValue()) {
            if (getRight() != null) {
                getRight().add(newValue);
            } else {
                setRight(new BSTNode(newValue));
                return this;
            }
        }
        return null;
    }

    @Override
    public SearchTreeNode remove(int deleteValue) {
        if (deleteValue < getValue()) {
            return removeInLeftSubTree(deleteValue);
        } else if (deleteValue > getValue()) {
            return removeInRightSubTree(deleteValue);
            //Remove node
        } else {
            return removeThisNode();
        }
    }

    private BSTNode removeInLeftSubTree(int deleteValue) {
        if (getLeft() != null) {
            setLeft(getLeft().remove(deleteValue));
        }
        return this;
    }

    private BSTNode removeInRightSubTree(int deleteValue) {
        if (getRight() != null) {
            setRight(getRight().remove(deleteValue));
        }
        return this;
    }

    private BSTNode removeThisNode() {
        if (getLeft() == null) {
            return getRight();
        } else if (getRight() == null) {
            return getLeft();
        } else {
            setLeft(getLeft().changeWithPredecessor(this));
            return this;
        }
    }

    private BSTNode changeWithPredecessor(BSTNode root) {
        if (this.getRight() != null) {
            setRight(getRight().changeWithPredecessor(root));
        } else {
            root.setValue(this.getValue());
            return getLeft();
        }
        return this;
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
