package com.example.algorithm.Graph;

import lombok.Setter;

public class GraphEdge {
    public static final int UNVISITED = 0;
    public static final int VISITED = 1;
    public static final int ON_FINAL_PATH = 2;
    public static final int PROCESSED = 3;

    @Setter
    private int marking;
    private Double weight;

    public GraphEdge(int marking) {
        this.marking = marking;
        this.weight = null;
    }

    public GraphEdge(int marking, double weight) {
        this.marking = marking;
        this.weight = Double.valueOf(weight);
    }

    public boolean tryToVisit() {
        if (marking != VISITED) {
            marking = VISITED;
            return false;
        } else {
            return true;
        }
    }

    public boolean tryToProcess() {
        if (marking == VISITED) {
            marking = PROCESSED;
            return true;
        }
            return false;
    }
    public void finish() {
        marking = ON_FINAL_PATH;
    }

    public int getMarking() {
        return marking;
    }

    public Double getWeight() {
        return weight;
    }
}
