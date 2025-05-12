package com.example.algorithm.searchTrees;

import com.example.algorithm.SearchTrees.RedBlackTree.RedBlackTree;
import com.example.algorithm.SearchTrees.RedBlackTree.RedBlackTreeNode;
import com.example.algorithm.SearchTrees.RedBlackTree.RedBlackTreeService;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {
    RedBlackTreeService redBlackTreeService = new RedBlackTreeService();

    @Test
    void redUncleInsertionTest() {
        RedBlackTree tree = redBlackTreeService.create(3);

        RedBlackTreeNode left = new RedBlackTreeNode(2, true, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(4, true, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);

        tree.add(1);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void blackUncleLeftLeftInsert() {
        RedBlackTree tree = redBlackTreeService.create(3);

        RedBlackTreeNode left = new RedBlackTreeNode(2, false, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(4, false, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);


        RedBlackTreeNode leftLeft = new RedBlackTreeNode(1, true, left);
        left.setLeft(leftLeft);

        tree.add(0);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void blackUncleLeftRightInsert() {
        RedBlackTree tree = redBlackTreeService.create(3);

        RedBlackTreeNode left = new RedBlackTreeNode(2, false, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(4, false, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);


        RedBlackTreeNode leftLeft = new RedBlackTreeNode(0, true, left);
        left.setLeft(leftLeft);

        tree.add(1);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void blackUncleRightRightInsert() {
        RedBlackTree tree = redBlackTreeService.create(3);

        RedBlackTreeNode left = new RedBlackTreeNode(2, false, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(4, false, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);

        RedBlackTreeNode rightRight = new RedBlackTreeNode(5, true, right);
        right.setRight(rightRight);

        tree.add(6);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void blackUncleRightLeftInsert() {
        RedBlackTree tree = redBlackTreeService.create(3);

        RedBlackTreeNode left = new RedBlackTreeNode(2, false, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(4, false, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);

        RedBlackTreeNode rightRight = new RedBlackTreeNode(6, true, right);
        right.setRight(rightRight);

        tree.add(5);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void removeRedLeaf() {
        RedBlackTree tree = redBlackTreeService.create(3);

        RedBlackTreeNode left = new RedBlackTreeNode(2, false, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(4, false, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);

        RedBlackTreeNode rightRight = new RedBlackTreeNode(5, true, right);
        right.setRight(rightRight);

        tree.remove(5);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void removeBlackLeafWithBlackSiblings() {
        RedBlackTree tree = redBlackTreeService.create(3);

        RedBlackTreeNode left = new RedBlackTreeNode(2, false, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(4, false, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);

        tree.remove(2);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase1() {
        RedBlackTree tree = getExampleTree();

        tree.remove(6);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase2() {
        RedBlackTree tree = getExampleTree();

        tree.remove(1);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase3() {
        RedBlackTree tree = getExampleTree();

        tree.remove(17);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase4() {
        RedBlackTree tree = getExampleTree();

        tree.remove(25);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase5() {
        RedBlackTree tree = getExampleTree2();

        tree.remove(18);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase6() {
        RedBlackTree tree = getExampleTree3();

        tree.remove(2);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase7() {
        RedBlackTree tree = getExampleTree();

        tree.remove(13);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase8() {
        RedBlackTree tree = getExampleTree();

        tree.remove(8);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase9() {
        RedBlackTree tree = getExampleTree2();

        tree.remove(3);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void deleteTestCase10() {
        RedBlackTree tree = getExampleTree();

        tree.remove(11);

        assertTrue(redBlackTreeInvariant(tree.getRoot()));
    }

    @Test
    void addAndRemove1000Nodes() {
        RedBlackTree tree = redBlackTreeService.create(0);
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 1000; i++) {
            numbers.add(i);
        }

        long seed = 42;
        Random random = new Random(seed);

        Collections.shuffle(numbers, random);

        for (int number : numbers) {
            tree.add(number);
            assertTrue(redBlackTreeInvariant(tree.getRoot()));
        }


        Collections.shuffle(numbers, random);
        for (int number : numbers) {
            assertTrue(tree.contains(number));
            tree.remove(number);
            assertTrue(redBlackTreeInvariant(tree.getRoot()));
        }

        assertTrue(tree.getRoot().getLeft().isNil());
        assertTrue(tree.getRoot().getRight().isNil());
        assertEquals(0, tree.getRoot().getValue());

        tree.remove(0);
        assertTrue(tree.getRoot().isNil());
    }

    @Test
    void addInEmptyTree() {
        RedBlackTree tree = redBlackTreeService.create(0);

        tree.remove(0);
        assertTrue(tree.getRoot().isNil());

        tree.add(0);
        assertEquals(0, tree.getRoot().getValue());
    }

    boolean redBlackTreeInvariant(RedBlackTreeNode root) {
        if (root == null) return true;

        if (!redNodeNoChildInvariant(root) || !blackPathLengthInvariant(root)) {
            return false;
        }

        return redBlackTreeInvariant(root.getLeft()) && redBlackTreeInvariant(root.getRight());
    }

    boolean blackPathLengthInvariant(@NotNull RedBlackTreeNode root) {
        if (root.isNil()) {
            return true;
        } else {
            return numberOfBlackNodes(root.getRight()) == numberOfBlackNodes(root.getLeft());
        }
    }

    boolean redNodeNoChildInvariant(@NotNull RedBlackTreeNode root) {
        if (root.getColor().equals("red")) {
            return !((root.getRight() != null && root.getRight().getColor().equals("red")) ||
                    (root.getLeft() != null && root.getLeft().getColor().equals("red")));
        }

        return true;
    }

    int numberOfBlackNodes(@NotNull RedBlackTreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftPath = root.getLeft() == null ? 0 : numberOfBlackNodes(root.getLeft());
        int rightPath = root.getRight() == null ? 0 : numberOfBlackNodes(root.getRight());

        if (root.getColor().equals("black")) {
            return Math.max(leftPath, rightPath) + 1;
        } else {
            return Math.max(leftPath, rightPath);
        }
    }

    private RedBlackTree getExampleTree() {

        RedBlackTree tree = redBlackTreeService.create(13);

        RedBlackTreeNode left = new RedBlackTreeNode(8, true, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(17, true, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);


        RedBlackTreeNode ll = new RedBlackTreeNode(1, false, left);
        RedBlackTreeNode lr = new RedBlackTreeNode(11, false, left);
        RedBlackTreeNode rl = new RedBlackTreeNode(15, false, right);
        RedBlackTreeNode rr = new RedBlackTreeNode(25, false, right);

        left.setLeft(ll);
        left.setRight(lr);
        right.setLeft(rl);
        right.setRight(rr);

        RedBlackTreeNode llr = new RedBlackTreeNode(6, true, ll);
        RedBlackTreeNode rrl = new RedBlackTreeNode(22, true, rr);
        RedBlackTreeNode rrr = new RedBlackTreeNode(27, true, rr);

        ll.setRight(llr);
        rr.setLeft(rrl);
        rr.setRight(rrr);

        return tree;
    }

    private RedBlackTree getExampleTree2() {

        RedBlackTree tree = redBlackTreeService.create(7);

        RedBlackTreeNode left = new RedBlackTreeNode(3, false, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(18, true, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);

        RedBlackTreeNode rl = new RedBlackTreeNode(10, false, right);
        RedBlackTreeNode rr = new RedBlackTreeNode(22, false, right);

        right.setLeft(rl);
        right.setRight(rr);

        RedBlackTreeNode rll = new RedBlackTreeNode(8, true, rl);
        RedBlackTreeNode rlr = new RedBlackTreeNode(11, true, rl);
        RedBlackTreeNode rrr = new RedBlackTreeNode(26, true, rr);

        rl.setLeft(rll);
        rl.setRight(rlr);
        rr.setRight(rrr);

        return tree;
    }

    private RedBlackTree getExampleTree3() {

        RedBlackTree tree = redBlackTreeService.create(5);

        RedBlackTreeNode left = new RedBlackTreeNode(2, true, tree.getRoot());
        RedBlackTreeNode right = new RedBlackTreeNode(8, false, tree.getRoot());
        tree.getRoot().setLeft(left);
        tree.getRoot().setRight(right);

        RedBlackTreeNode ll = new RedBlackTreeNode(1, false, left);
        RedBlackTreeNode lr = new RedBlackTreeNode(4, false, left);
        RedBlackTreeNode rl = new RedBlackTreeNode(7, true, right);
        RedBlackTreeNode rr = new RedBlackTreeNode(9, true, right);

        left.setLeft(ll);
        left.setRight(lr);
        right.setLeft(rl);
        right.setRight(rr);

        return tree;
    }
}
