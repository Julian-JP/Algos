package com.example.algorithm.Graph.AllShortestPath;

import com.example.algorithm.Graph.Graph;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

@Getter
public class AllShortestPathGraph extends Graph {
    private int start;

    private double[] cost;

    public AllShortestPathGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        start = super.getVertexId(graph.getString("start"));

        cost = new double[super.getVertexList().length];
        Arrays.fill(cost, Double.POSITIVE_INFINITY);
        cost[start] = 0.0;
    }
}
