package com.example.algorithm.Graph;

public class GraphEdge {
    private String VISITED_COLOR = "blue";
    private String FINAL_COLOR = "red";
    private String color;

    public GraphEdge(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
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
