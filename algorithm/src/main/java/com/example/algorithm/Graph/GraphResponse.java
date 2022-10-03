package com.example.algorithm.Graph;

public class GraphResponse {
    private GraphEdge[][] edges;
    private GraphNode[] vertices;

    public GraphResponse(GraphEdge[][] adjacencyMatrix) {
        this.edges = adjacencyMatrix;
    }
    public GraphResponse(GraphEdge[][] adjacencyMatrix, GraphNode[] nodes) {
        this.edges = adjacencyMatrix;
        this.vertices = nodes;
    }

    public GraphEdge[][] getEdges() {
        return edges;
    }

    public GraphNode[] getVertices() {
        return vertices;
    }
}
