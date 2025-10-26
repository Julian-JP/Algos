package com.example.algorithm.searchTrees;

import com.example.algorithm.SearchTrees.SearchTree;
import com.example.algorithm.SearchTrees.SearchTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTreeTestUtils {
    static <SearchTreeNodeT extends SearchTreeNode> void addAndRemove1000Nodes(SearchTree tree, Predicate<SearchTreeNodeT> check) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 1000; i++) {
            numbers.add(i);
        }

        long seed = 42;
        Random random = new Random(seed);

        Collections.shuffle(numbers, random);

        for (int number : numbers) {
            tree.add(number);
            assertTrue(check.test((SearchTreeNodeT) tree.getRoot()));
        }


        Collections.shuffle(numbers, random);
        for (int number : numbers) {
            assertTrue(tree.contains(number));
            tree.remove(number);
            assertTrue(check.test((SearchTreeNodeT) tree.getRoot()));
        }

        assertNull(tree.getRoot().getLeft());
        assertNull(tree.getRoot().getRight());
        assertEquals(0, tree.getRoot().getValue());
    }
}
