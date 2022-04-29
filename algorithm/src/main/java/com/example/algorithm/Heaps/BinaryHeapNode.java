package com.example.algorithm.Heaps;

public class BinaryHeapNode {

    private int value;
    private BinaryHeapNode left, right;
    public BinaryHeapNode(int value, BinaryHeapNode left, BinaryHeapNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public BinaryHeapNode getLeft() {
        return left;
    }

    public BinaryHeapNode getRight() {
        return right;
    }
}
