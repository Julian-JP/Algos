package com.example.algorithm.heaps;

import com.example.algorithm.Heaps.BinaryHeap.BinaryHeap;
import com.example.algorithm.Heaps.BinaryHeap.BinaryHeapNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryHeapTest {

    @Test
    void addAndRemove1000Nodes() {
        BinaryHeap heap = new BinaryHeap(new BinaryHeapNode(0, null, null));

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 1000; i++) {
            numbers.add(i);
        }

        long seed = 42;
        Random random = new Random(seed);

        Collections.shuffle(numbers, random);

        for (Integer number : numbers) {
            heap.add(number);
            assertTrue(heapInvariant(heap));
        }

        for (int i=0; i <= numbers.size(); i++) {
            assertEquals(i, heap.getMinimum());
        }

        assertNull(heap.getRoot());
    }

    boolean heapInvariant(@NotNull BinaryHeap heap) {
        int height = getHeight(heap.getRoot());

        return completeBinaryTreeInvariant(heap.getRoot(), height, 0) && heapPropertyInvariant(heap.getRoot());
    }

    boolean completeBinaryTreeInvariant(@Nullable BinaryHeapNode root, int treeHeight, int currentLevel) {
        if (root == null) return Math.abs(treeHeight - currentLevel) <= 1;

        return completeBinaryTreeInvariant(root.getLeft(), treeHeight, currentLevel + 1) &&
                completeBinaryTreeInvariant(root.getRight(), treeHeight, currentLevel + 1);
    }

    int getHeight(@Nullable BinaryHeapNode root) {
        if (root == null) return 0;

        return 1 + Math.max(getHeight(root.getLeft()), getHeight(root.getRight()));
    }

    boolean heapPropertyInvariant(@NotNull  BinaryHeapNode heap) {
        boolean result = true;

        if (heap.getLeft() != null) {
            if (heap.getLeft().getValue() < heap.getValue()) {
                return false;
            } else {
                result &= heapPropertyInvariant(heap.getLeft());
            }
        }
        if (heap.getRight() != null) {
            if (heap.getRight().getValue() < heap.getValue()) {
                return false;
            } else {
                result &= heapPropertyInvariant(heap.getRight());
            }
        }

        return result;
    }
}
