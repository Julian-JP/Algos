package com.example.algorithm.SearchTrees;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class SearchTreeNode {
    private Integer value;
    private SearchTreeNode left;
    private SearchTreeNode right;
    private String color = "white";

    public SearchTreeNode(Integer value) {
        this.value = value;
    }
    public SearchTreeNode(Integer value, SearchTreeNode left, SearchTreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public abstract SearchTreeNode add(Integer newValue);

    public abstract SearchTreeNode remove(Integer newValue);

    public abstract boolean contains(Integer value);

    public int getHeight() {
        int maxHeight = 1;
        if (left != null) {
            maxHeight = Math.max(left.getHeight() + 1, maxHeight);
        }
        if (right != null) {
            maxHeight = Math.max(right.getHeight() + 1, maxHeight);
        }
        return maxHeight;
    }
}
