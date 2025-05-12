package com.example.algorithm.SearchTrees.RedBlackTree;

import com.example.algorithm.SearchTrees.SearchTree;
import com.example.algorithm.SearchTrees.SearchTreeNode;
import org.jetbrains.annotations.NotNull;

public class RedBlackTree extends SearchTree {
    public RedBlackTree(SearchTreeNode root) {
        super(root);
    }

    @Override
    public void add(int value) {
        if (getRoot().isNil()) {
            setRoot(new RedBlackTreeNode(value, false, null));
        }
        getRoot().add(value);
    }

    @Override
    public void remove(int value) {
        RedBlackTreeNode nodeToDelete = getRoot().find(value);
        RedBlackTreeNode replacement;
        RedBlackTreeNode x;

        if (nodeToDelete == null) {
            return;
        }

        if (nodeToDelete.getLeft().isNil() && nodeToDelete.getRight().isNil()) {
            replacement = nodeToDelete.getRight();
            x = replacement;
        } else if (nodeToDelete.getLeft().isNil()) {
            replacement = nodeToDelete.getRight();
            x = replacement;
        } else if (nodeToDelete.getRight().isNil()) {
            replacement = nodeToDelete.getLeft();
            x = replacement;
        } else {
            replacement = nodeToDelete.getRight().getSmallest();
            x = replacement.getRight();
        }

        spliceOut(nodeToDelete, replacement);


        if (nodeToDelete.getColor().equals("red") && (replacement.isNil() || replacement.getColor().equals("red"))) {
            return;
        } else if (nodeToDelete.getColor().equals("red") && !replacement.isNil() && replacement.getColor().equals("black")) {
            replacement.setColor("red");
        } else if (nodeToDelete.getColor().equals("black") && replacement.getColor().equals("red")) {
            replacement.setColor("black");
            return;
        } else if (nodeToDelete.getColor().equals("black") && isBlack(replacement) && x == getRoot()) {
            return;
        }


        //Delete fix up
        RedBlackTreeNode w = x.getParent().getLeft() == x ? x.getParent().getRight() : x.getParent().getLeft();
        deleteFix(x, w);
    }

    private void deleteFix(RedBlackTreeNode x, RedBlackTreeNode w) {
        if (isRed(x)) {
            deleteCase0(x);
        } else if (isBlack(x) && isRed(w)) {
            deleteCase1(x, w);
        } else if (isBlack(x) && isBlack(w) && w != null) {
            if (isBlack(w.getLeft()) && isBlack(w.getRight())) {
                deleteCase2(x, w);
            } else if ((isBlack(w.getLeft()) && isRed(w.getRight())) || (isRed(w.getLeft()) && isBlack(w.getRight()))) {
                deleteCase3(x, w);
            } else if (isRed(w.getLeft()) && isRed(w.getRight())) {
                deleteCase4(x, w);
            }
        }
    }

    private void deleteCase0(RedBlackTreeNode x) {
        x.setColor("black");
    }

    private void deleteCase1(RedBlackTreeNode x, RedBlackTreeNode w) {
        w.setColor("black");
        x.getParent().setColor("red");

        if (x.getParent().getLeft() == x) {
            x.getParent().rotateLeft();
            w = x.getParent().getRight();
        } else {
            x.getParent().rotateRight();
            w = x.getParent().getLeft();
        }
        deleteFix(x, w);
    }

    private void deleteCase2(RedBlackTreeNode x, RedBlackTreeNode w) {
        w.setColor("red");
        x = x.getParent();

        if (x.getColor().equals("red")) {
            deleteCase0(x);
        } else if (x == getRoot()) {
            return;
        } else {
            deleteFix(x, w);
        }
    }

    private void deleteCase3(RedBlackTreeNode x, RedBlackTreeNode w) {
        w.setColor("red");
        if (x.getParent().getLeft() == x) {
            w.getLeft().setColor("black");
            w.rotateRight();
            w = x.getParent().getRight();
        } else {
            w.getRight().setColor("black");
            w.rotateLeft();
            w = x.getParent().getLeft();
        }

        deleteCase4(x, w);
    }

    private void deleteCase4(RedBlackTreeNode x, RedBlackTreeNode w) {
        w.setColor(x.getParent().getColor());
        x.getParent().setColor("black");

        if (x.getParent().getLeft() == x) {
            w.getRight().setColor("black");
            x.getParent().rotateLeft();
        } else {
            w.getLeft().setColor("black");
            x.getParent().rotateRight();
        }
    }

    public void parents() {
        getRoot().updateParents(null);
    }

    @Override
    public RedBlackTreeNode getRoot() {
        return (RedBlackTreeNode) super.getRoot();
    }

    private boolean isBlack(RedBlackTreeNode node) {
        return node == null || node.getColor().equals("black");
    }

    private boolean isRed(RedBlackTreeNode node) {
        return node != null && node.getColor().equals("red");
    }

    private void spliceOut(@NotNull RedBlackTreeNode nodeToDelete, RedBlackTreeNode replacementNode) {
        if (replacementNode != null) {
            if (replacementNode.getParent() != null) {
                if (replacementNode.getParent().getLeft() == replacementNode) {
                    replacementNode.getParent().setLeft(replacementNode.getRight());
                } else {
                    replacementNode.getParent().setRight(replacementNode.getRight());
                }
            }
            if (!replacementNode.isNil()) {
                replacementNode.getRight().setParent(replacementNode.getParent());
            }

            replacementNode.setParent(nodeToDelete.getParent());
            replacementNode.setRight(nodeToDelete.getRight());
            replacementNode.setLeft(nodeToDelete.getLeft());

            if (nodeToDelete.getRight() != null) {
                nodeToDelete.getRight().setParent(replacementNode);
            }
            if (nodeToDelete.getLeft() != null) {
                nodeToDelete.getLeft().setParent(replacementNode);
            }
        }

        if (nodeToDelete.getParent() != null) {
            if (nodeToDelete.getParent().getLeft() == nodeToDelete) {
                nodeToDelete.getParent().setLeft(replacementNode);
            } else {
                nodeToDelete.getParent().setRight(replacementNode);
            }
        }

        if (nodeToDelete == getRoot()) {
            setRoot(replacementNode);
        }

    }
}
