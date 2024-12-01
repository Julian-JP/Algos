package com.example.algorithm.Graph;

import lombok.Getter;
import lombok.Setter;

public class GraphNode {
    @Getter
    @Setter
    Double weight;

    @Getter
    @Setter
    String value;

    public GraphNode(String value, Double weight) {
        this.value = value;
        this.weight = weight;
    }
}
