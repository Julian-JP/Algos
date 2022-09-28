package com.example.algorithm.SearchTrees.RedBlackTree;

import com.example.algorithm.SearchTrees.SearchTreeNode;

public class RedBlackTreeNode extends SearchTreeNode {
    private RedBlackTreeNode parent;
    private boolean isNilNode = false;

    public RedBlackTreeNode(Integer value, boolean isRed, RedBlackTreeNode parent) {
        super(value);
        setColor(isRed ? "red" : "black");
        this.parent = parent;
        this.isNilNode = false;
        super.setLeft(new RedBlackTreeNode(this));
        super.setRight(new RedBlackTreeNode(this));
    }

    public RedBlackTreeNode(Integer value, RedBlackTreeNode left, RedBlackTreeNode right, String color, RedBlackTreeNode parent) {
        super(value, left, right);
        setColor(color);
        this.parent = parent;
        this.isNilNode = false;
    }

    public RedBlackTreeNode(RedBlackTreeNode parent) {
        super(null, null, null);
        setColor("black");
        this.parent = parent;
        this.isNilNode = true;
    }

    @Override
    public RedBlackTreeNode add(Integer newValue) {
        RedBlackTreeNode root = this;
        RedBlackTreeNode temp = addRec(newValue);
        if (temp == null) return root;
        else return fixTreeInsert(temp, root);
    }

    public RedBlackTreeNode addRec(int newValue) {
        if (getValue() > newValue) {
            if (getLeft().isNilNode) {
                setLeft(new RedBlackTreeNode(newValue, true, this));
                return getLeft();
            } else {
                return getLeft().addRec(newValue);
            }
        } else if (getValue() < newValue) {
            if (getRight().isNilNode) {
                setRight(new RedBlackTreeNode(newValue, true, this));
                return getRight();
            } else {
                return getRight().addRec(newValue);
            }
        }
        return null;
    }

    public RedBlackTreeNode fixTreeInsert(RedBlackTreeNode child, RedBlackTreeNode root) {
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
            RedBlackTreeNode temp = fixTreeInsert(grandParent, root);
            temp.setColor("black");
            return temp;
        } else if (grandParent == root) {
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
        else if (grandParent.getLeft() == parent && parent.getLeft() == child)
            return caseLeftLeft(grandParent, parent, uncle, child);
        else if (grandParent.getLeft() == parent && parent.getRight() == child)
            return caseLeftRight(grandParent, parent, uncle, child);
        else if (grandParent.getRight() == parent && parent.getRight() == child)
            return caseRightRight(grandParent, parent, uncle, child);
        else if (grandParent.getRight() == parent && parent.getLeft() == child)
            return caseRightLeft(grandParent, parent, uncle, child);

        return null;
    }

    private RedBlackTreeNode caseBlackParent(RedBlackTreeNode grandParent, RedBlackTreeNode child) {
        child.setColor("red");
        return grandParent;
    }

    private RedBlackTreeNode caseRedUncle(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle) {
        parent.setColor("black");
        uncle.setColor("black");
        grandParent.setColor("red");
        return grandParent;
    }

    private RedBlackTreeNode caseLeftLeft(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        linkWithGrandParentsParent(grandParent, parent);

        child.parent = parent;
        parent.parent = grandParent.parent;
        grandParent.parent = parent;
        if (!parent.getRight().isNilNode) {
            parent.getRight().parent = grandParent;
        }

        grandParent.setColor("red");
        parent.setColor("black");
        grandParent.setLeft(parent.getRight());
        parent.setRight(grandParent);

        return parent;
    }

