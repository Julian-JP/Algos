package com.example.algorithm.Graph;

public class GraphEdge {
    private final String VISITED_COLOR = "blue";
    private final String FINAL_COLOR = "red";
    private String color;

    public GraphEdge(String color) {
        this.color = color;
    }

    public boolean isVisited() {
        if (color.equals(VISITED_COLOR)) {
            return true;
        } else {
            color = VISITED_COLOR;
            return false;
        }
    }

    public void finish() {
        color = FINAL_COLOR;
    }
}
