package com.example.algorithm.Graph.AllShortestPath;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import com.example.algorithm.Graph.GraphNode;
import com.example.algorithm.Graph.GraphResponse;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.Arrays;
import static com.example.algorithm.Graph.GraphEdge.UNVISITED;

@Getter
public class AllShortestPathGraph extends Graph {
    private int start;

    public AllShortestPathGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        start = graph.getInt("start");
    }
}