    private RedBlackTreeNode caseLeftRight(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        linkWithGrandParentsParent(grandParent, child);

        child.parent = grandParent.parent;
        grandParent.parent = child;
        parent.parent = child;
        if (!child.getRight().isNilNode) {
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
        linkWithGrandParentsParent(grandParent, parent);

        parent.parent = grandParent.parent;
        grandParent.parent = parent;
        if (!parent.getLeft().isNilNode) {
            parent.getLeft().parent = grandParent;
        }

        grandParent.setColor("red");
        parent.setColor("black");
        grandParent.setRight(parent.getLeft());
        parent.setLeft(grandParent);

        return parent;
    }

    private RedBlackTreeNode caseRightLeft(RedBlackTreeNode grandParent, RedBlackTreeNode parent, RedBlackTreeNode uncle, RedBlackTreeNode child) {
        linkWithGrandParentsParent(grandParent, child);

        child.parent = grandParent.parent;
        grandParent.parent = child;
        parent.parent = child;
        if (!child.getLeft().isNilNode) {
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

    private void linkWithGrandParentsParent(RedBlackTreeNode grandParent, RedBlackTreeNode nodeToLink) {
        if (grandParent.parent != null && grandParent.parent.getRight() == grandParent) {
            grandParent.parent.setRight(nodeToLink);
        } else if (grandParent.parent != null) {
            grandParent.parent.setLeft(nodeToLink);
        }
    }

    @Override
    public SearchTreeNode remove(Integer newValue) {
        RedBlackTreeNode x;
        RedBlackTreeNode deletedNode = findNode(newValue);
        RedBlackTreeNode replacement;
        RedBlackTreeNode root = this;

        if (deletedNode == null) {
            return this;
        }

        //Step 1
        if (deletedNode.getRight().isNilNode && deletedNode.getLeft().isNilNode) {
            x = deletedNode.getRight();
            replacement = deletedNode.getRight();
        } else if (!deletedNode.getRight().isNilNode && !deletedNode.getLeft().isNilNode) {
            replacement = deletedNode.getRight().findNodeWithSmallestValueInSubTree();
            x = replacement.getRight();
        } else if (!deletedNode.getRight().isNilNode) {
            x = deletedNode.getRight();
            replacement = deletedNode.getRight();
        } else {
            x = deletedNode.getLeft();
            replacement = deletedNode.getLeft();
        }

        System.out.println("Replacement: " + replacement.getValue());
        System.out.println("x: " + x.getValue());
        System.out.println("w: " + getSibling(x).getValue());
        root = spliceOut(deletedNode, replacement, x, root);

        //Step 2
        //If deletedNode == red and replacement red or NIL nothing to do
        //If deletedNode == black and replacement is black and x is the root node nothing to do
        if (isRed(deletedNode) && isBlackNotNIL(replacement)) {
            replacement.setColor("red");
            root = fixTreeDelete(deletedNode, replacement, x, root);

        } else if (isBlack(deletedNode) && isRed(replacement)) {
            replacement.setColor("black");

        } else if (isBlack(deletedNode) && x != root && isBlack(replacement)) {
            root = fixTreeDelete(deletedNode, replacement, x, root);

        }

        return root;
    }

    private RedBlackTreeNode spliceOut(RedBlackTreeNode node, RedBlackTreeNode replacement, RedBlackTreeNode x, RedBlackTreeNode root) {
        //Splice out Replacement
        if (replacement != x) {
            if (replacement.parent.getRight() == replacement) {
                replacement.parent.setRight(x);
            } else {
                replacement.parent.setLeft(x);
            }
            x.parent = replacement.parent;

            if (!replacement.isNilNode && replacement.getRight() != x) {
                replacement.getRight().parent = x;
                x.setRight(replacement.getRight());
            }
            if (!replacement.isNilNode && replacement.getLeft() != x) {
                replacement.getLeft().parent = x;
                x.setLeft(replacement.getLeft());
            }
        }

        //Change node with replacement
        if (node.parent != null && node.parent.getRight() == node) {
            node.parent.setRight(replacement);
        } else if (node.parent != null && node.parent.getLeft() == node) {
            node.parent.setLeft(replacement);
        }

        replacement.parent = node.parent;
        if (!replacement.isNilNode) {
            replacement.setRight(node.getRight() != replacement ? node.getRight() : replacement.getRight());
            replacement.setLeft(node.getLeft() != replacement ? node.getLeft() : replacement.getLeft());
            replacement.getLeft().parent = replacement;
            replacement.getRight().parent = replacement;
        }

        if (node == root) {
            return replacement;
        } else {
            return root;
        }
    }

    private RedBlackTreeNode fixTreeDelete(RedBlackTreeNode deletedNode, RedBlackTreeNode replacement, RedBlackTreeNode x, RedBlackTreeNode root) {

        RedBlackTreeNode xSibling = getSibling(x);

        if (isRed(x)) {
            //Case 0
            root = case0(x, root);
        } else if (isBlack(x) && isRed(xSibling)) {
            //Case 1
            root = case1(deletedNode, replacement, x, root);
        } else if (isBlack(x) && isBlackNotNIL(xSibling) && isBlack(xSibling.getLeft()) && isBlack(xSibling.getRight())) {
            //Case 2
            root = case2(deletedNode, replacement, x, root);
        } else if (isBlack(x) && isBlackNotNIL(xSibling) && xSibling.parent.getLeft() == x && isRed(xSibling.getLeft()) && isBlack(xSibling.getRight())) {
            //Case 3a
            root = case3a(deletedNode, replacement, x, root);
        } else if (isBlack(x) && isBlackNotNIL(xSibling) && xSibling.parent.getRight() == x && isRed(xSibling.getRight()) && isBlack(xSibling.getLeft())) {
            //case 3b
            root = case3b(deletedNode, replacement, x, root);
        } else if (isBlack(x) && isBlackNotNIL(xSibling) && xSibling.parent.getLeft() == x && isRed(xSibling.getRight())) {
            //Case 4a
            root = case4a(x, root);
        } else if (isBlack(x) && isBlackNotNIL(xSibling) && xSibling.parent.getRight() == x && isRed(xSibling.getLeft())) {
            //case 4b
            root = case4b(x, root);
        } else {
            return null;
        }

        return root;
    }

    private RedBlackTreeNode case0(RedBlackTreeNode x, RedBlackTreeNode root) {
        System.out.println("Case 0");
        x.setColor("black");
        return root;
    }

    private RedBlackTreeNode case1(RedBlackTreeNode deletedNode, RedBlackTreeNode replacement, RedBlackTreeNode x, RedBlackTreeNode root) {
        System.out.println("Case 1");
        RedBlackTreeNode xSibling = getSibling(x);

        xSibling.setColor("black");
        x.parent.setColor("red");

        RedBlackTreeNode subtreeRootAfterRotation;
        if (x.parent.getRight() == x) {
            subtreeRootAfterRotation = rotateRight(x.parent);
        } else {
            subtreeRootAfterRotation = rotateLeft(x.parent);
        }

        if (x.parent == root) {
            root = subtreeRootAfterRotation;
        }

        return fixTreeDelete(deletedNode, replacement, x, root);
    }

    private RedBlackTreeNode case2(RedBlackTreeNode deletedNode, RedBlackTreeNode replacement, RedBlackTreeNode x, RedBlackTreeNode root) {
        System.out.println("Case 2");
        RedBlackTreeNode xSibling = getSibling(x);

        xSibling.setColor("red");
        x = xSibling.parent;

        //If new X is black and the root of the tree we are done
        if (isRed(x)) {
            x.setColor("black");

        } else if (isBlack(x) && x != this) {
            return fixTreeDelete(deletedNode, replacement, x, root);
        }
        return root;
    }

    private RedBlackTreeNode case3a(RedBlackTreeNode deletedNode, RedBlackTreeNode replacement, RedBlackTreeNode x, RedBlackTreeNode root) {
        System.out.println("Case 3a");
        RedBlackTreeNode xSibling = getSibling(x);

        xSibling.getLeft().setColor("black");
        xSibling.setColor("red");
        rotateRight(xSibling);


        if (x.parent.getRight() == x) {
            return case4b(x, root);
        } else {
            return case4a(x, root);
        }
    }

    private RedBlackTreeNode case3b(RedBlackTreeNode deletedNode, RedBlackTreeNode replacement, RedBlackTreeNode x, RedBlackTreeNode root) {
        System.out.println("Case 3b");
        RedBlackTreeNode xSibling = getSibling(x);

        xSibling.getRight().setColor("black");
        xSibling.setColor("red");
        rotateLeft(xSibling);


        if (x.parent.getRight() == x) {
            return case4b(x, root);
        } else {
            return case4a(x, root);
        }

    }

    private RedBlackTreeNode case4a(RedBlackTreeNode x, RedBlackTreeNode root) {
        System.out.println("Case 4a");
        RedBlackTreeNode xSibling = getSibling(x);
        RedBlackTreeNode subtreeRootAfterRotation;

        xSibling.setColor(x.parent.getColor());
        x.parent.setColor("black");
        xSibling.getRight().setColor("black");
        subtreeRootAfterRotation = rotateLeft(x.parent);

        if (x.parent == root) {
            return subtreeRootAfterRotation;
        }
        return root;
    }

    private RedBlackTreeNode case4b(RedBlackTreeNode x, RedBlackTreeNode root) {
        System.out.println("Case 4b");
        RedBlackTreeNode xSibling = getSibling(x);
        RedBlackTreeNode subtreeRootAfterRotation;

        xSibling.setColor(x.parent.getColor());
        x.parent.setColor("black");
        xSibling.getLeft().setColor("black");
        subtreeRootAfterRotation = rotateRight(x.parent);

        if (x.parent == root) {
            return subtreeRootAfterRotation;
        }
        return root;
    }

    private RedBlackTreeNode rotateLeft(RedBlackTreeNode node) {
        RedBlackTreeNode rightChild = node.getRight();//Niemals null da sonst keine rotation möglich wäre
        RedBlackTreeNode possiblerightLeftChild = rightChild.getLeft();

        //Set Children
        if (node.parent != null && node.parent.getLeft() == node) {
            node.parent.setLeft(rightChild);
        } else if (node.parent != null && node.parent.getRight() == node) {
            node.parent.setRight(rightChild);
        }

        rightChild.setLeft(node);
        node.setRight(possiblerightLeftChild);

        //Set Parents
        rightChild.parent = node.parent;
        node.parent = rightChild;
        if (possiblerightLeftChild != null) {
            possiblerightLeftChild.parent = node;
        }


        return rightChild;

    }

    private RedBlackTreeNode rotateRight(RedBlackTreeNode node) {
        RedBlackTreeNode leftChild = node.getLeft();//Niemals null da sonst keine rotation möglich wäre
        RedBlackTreeNode possibleLeftRightChild = leftChild.getRight();

        //set Children
        if (node.parent != null && node.parent.getLeft() == node) {
            node.parent.setLeft(leftChild);
        } else if (node.parent != null && node.parent.getRight() == node) {
            node.parent.setRight(leftChild);
        }

        leftChild.setRight(node);
        node.setLeft(possibleLeftRightChild);

        //Set Parents
        leftChild.parent = node.parent;
        node.parent = leftChild;
        if (possibleLeftRightChild != null) {
            possibleLeftRightChild.parent = node;
        }

        return leftChild;
    }

    private static boolean isRed(RedBlackTreeNode node) {
        return node != null && node.getColor().equals("red");
    }

    private static boolean isBlack(RedBlackTreeNode node) {
        return node.getColor().equals("black");
    }

    private static boolean isBlackNotNIL(RedBlackTreeNode node) {
        return !node.isNilNode && node.getColor().equals("black");
    }

    private RedBlackTreeNode findNode(int value) {
        if (value == getValue()) {
            return this;
        } else if (value < getValue()) {
            if (getLeft().isNilNode) return null;
            else return getLeft().findNode(value);
        } else {
            if (getRight().isNilNode) return null;
            else return getRight().findNode(value);
        }
    }

    private RedBlackTreeNode getSibling(RedBlackTreeNode node) {
        if (node != null && node.parent != null) {
            return node.parent.getRight() == node ? node.parent.getLeft() : node.parent.getRight();
        }
        return null;
    }

    private RedBlackTreeNode findNodeWithSmallestValueInSubTree() {
        if (getLeft().isNilNode) {
            return this;
        } else {
            return getLeft().findNodeWithSmallestValueInSubTree();
        }
    }

    private RedBlackTreeNode findNodeWithBiggestValueInSubTree() {
        if (getRight().isNilNode) {
            return this;
        } else {
            return getRight().findNodeWithBiggestValueInSubTree();
        }
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


    public void setRight(RedBlackTreeNode right) {
        if (!isNilNode) super.setRight(right);
    }

    public void setLeft(RedBlackTreeNode left) {
        if (!isNilNode) super.setLeft(left);
    }
}
