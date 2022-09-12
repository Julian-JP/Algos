package com.example.algorithm.Graph;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Graph {
    protected GraphEdge[][] adjazensMatrix;
    protected int start, end;

    public Graph(GraphEdge[][] adjazensMatrix, int start, int end) {
        this.adjazensMatrix = adjazensMatrix;
    }

    public Graph(String graphJSON) throws JSONException {
        JSONObject graph = new JSONObject(graphJSON);
        convJSONtoAdjazensmatrix(graph.getJSONArray("edges"));
        start = graph.getInt("start");
        end = graph.getInt("end");
    }

    private void convJSONtoAdjazensmatrix(JSONArray adjMatrixJSON) throws JSONException {
        adjazensMatrix = new GraphEdge[adjMatrixJSON.length()][adjMatrixJSON.length()];
        for (int i = 0; i < adjMatrixJSON.length(); i++) {
            for (int j = 0; j < adjMatrixJSON.getJSONArray(i).length(); j++) {
                try {
                    adjazensMatrix[i][j] = new GraphEdge(adjMatrixJSON.getJSONArray(i).getJSONObject(j).getString("color"));
                } catch (JSONException e) {
                    adjazensMatrix[i][j] = null;
                }
            }
        }
    }
}
