package com.example.algorithm.Graph;

public class GraphEdge {
    private static final String VISITED_COLOR = "blue";
    private static final String FINAL_COLOR = "red";
    private static final String UNVISITED_COLOR = "black";
    private static final String PROCESSED_COLOR = "gray";
    private String color;
    private Double weight;

    public GraphEdge(String color) {
        this.color = color;
        this.weight = null;
    }
    public GraphEdge(String color, Double weight) {
        this.color = color;
        this.weight = weight;
    }

    public boolean tryToVisit() {
        if (!color.equals(VISITED_COLOR)) {
            color = VISITED_COLOR;
            return false;
        } else {
            return true;
        }
    }

    public boolean tryToProcess() {
        if (color.equals(VISITED_COLOR)) {
            color = PROCESSED_COLOR;
            return true;
        }
            return false;
    }
    public void finish() {
        color = FINAL_COLOR;
    }

    public String getColor() {
        return color;
    }

    public Double getWeight() {
        return weight;
    }

    public boolean isVisited() {
        return color.equals(VISITED_COLOR);
    }

    public boolean isProcessed() {
        return color.equals(PROCESSED_COLOR);
    }

    public void setProcessed() {
        color = PROCESSED_COLOR;
    }

    public void setUnvisited() {
        color = UNVISITED_COLOR;
    }
}
