package com.example.algorithm.searchTrees;

import com.example.algorithm.SearchTrees.BinarySearchTree.BinarySearchTree;
import com.example.algorithm.SearchTrees.BinarySearchTree.BinarySearchTreeService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest {
    BinarySearchTreeService binarySearchTreeService = new BinarySearchTreeService();

    @Test
    void create1DeepTree() {
        BinarySearchTree tree = binarySearchTreeService.create(0);

        assertNull(tree.getRoot().getLeft());
        assertEquals(0, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());

    }

    @Test
    void addLeftNode() {
        BinarySearchTree tree = binarySearchTreeService.create(1);
        tree.add(0);

        assertEquals(0, tree.getRoot().getLeft().getValue());
        assertEquals(1, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());
    }

    @Test
    void addRightNode() {
        BinarySearchTree tree = binarySearchTreeService.create(0);
        tree.add(1);

        assertNull(tree.getRoot().getLeft());
        assertEquals(0, tree.getRoot().getValue());
        assertEquals(1, tree.getRoot().getRight().getValue());
    }

    @Test
    void removeChildNode() {
        BinarySearchTree tree = binarySearchTreeService.create(1);
        tree.add(0);
        tree.remove(0);

        assertNull(tree.getRoot().getLeft());
        assertEquals(1, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());


        tree.add(2);
        tree.remove(2);

        assertNull(tree.getRoot().getLeft());
        assertEquals(1, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());
    }

    @Test
    void removeRootNode() {
        BinarySearchTree tree = binarySearchTreeService.create(1);
        tree.add(0);
        tree.add(2);

        tree.remove(1);


         assertEquals(0, tree.getRoot().getLeft().getValue());
        assertEquals(2, tree.getRoot().getValue());
        assertNull(tree.getRoot().getRight());

        assertNull(tree.getRoot().getLeft().getLeft());
        assertNull(tree.getRoot().getLeft().getRight());
    }

    @Test
    void addAndRemove1000Nodes() {
        BinarySearchTree tree = binarySearchTreeService.create(0);
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 1000; i++) {
            numbers.add(i);
        }

        long seed = 42;
        Random random = new Random(seed);

        Collections.shuffle(numbers, random);

        for (int number : numbers) {
            tree.add(number);
        }


        Collections.shuffle(numbers, random);
        for (int number : numbers) {
            assertTrue(tree.contains(number));
            tree.remove(number);
        }

        assertNull(tree.getRoot().getLeft());
        assertNull(tree.getRoot().getRight());
        assertEquals(0, tree.getRoot().getValue());
    }

    @Test
    void addInEmptyTree() {
        BinarySearchTree tree = binarySearchTreeService.create(0);
        tree.remove(0);
        assertNull(tree.getRoot());
        tree.add(0);
        assertEquals(0, tree.getRoot().getValue());
    }
}
