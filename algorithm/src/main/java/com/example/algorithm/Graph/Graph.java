package com.example.algorithm.Graph;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Graph {
    protected GraphEdge[][] adjacencyMatrix;

    public Graph(String graphJSON) throws JSONException {
        JSONObject graph = new JSONObject(graphJSON);
        convJSONtoAdjacencyMatrix(graph.getJSONArray("edges"));
    }

    private void convJSONtoAdjacencyMatrix(JSONArray adjMatrixJSON) throws JSONException {
        adjacencyMatrix = new GraphEdge[adjMatrixJSON.length()][adjMatrixJSON.length()];
        for (int i = 0; i < adjMatrixJSON.length(); i++) {
            for (int j = 0; j < adjMatrixJSON.getJSONArray(i).length(); j++) {
                try {
                    if (adjMatrixJSON.getJSONArray(i).getJSONObject(j).getString("weight").equals("null")) {
                        adjacencyMatrix[i][j] = new GraphEdge(adjMatrixJSON.getJSONArray(i).getJSONObject(j).getInt("marking"));
                    } else {
                        adjacencyMatrix[i][j] = new GraphEdge(adjMatrixJSON.getJSONArray(i).getJSONObject(j).getInt("marking"), adjMatrixJSON.getJSONArray(i).getJSONObject(j).getDouble("weight"));
                    }
                } catch (JSONException e) {
                    adjacencyMatrix[i][j] = null;
                }
            }
        }
    }
}
