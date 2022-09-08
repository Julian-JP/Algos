package com.example.algorithm.Graph;

public class GraphResponse {
    private GraphEdge edges[][];
    private int start, end;

    public GraphResponse(GraphEdge[][] adjazensMatrix, int start, int end) {
        this.edges = adjazensMatrix;
        this.start = start;
        this.end = end;
    }

    public GraphEdge[][] getEdges() {
        return edges;
    }
}
