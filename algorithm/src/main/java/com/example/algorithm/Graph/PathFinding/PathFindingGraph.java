package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Graph.Graph;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
public class PathFindingGraph extends Graph {
    private final int start;
    private final int end;

    public PathFindingGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        start = getVertexId(graph.getString("start"));
        end = getVertexId(graph.getString("end"));
    }
}
