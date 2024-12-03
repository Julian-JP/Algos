package com.example.algorithm.Graph;

import lombok.Getter;
import lombok.Setter;

public class GraphEdge {
    public static final int UNVISITED = 0;
    public static final int VISITED = 1;
    public static final int ON_FINAL_PATH = 2;
    public static final int PROCESSED = 3;

    @Getter
    private int marking;
    @Getter
    private Double weight;
    @Getter
    private String id;

    public GraphEdge(int marking, Double weight, String id) {
        this.marking = marking;
        this.weight = weight;
    }

    public void visit() {
        marking = VISITED;
    }

    public void process() {
        marking = PROCESSED;
    }

    public void finish() {
        marking = ON_FINAL_PATH;
    }
}
