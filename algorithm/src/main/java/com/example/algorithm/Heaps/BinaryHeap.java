package com.example.algorithm.Heaps;

import java.util.ArrayList;

public class BinaryHeap {

    private ArrayList<Integer> heap;
    private BinaryHeapNode root;


    public BinaryHeap(BinaryHeapNode root) {
        heap = new ArrayList<>();
        fromHeapObj(root);
    }

    public void add(int newValue) {
        heap.add(newValue);
    }

    public Integer getMinimum() {
        if (heap.size() != 0) {
            int lowestValue = heap.get(0);
            if (heap.size() == 1) {
                heap.clear();
            } else {
                heap.set(0, heap.remove(heap.size()-1));
                siftDown(0);
            }
            return lowestValue;
        } else {
            return null;
        }
    }

    public BinaryHeapNode getRoot() {
        return toHeapObj(0);
    }

    private BinaryHeapNode toHeapObj(int index) {
        if (index < heap.size()) {
            return new BinaryHeapNode(heap.get(index), toHeapObj(leftChildrenIndex(index)), toHeapObj(rightChildrenIndex(index)));
        } else {
            return null;
        }
    }

    private void fromHeapObj(BinaryHeapNode root) {
        ArrayList<BinaryHeapNode> layerCurrent = new ArrayList<>();
        ArrayList<BinaryHeapNode> layerNext = new ArrayList<>();
        heap.clear();
        layerCurrent.add(root);
        while (!layerCurrent.isEmpty()) {
            for (BinaryHeapNode node : layerCurrent) {
                heap.add(node.getValue());
                if (node.getLeft() != null) {
                    layerNext.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    layerNext.add(node.getRight());
                }
            }
            ArrayList<BinaryHeapNode> temp = layerCurrent;
            layerCurrent = layerNext;
            layerNext = temp;
            layerNext.clear();
        }
    }

    private void siftUp(int index) {
       // while (index != 0 && )
    }

    private void siftDown(int index) {
        int smallestChild = smallestDirectChildren(index);

        if (isLeaf(index)) {
            return;
        }

        if (heap.get(smallestChild) < heap.get(index)) {
            int temp = heap.get(index);
            heap.set(index, heap.get(smallestChild));
            heap.set(smallestChild, heap.get(index));
            siftDown(smallestChild);
        }
    }

    private boolean isLeaf(int index) {
        return ((index + 1) * 2) - 1 < heap.size();
    }

    private int smallestDirectChildren(int index) {
        int leftChild = leftChildrenIndex(index);
        int rightChild = rightChildrenIndex(index);

        if (leftChild >= heap.size()) {
            return rightChild;
        } else if (rightChild >= heap.size()) {
            return leftChild;
        } else if (heap.get(leftChild) < heap.get(rightChild)) {
            return leftChild;
        } else {
            return rightChild;
        }
    }

    private int rightChildrenIndex(int index) {
        return ((index + 1)*2);
    }

    private int leftChildrenIndex(int index) {
        return ((index + 1)*2) - 1;
    }

    private int calcParentIndex(int index) {
        return ((index + 1) / 2) - 1;
    }


}