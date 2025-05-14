package com.example.algorithm.SearchTrees.RedBlackTree;

import com.example.algorithm.SearchTrees.BinarySearchTree.BSTNode;
import com.example.algorithm.SearchTrees.SearchTreeNode;
import lombok.Getter;
import lombok.Setter;

public class RedBlackTreeNode extends BSTNode {

    public static RedBlackTreeNode getNilNode(RedBlackTreeNode parent) {
        return new RedBlackTreeNode(parent);
    }

    @Setter
    @Getter
    private RedBlackTreeNode parent;


    public RedBlackTreeNode(Integer value, boolean isRed, RedBlackTreeNode parent) {
        super(value);
        setColor(isRed ? "red" : "black");
        this.parent = parent;
        super.setLeft(getNilNode(this));
        super.setRight(getNilNode(this));
    }

    public RedBlackTreeNode(Integer value, RedBlackTreeNode left, RedBlackTreeNode right, String color, RedBlackTreeNode parent) {
        super(value, left, right);
        setColor(color);
        this.parent = parent;
    }

    private RedBlackTreeNode(RedBlackTreeNode parent) {
        super(null, null, null);
        this.parent = parent;
        setColor("black");
    }

    @Override
    public RedBlackTreeNode add(Integer newValue) {
         if (getValue() > newValue) {
             if (getLeft().isNil()) {
                 setLeft(new RedBlackTreeNode(newValue, true, this));
                 return getLeft().balanceInsert();
             } else {
                 return getLeft().add(newValue);
             }
        } else if (getValue() < newValue) {
             if (getRight().isNil()) {
                 setRight(new RedBlackTreeNode(newValue, true, this));
                 return getRight().balanceInsert();
             } else {
                 return getRight().add(newValue);
             }
        }
        return getRoot();
    }

    private RedBlackTreeNode balanceInsert() {
        if (parent != null && parent.getColor().equals("black")) {
            return getRoot();
        }
        if (isReadUncle()) {
            insertRedUncleCase();
            return parent.getParent().balanceInsert();
        } else if (parent != null && parent.parent != null){
            boolean thisIsLeftChild = parent.getLeft() == this;
            boolean parentIsLeftChild = parent.parent.getLeft() == parent;

            if (thisIsLeftChild && parentIsLeftChild) {
                return insertLeftLeft().getRoot();
            } else if (!thisIsLeftChild && !parentIsLeftChild) {
                return insertRightRight().getRoot();
            } else if (!thisIsLeftChild) {
                return insertLeftRight().getRoot();
            } else {
                return insertRightLeft().getRoot();
            }
        }

        return this;
    }

    private RedBlackTreeNode getRoot() {
        if (parent == null) {
            return this;
        } else {
            return parent.getRoot();
        }
    }

    @Override
    public SearchTreeNode remove(Integer newValue) {
        throw new UnsupportedOperationException("No recursive implementation supported");
    }

    private boolean isReadUncle() {
        return getUncle() != null && getUncle().getColor().equals("red");
    }

    private void insertRedUncleCase() {
        parent.setColor("black");
        parent.parent.setColor("red");
        getUncle().setColor("black");
    }

    private RedBlackTreeNode insertLeftLeft() {
        RedBlackTreeNode p = parent;
        RedBlackTreeNode gp = p.parent;

        String parentColor = p.getColor();
        String gpColor = gp.getColor();

        p.setColor(gpColor);
        gp.setColor(parentColor);

        return gp.rotateRight();
    }

    private RedBlackTreeNode insertLeftRight() {
        RedBlackTreeNode p = parent;

        p.rotateLeft();

        return p.insertLeftLeft();
    }

    private RedBlackTreeNode insertRightRight() {
        RedBlackTreeNode p = parent;
        RedBlackTreeNode gp = p.parent;

        String parentColor = p.getColor();
        String gpColor = gp.getColor();

        p.setColor(gpColor);
        gp.setColor(parentColor);

        return gp.rotateLeft();
    }

