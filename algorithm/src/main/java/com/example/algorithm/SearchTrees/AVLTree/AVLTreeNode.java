package com.example.algorithm.SearchTrees.AVLTree;

import com.example.algorithm.SearchTrees.SearchTreeNode;

public class AVLTreeNode extends SearchTreeNode {

    private int height;

    public AVLTreeNode(int value) {
        super(value);
        height = 1;
    }

    public AVLTreeNode(int value, AVLTreeNode left, AVLTreeNode right) {
        super(value, left, right);
        updateHeight();
    }

    @Override
    public AVLTreeNode add(Integer newValue) {
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
        return balanceInsertion();
    }

    @Override
    public SearchTreeNode remove(Integer deleteValue) {
        if (deleteValue.equals(getValue())) {
            AVLTreeNode unbalancedRemovedSubtree = removeThisNode();
            if (unbalancedRemovedSubtree != null) {
                return unbalancedRemovedSubtree.balanceRemoval();
            } else {
                return null;
            }
        }

        if (deleteValue < getValue() && getLeft() != null) {
            setLeft(getLeft().remove(deleteValue));
        } else if (deleteValue > getValue() && getRight() != null) {
            setRight(getRight().remove(deleteValue));
        }
        return balanceRemoval();
    }

    private AVLTreeNode removeThisNode() {
        if (getLeft() == null) {
            return getRight();
        } else if (getRight() == null) {
            return getLeft();
        } else {
            AVLTreeNode successor = getRight().getSmallestNode();
            setValue(successor.getValue());
            setRight(getRight().remove(successor.getValue()));
            return this;
        }
    }

    private AVLTreeNode getSmallestNode() {
        if (getLeft() != null) {
            return getLeft().getSmallestNode();
        } else {
            return this;
        }
    }

    private AVLTreeNode balanceInsertion() {
        int heightDifference = getHeightDifference();

        if (heightDifference == 2 && getLeft().getHeightDifference() > 0) {
            return rotateRight();
        } else if (heightDifference == -2 && getRight().getHeightDifference() < 0) {
            return rotateLeft();
        } else if (heightDifference == 2 && getLeft().getHeightDifference() < 0) {
            return rotateLeftRight();
        } else if (heightDifference == -2 && getRight().getHeightDifference() > 0) {
            return rotateRightLeft();
        }

        updateHeight();
        return this;
    }

    private AVLTreeNode balanceRemoval() {
        int heightDifference = getHeightDifference();

        if (heightDifference == 2 && getLeft().getHeightDifference() >= 0) {
            return rotateRight();
        } else if (heightDifference == -2 && getRight().getHeightDifference() <= 0) {
            return rotateLeft();
        } else if (heightDifference == 2 && getLeft().getHeightDifference() < 0) {
            return rotateLeftRight();
        } else if (heightDifference == -2 && getRight().getHeightDifference() > 0) {
            return rotateRightLeft();
        }

        updateHeight();
        return this;
    }

    private AVLTreeNode rotateRight() {
        AVLTreeNode root = this;
        AVLTreeNode left = root.getLeft();

        root.setLeft(left.getRight());
        left.setRight(root);

        root.updateHeight();
        left.updateHeight();
        return left;
    }

    private AVLTreeNode rotateLeft() {
        AVLTreeNode root = this;
        AVLTreeNode right = root.getRight();

        root.setRight(right.getLeft());
        right.setLeft(root);

        root.updateHeight();
        right.updateHeight();
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

        root.updateHeight();
        right.updateHeight();
        rightLeft.updateHeight();

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

        root.updateHeight();
        left.updateHeight();
        leftRight.updateHeight();

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

    private void updateHeight() {
        int leftHeight = getLeft() == null ? 0 : getLeft().height;
        int rightHeight = getRight() == null ? 0 : getRight().height;

        height = Math.max(leftHeight, rightHeight) + 1;
    }

    private int getHeightDifference() {
        int leftHeight = getLeft() == null ? 0 : getLeft().height;
        int rightHeight = getRight() == null ? 0 : getRight().height;
        return leftHeight - rightHeight;
    }
}
