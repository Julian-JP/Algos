package com.example.algorithm.SearchTrees.RedBlackTree;

import com.example.algorithm.SearchTrees.SearchTreeNode;

public class RedBlackTreeNode extends SearchTreeNode {
    private RedBlackTreeNode parent;
    public RedBlackTreeNode(int value, boolean isRed, RedBlackTreeNode parent) {
        super(value);
        setColor(isRed ? "red" : "black");
        this.parent = parent;
    }

    public RedBlackTreeNode(int value, RedBlackTreeNode left, RedBlackTreeNode right, String color, RedBlackTreeNode parent) {
        super(value, left, right);
        setColor(color);
        this.parent = parent;
    }

    @Override
    public RedBlackTreeNode add(int newValue) {
        RedBlackTreeNode root = this;
        RedBlackTreeNode temp = addRec(newValue);
        if (temp == null) return root;
        else return fixTree(temp, root);
    }

    public RedBlackTreeNode addRec(int newValue) {
        if (getValue() > newValue) {
            if (getLeft() == null) {
                setLeft(new RedBlackTreeNode(newValue, true, this));
                return getLeft();
            } else {
                return getLeft().addRec(newValue);
            }
        } else if (getValue() < newValue) {
            if (getRight() == null) {
                setRight(new RedBlackTreeNode(newValue, true, this));
                return getRight();
            } else {
                return getRight().addRec(newValue);
            }
        }
        return null;
    }

    public RedBlackTreeNode fixTree(RedBlackTreeNode child, RedBlackTreeNode root) {
        RedBlackTreeNode parent = child.parent;
        if (parent == null) {
            return child;
        }

        RedBlackTreeNode grandParent = parent.parent;
        if (grandParent == null) {
            parent.setColor("black");
            return parent;
        }

        RedBlackTreeNode uncle = grandParent.getLeft() != parent ? grandParent.getLeft() : grandParent.getRight();

        if (grandParent.getColor().equals("red")) {
            RedBlackTreeNode temp = grandParent;
            while (temp.parent != null) {
                temp = temp.parent;
            }
            return temp;
        }

        RedBlackTreeNode posNewRoot = selectCaseInsert(grandParent, parent, uncle, child);
        if (grandParent == posNewRoot) {
            RedBlackTreeNode temp = fixTree(grandParent, root);
            temp.setColor("black");
            return temp;
        } else if (grandParent == root){
            posNewRoot.setColor("black");
            return posNewRoot;
        } else {
            return root;
        }

    }

    private RedBlackTreeNode selectCaseInsert(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        if (grandParent == null) return parent;
        else if (parent != null && parent.getColor().equals("black")) return caseBlackParent(grandParent, child);
        else if (uncle != null && uncle.getColor().equals("red")) return caseRedUncle(grandParent, parent, uncle);
        else if (grandParent == null) return parent;
        else if (grandParent.getLeft() == parent && parent.getLeft() == child) return caseLeftLeft(grandParent, parent, uncle, child);
        else if (grandParent.getLeft() == parent && parent.getRight() == child) return caseLeftRight(grandParent, parent, uncle, child);
        else if (grandParent.getRight() == parent && parent.getRight() == child) return caseRightRight(grandParent, parent, uncle, child);
        else if (grandParent.getRight() == parent && parent.getLeft() == child) return caseRightLeft(grandParent, parent, uncle, child);

        return null;
    }

    private RedBlackTreeNode caseBlackParent(RedBlackTreeNode grandParent, RedBlackTreeNode child) {
        System.out.println("BlackParent");
        child.setColor("red");
        return grandParent;
    }

    private RedBlackTreeNode caseRedUncle(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle) {
        System.out.println("Red Uncle");
        parent.setColor("black");
        uncle.setColor("black");
        grandParent.setColor("red");
        return grandParent;
    }

    private RedBlackTreeNode caseLeftLeft(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        System.out.println("LeftLeft");
        if (grandParent.parent != null && grandParent.parent.getRight() == grandParent) {
            grandParent.parent.setRight(parent);
        } else if (grandParent.parent != null) {
            grandParent.parent.setLeft(parent);
        }
        child.parent = parent;
        parent.parent = grandParent.parent;
        grandParent.parent = parent;
        if (parent.getRight() != null) {
            parent.getRight().parent = grandParent;
        }

        grandParent.setColor("red");
        parent.setColor("black");
        grandParent.setLeft(parent.getRight());
        parent.setRight(grandParent);

        return parent;
    }

    private RedBlackTreeNode caseLeftRight(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        System.out.println("LeftRight");
        if (grandParent.parent != null && grandParent.parent.getRight() == grandParent) {
            grandParent.parent.setRight(child);
        } else if (grandParent.parent != null) {
            grandParent.parent.setLeft(child);
        }
        child.parent = grandParent.parent;
        grandParent.parent = child;
        parent.parent = child;
        if (child.getRight() != null) {
            child.getRight().parent = grandParent;
        }

        grandParent.setColor("red");
        parent.setColor("red");
        child.setColor("black");
        grandParent.setLeft(child.getRight());
        parent.setRight(child.getLeft());
        child.setLeft(parent);
        child.setRight(grandParent);

        return child;
    }

    private RedBlackTreeNode caseRightRight(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        System.out.println("RightRight");
        if (grandParent.parent != null && grandParent.parent.getRight() == grandParent) {
            grandParent.parent.setRight(parent);
        } else if (grandParent.parent != null) {
            grandParent.parent.setLeft(parent);
        }
        parent.parent = grandParent.parent;
        grandParent.parent = parent;
        if (parent.getLeft() != null) {
            parent.getLeft().parent = grandParent;
        }

        grandParent.setColor("red");
        parent.setColor("black");
        grandParent.setRight(parent.getLeft());
        parent.setLeft(grandParent);

        return parent;
    }

    private RedBlackTreeNode caseRightLeft(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        System.out.println("RightLeft");
        if (grandParent.parent != null && grandParent.parent.getRight() == grandParent) {
            grandParent.parent.setRight(child);
        } else if (grandParent.parent != null) {
            grandParent.parent.setLeft(child);
        }
        child.parent = grandParent.parent;
        grandParent.parent = child;
        parent.parent = child;
        if (child.getLeft() != null) {
            child.getLeft().parent = grandParent;
        }

        grandParent.setColor("red");
        parent.setColor("red");
        child.setColor("black");
        grandParent.setRight(child.getLeft());
        parent.setLeft(child.getRight());
        child.setLeft(grandParent);
        child.setRight(parent);

        return child;
    }

    @Override
    public SearchTreeNode remove(int newValue) {
        return null;
    }

    public RedBlackTreeNode getRight() {
        return (RedBlackTreeNode) super.getRight();
    }

    public RedBlackTreeNode getLeft() {
        return (RedBlackTreeNode) super.getLeft();
    }

    public void updateParents(RedBlackTreeNode parent) {
        this.parent = parent;
        if (getLeft() != null) getLeft().updateParents(this);
        if (getRight() != null) getRight().updateParents(this);
    }
}
