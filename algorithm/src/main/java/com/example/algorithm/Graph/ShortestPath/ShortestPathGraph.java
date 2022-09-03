package com.example.algorithm.Graph.ShortestPath;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import org.json.JSONException;

public class ShortestPathGraph extends Graph {
    public ShortestPathGraph(GraphEdge[][] adjazensMatrix) {
        super(adjazensMatrix);
    }
    public ShortestPathGraph(String graphJSON) throws JSONException {
        super(graphJSON);
    }
}
