package com.example.algorithm.Graph;

public class GraphResponse {
    private GraphEdge[][] edges;

    public GraphResponse(GraphEdge[][] adjacencyMatrix) {
        this.edges = adjacencyMatrix;
    }

    public GraphEdge[][] getEdges() {
        return edges;
    }
}
