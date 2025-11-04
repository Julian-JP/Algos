package com.example.algorithm.Heaps.PairingHeap;

import lombok.Getter;

@Getter
public class PairingHeap {
    private PairingHeapNode root;

    public PairingHeap() {}

    public PairingHeap(PairingHeapNode root) {
        this.root = root;
    }

    public void add(int val) {
        root = PairingHeapNode.merge(new PairingHeapNode(val), root);
    }

    public Integer getMinimum() {
        if (root == null) {
            return null;
        }

        int ret = root.getVal();

        root = PairingHeapNode.merge(root.getChildren());
        return ret;
    }
}
