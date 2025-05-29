package com.example.algorithm.Graph.MinimalSpanningTree;

import com.example.algorithm.Graph.Graph;
import org.json.JSONException;
import org.json.JSONObject;

public class MinimalSpanningTreeGraph extends Graph {
    private int start;

    public MinimalSpanningTreeGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        start = graph.getInt("start");
    }
}
