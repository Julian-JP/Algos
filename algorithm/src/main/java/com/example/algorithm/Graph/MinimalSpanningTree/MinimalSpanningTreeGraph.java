package com.example.algorithm.Graph.MinimalSpanningTree;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MinimalSpanningTreeGraph extends Graph {
    private int start;

    public MinimalSpanningTreeGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        start = graph.getInt("start");
    }
}
