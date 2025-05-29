package com.example.algorithm.ResponseTypes;

import lombok.Getter;

import java.util.List;

@Getter
public class TreeResponse {
    private final TreeNodeResponse root;

    public TreeResponse(TreeNodeResponse root) {
        this.root = root;
    }
}