    private RedBlackTreeNode insertRightLeft() {
        RedBlackTreeNode p = parent;

        p.rotateRight();

        return p.insertRightRight();
    }

    RedBlackTreeNode rotateLeft() {
        /*
          A                     B
           \                   / \
            B       =>        A   C
             \
              C
         */

        RedBlackTreeNode a = this;
        RedBlackTreeNode b = getRight();

        RedBlackTreeNode ap = a.parent;
        RedBlackTreeNode bl = b.getLeft();

        b.setLeft(a);
        a.parent = b;

        b.parent = ap;
        if (ap != null) {
            if (ap.getRight() == a) {
                ap.setRight(b);
            } else {
                ap.setLeft(b);
            }
        }

        a.setRight(bl);
        if (bl != null) {
            bl.parent = a;
        }


        return b;
    }

    RedBlackTreeNode rotateRight() {
        /*
              A                 B
             /                 / \
            B       =>        C   A
           /
          C
         */

        RedBlackTreeNode a = this;
        RedBlackTreeNode b = getLeft();

        RedBlackTreeNode ap = a.parent;
        RedBlackTreeNode br = b.getRight();

        b.setRight(a);
        a.parent = b;

        b.parent = ap;
        if (ap != null) {
            if (ap.getRight() == a) {
                ap.setRight(b);
            } else {
                ap.setLeft(b);
            }
        }

        a.setLeft(br);
        if (br != null) {
            br.parent = a;
        }


        return b;
    }

    private RedBlackTreeNode getUncle() {
        if (parent != null) {
            return parent.getSibling();
        }

        return null;
    }

    private RedBlackTreeNode getSibling() {
        if (parent != null && parent.getLeft() == this) {
            return parent.getRight();
        } else if (parent != null && parent.getRight() == this) {
            return parent.getLeft();
        }

        return null;
    }


    public RedBlackTreeNode find(int value) {
        if (isNil()) {
            return null;
        }

        if (value < getValue()) {
            if (getLeft() != null) {
                return getLeft().find(value);
            } else {
                return null;
            }
        } else if (value > getValue()) {
            if (getRight() != null) {
                return getRight().find(value);
            } else {
                return null;
            }
        } else {
            return this;
        }
    }

    public RedBlackTreeNode getSmallest() {
        if (!getLeft().isNil()) {
            return getLeft().getSmallest();
        } else {
            return this;
        }
    }

    public void updateParents(RedBlackTreeNode parent) {
        this.parent = parent;
        if (getLeft() != null) getLeft().updateParents(this);
        if (getRight() != null) getRight().updateParents(this);
    }

    public void traverseInorder(String tabs) {
        if (getLeft() != null) {
            getLeft().traverseInorder(tabs + "\t");
        }

        if (isNil()) {
            System.out.println(tabs + "NIL");
        } else {
            if (getColor().equals("red")) {
                System.out.println(tabs + "\u001B[31m" + getValue() + "\u001B[0m");
            } else {
                System.out.println(tabs + getValue());
            }
        }

        if (getRight() != null) {
            getRight().traverseInorder(tabs + "\t");
        }
    }

    boolean isBlack() {
        return isNil() || getColor().equals("black");
    }

    boolean isRightChild() {
        return parent != null && parent.getRight() == this;
    }

    boolean isLeftChild() {
        return parent != null && parent.getLeft() == this;
    }

    @Override
    public RedBlackTreeNode getRight() {
        return (RedBlackTreeNode) super.getRight();
    }

    @Override
    public RedBlackTreeNode getLeft() {
        return (RedBlackTreeNode) super.getLeft();
    }

    @Override
    public void setRight(SearchTreeNode newRight) {
        if (!isNil()) {
            super.setRight(newRight);
        }
    }

    @Override
    public void setLeft(SearchTreeNode newLeft) {
        if (!isNil()) {
            super.setLeft(newLeft);
        }
    }

    public boolean isNil() {
        return getValue() == null;
    }
}
