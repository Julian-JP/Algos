package com.example.algorithm.Heaps.PairingHeap;

import lombok.Getter;

import java.util.*;

@Getter
public class PairingHeapNode {
    private List<PairingHeapNode> children;
    private int val;

    public PairingHeapNode(int val) {
        children = new ArrayList<>();
        this.val = val;
    }

    public PairingHeapNode(List<PairingHeapNode> children, int val) {
        this.children = children;
        this.val = val;
    }

    public static PairingHeapNode merge(PairingHeapNode node1, PairingHeapNode node2) {
        if (node1 == null) {
            return node2;
        }
        if (node2 == null) {
            return node1;
        }

        if (node1.val < node2.val) {
            node1.children.add(node2);
            return node1;
        } else {
            node2.children.add(node1);
            return node2;
        }

    }

    public static PairingHeapNode merge(List<PairingHeapNode> nodes) {
        Deque<PairingHeapNode> fifo = new ArrayDeque<>();
        for (int i=1; i < nodes.size(); i+=2) {
            fifo.addLast(merge(nodes.get(i), nodes.get(i-1)));
        }
        if (nodes.size() % 2 != 0) {
            fifo.addLast(nodes.getLast());
        }

        while (fifo.size() > 1) {
            PairingHeapNode first = fifo.pollFirst();
            PairingHeapNode second = fifo.pollFirst();

            PairingHeapNode merged = merge(first, second);
            fifo.addLast(merged);
        }
        return  fifo.pollFirst();
    }
}
