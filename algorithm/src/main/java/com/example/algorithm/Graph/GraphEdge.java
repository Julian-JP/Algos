package com.example.algorithm.Graph;

import lombok.Getter;
import lombok.Setter;

public class GraphEdge {
    public enum Marking {
        UNVISITED(0),
        VISITED(1),
        ON_FINAL_PATH(2),
        PROCESSED(3);

        private final int value;

        Marking(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Getter
    private Marking marking;
    @Getter
    private Double weight;

    public GraphEdge(Marking marking, Double weight) {
        this.marking = marking;
        this.weight = weight;
    }

    public void visit() {
        marking = Marking.VISITED;
    }

    public void process() {
        marking = Marking.PROCESSED;
    }

    public void finish() {
        marking = Marking.ON_FINAL_PATH;
    }
}
