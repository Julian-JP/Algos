package com.example.algorithm.ResponseTypes;

import lombok.Getter;

import java.util.List;

@Getter
public class TreeNodeResponse {
    private final static String DEFAULT_COLOR = "white";

    private List<TreeNodeResponse> children;
    private final String color;
    private final String value;

    public TreeNodeResponse(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public TreeNodeResponse(String value) {
        this.color = DEFAULT_COLOR;
        this.value = value;
    }

    public TreeNodeResponse(List<TreeNodeResponse> children, String value) {
        this.children = children;
        this.color = DEFAULT_COLOR;
        this.value = value;
    }

    public TreeNodeResponse(List<TreeNodeResponse> children, String color, String value) {
        this.children = children;
        this.color = color;
        this.value = value;
    }
}
