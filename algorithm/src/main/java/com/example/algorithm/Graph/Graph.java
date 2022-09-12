package com.example.algorithm.Graph;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Graph {
    protected GraphEdge[][] adjacencyMatrix;
    protected int start, end;

    public Graph(String graphJSON) throws JSONException {
        JSONObject graph = new JSONObject(graphJSON);
        convJSONtoAdjacencyMatrix(graph.getJSONArray("edges"));
        start = graph.getInt("start");
        end = graph.getInt("end");
    }

    private void convJSONtoAdjacencyMatrix(JSONArray adjMatrixJSON) throws JSONException {
        adjacencyMatrix = new GraphEdge[adjMatrixJSON.length()][adjMatrixJSON.length()];
        for (int i = 0; i < adjMatrixJSON.length(); i++) {
            for (int j = 0; j < adjMatrixJSON.getJSONArray(i).length(); j++) {
                try {
                    adjacencyMatrix[i][j] = new GraphEdge(adjMatrixJSON.getJSONArray(i).getJSONObject(j).getString("color"));
                } catch (JSONException e) {
                    adjacencyMatrix[i][j] = null;
                }
            }
        }
    }
}
