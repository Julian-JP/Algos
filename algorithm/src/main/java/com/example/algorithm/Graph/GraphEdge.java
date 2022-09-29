package com.example.algorithm.Graph;

public class GraphEdge {
    private final String VISITED_COLOR = "blue";
    private final String FINAL_COLOR = "red";
    private final String UNVISITED_COLOR = "black";
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

    public boolean isVisited() {
        if (color.equals(UNVISITED_COLOR) == false) {
            return true;
        } else {
            color = VISITED_COLOR;
            return false;
        }
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
}
