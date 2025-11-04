package com.example.algorithm.heaps;


import com.example.algorithm.Heaps.PairingHeap.PairingHeap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PairingHeapTest {
    @Test
    void addAndRemove1000Nodes() {
        PairingHeap heap = new PairingHeap();
        heap.add(0);

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < 1000; i++) {
            numbers.add(i);
        }

        long seed = 42;
        Random random = new Random(seed);

        Collections.shuffle(numbers, random);

        for (Integer number : numbers) {
            heap.add(number);
        }

        for (int i=0; i <= numbers.size(); i++) {
            assertEquals(i, heap.getMinimum());
        }

        assertNull(heap.getMinimum());
    }
}
