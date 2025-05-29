package com.example.algorithm.Graph.AllShortestPath;

import com.example.algorithm.Graph.Graph;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
public class AllShortestPathGraph extends Graph {
    private int start;

    public AllShortestPathGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        start = graph.getInt("start");
    }
}
