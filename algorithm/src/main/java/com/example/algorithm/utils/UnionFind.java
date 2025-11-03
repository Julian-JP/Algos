package com.example.algorithm.utils;

import java.util.Arrays;

public class UnionFind {
    private final int[] parent;

    public UnionFind(int size) {
        parent = new int[size];
        Arrays.fill(parent, -1);
    }

    public int find(int p) {
        assert p >= 0 && p < parent.length;

        if (parent[p] == -1) {
            return p;
        }

        parent[p] = find(parent[p]);
        return parent[p];
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot != qRoot) {
            parent[pRoot] = qRoot;
        }
    }
}
