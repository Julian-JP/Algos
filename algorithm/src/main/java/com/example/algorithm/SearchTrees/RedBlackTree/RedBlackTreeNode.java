package com.example.algorithm.SearchTrees.RedBlackTree;

import com.example.algorithm.SearchTrees.SearchTreeNode;

public class RedBlackTreeNode extends SearchTreeNode {
    public RedBlackTreeNode(int value, boolean isRed) {
        super(value);
        setColor(isRed ? "red" : "black");
    }

    public RedBlackTreeNode(int value, RedBlackTreeNode left, RedBlackTreeNode right, String color) {
        super(value, left, right);
        setColor(color);
    }

    @Override
    public RedBlackTreeNode add(int newValue) {
        return add(newValue, null, null);
    }

    public RedBlackTreeNode add(int newValue, RedBlackTreeNode grandparent, RedBlackTreeNode uncle) {
        if (getValue() == newValue) {
            return this;
        } else if (getValue() > newValue) {
            if (getLeft() == null) {
                setLeft(new RedBlackTreeNode(newValue, true));
                selectCaseInsert(grandparent, this, uncle, (RedBlackTreeNode) getLeft());
            }
        } else if (getValue() < newValue) {
            if (getRight() == null) {
                setRight(new RedBlackTreeNode(newValue, true));
                selectCaseInsert(grandparent, this, uncle, (RedBlackTreeNode) getRight());
            }
        }
        return this;
    }

    private void selectCaseInsert(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        if (parent != null && parent.getColor() == "black") caseBlackParent(child);
        else if (uncle != null && uncle.getColor() == "red") caseRedUncle(grandParent, parent, uncle, child);
        else if (uncle == null || uncle.getColor() == "black") caseBlackUncle(child);
    }

    private void caseBlackParent(RedBlackTreeNode child) {
        child.setColor("black");
    }

    private void caseRedUncle(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        parent.setColor("black");
        uncle.setColor("black");
        grandParent.setColor("red");
    }

    private void caseBlackUncle(RedBlackTreeNode child) {

    }

    @Override
    public SearchTreeNode remove(int newValue) {
        return null;
    }
}
