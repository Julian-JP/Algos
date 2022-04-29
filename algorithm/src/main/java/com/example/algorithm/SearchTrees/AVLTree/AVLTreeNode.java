package com.example.algorithm.SearchTrees.AVLTree;

import com.example.algorithm.SearchTrees.SearchTreeNode;

public class AVLTreeNode extends SearchTreeNode {

    private int heightDifference;

    public AVLTreeNode(int value) {
        super(value);
        heightDifference = 0;
    }

    public AVLTreeNode(int value, AVLTreeNode left, AVLTreeNode right) {
        super(value, left, right);
        heightDifference = updateHeightDifferenze();
    }

    @Override
    public AVLTreeNode add(int newValue) {
        if (getValue() == newValue) {
            return this;
        } else if (newValue < getValue()) {
            if (getLeft() != null) {
                setLeft(getLeft().add(newValue));
            } else {
                setLeft(new AVLTreeNode(newValue));
            }
        } else if (newValue > getValue()) {
            if (getRight() != null) {
                setRight(getRight().add(newValue));
            } else {
                setRight(new AVLTreeNode(newValue));
            }
        }
        return balance();
    }

    @Override
    public SearchTreeNode remove(int deleteValue) {
        if (deleteValue < getValue()) {
            if (getLeft() != null) {
                setLeft(getLeft().remove(deleteValue));
            } else {
                return this;
            }
        } else if (deleteValue > getValue()) {
            if (getRight() != null) {
                setRight(getRight().remove(deleteValue));
            } else {
                return this;
            }
            //Remove node
        } else if (getLeft() == null) {
            return getRight();
        } else if (getRight() == null) {
            return getLeft();
        } else {
            setLeft(getLeft().changeWithPredecessor(this));
        }
        return balance();
    }

    private AVLTreeNode changeWithPredecessor(AVLTreeNode root) {
        if (this.getRight() != null) {
            setRight(getRight().changeWithPredecessor(root));
            return balance();
        } else {
            root.setValue(this.getValue());
            return this.getLeft();
        }
    }

    private AVLTreeNode balance() {
        heightDifference = updateHeightDifferenze();
        if (heightDifference == 2 && getRight().heightDifference >= 0) {
            return rotateLeft();
        } else if (heightDifference == -2 && getLeft().heightDifference <= 0) {
            return rotateRight();
        } else if (heightDifference == 2 && getRight().heightDifference < 0) {
            return rotateRightLeft();
        } else if (heightDifference == -2 && getLeft().heightDifference > 0) {
            return rotateLeftRight();
        }
        return this;
    }

    private AVLTreeNode rotateRight() {
        AVLTreeNode root = this;
        AVLTreeNode left = root.getLeft();

        root.setLeft(left.getRight());
        left.setRight(root);
        return left;
    }

    private AVLTreeNode rotateLeft() {
        AVLTreeNode root = this;
        AVLTreeNode right = root.getRight();

        root.setRight(right.getLeft());
        right.setLeft(root);
        return right;
    }

    private AVLTreeNode rotateRightLeft() {
        AVLTreeNode root = this;
        AVLTreeNode right = root.getRight();
        AVLTreeNode rightLeft = root.getRight().getLeft();

        root.setRight(rightLeft.getLeft());
        right.setLeft(rightLeft.getRight());
        rightLeft.setLeft(root);
        rightLeft.setRight(right);

        return rightLeft;
    }

    private AVLTreeNode rotateLeftRight() {
        AVLTreeNode root = this;
        AVLTreeNode left = root.getLeft();
        AVLTreeNode leftRight = root.getLeft().getRight();

        root.setLeft(leftRight.getRight());
        left.setRight(leftRight.getLeft());
        leftRight.setRight(root);
        leftRight.setLeft(left);

        return leftRight;
    }

    @Override
    public AVLTreeNode getRight() {
        return (AVLTreeNode) super.getRight();
    }

    @Override
    public AVLTreeNode getLeft() {
        return (AVLTreeNode) super.getLeft();
    }

    private int getHeight() {
        int heightLeft;
        int heightRight;

        if (getLeft() != null) {
            heightLeft = getLeft().getHeight();
        } else {
            heightLeft = 0;
        }

        if (getRight() != null) {
            heightRight = getRight().getHeight();
        } else {
            heightRight = 0;
        }

        return Math.max(heightRight, heightLeft) + 1;
    }

    private int updateHeightDifferenze() {
        if (getLeft() != null && getRight() != null) {
            return getRight().getHeight() - getLeft().getHeight();
        } else if (getLeft() != null) {
            return -getLeft().getHeight();
        } else if (getRight() != null) {
            return getRight().getHeight();
        } else {
            return 0;
        }
    }
}
