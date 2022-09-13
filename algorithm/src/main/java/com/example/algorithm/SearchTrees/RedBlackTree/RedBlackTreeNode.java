package com.example.algorithm.SearchTrees.RedBlackTree;

import com.example.algorithm.SearchTrees.SearchTreeNode;

public class RedBlackTreeNode extends SearchTreeNode {
    public RedBlackTreeNode(int value, boolean isRed) {
        super(value);
        setColor(isRed ? "red" : "black");
    }

    public RedBlackTreeNode(int value, RedBlackTreeNode left, RedBlackTreeNode right) {
        super(value, left, right);
    }

    @Override
    public RedBlackTreeNode add(int newValue) {
        return add(newValue, null, null);
    }

    public RedBlackTreeNode add(int newValue, RedBlackTreeNode grandparent, RedBlackTreeNode uncle) {
        if (getValue() == newValue) {
            return this;
        } else if (getValue() < newValue) {
            if (getLeft() == null) {
                setLeft(new RedBlackTreeNode(newValue, true));
                selectCase(grandparent, this, uncle, (RedBlackTreeNode) getLeft());
            }
        } else if (getValue() > newValue) {
            if (getRight() == null) {
                setRight(new RedBlackTreeNode(newValue, true));
                selectCase(grandparent, this, uncle, (RedBlackTreeNode) getRight());
            }
        }
    }

    private void selectCase(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        if (parent.getColor() == "black") caseBlackParent(child);
    }

    private void caseBlackParent(RedBlackTreeNode child) {
        child.setColor("black");
    }

    private RedBlackTreeNode SelectCaseInsert(RedBlackTreeNode current, )

    @Override
    public SearchTreeNode remove(int newValue) {
        return null;
    }


}
