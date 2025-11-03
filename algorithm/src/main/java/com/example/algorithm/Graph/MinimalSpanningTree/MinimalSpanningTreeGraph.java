package com.example.algorithm.Graph.MinimalSpanningTree;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

public class MinimalSpanningTreeGraph extends Graph {
    @Getter
    private final int start;

    public MinimalSpanningTreeGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        start = graph.getInt("start");
    }

    public boolean containsEdge(int from, int to) {
        return adjacencyMatrix[from][to] != null || adjacencyMatrix[to][from] != null;
    }

    public GraphEdge getEdge(int from, int to) {
        if (adjacencyMatrix[from][to] != null) {
            return adjacencyMatrix[from][to];
        } else {
            return adjacencyMatrix[to][from];
        }
    }
}
