package com.example.algorithm.Graph;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Getter
public class Graph {
    protected GraphEdge[][] adjacencyMatrix;
    protected GraphNode[] vertexList;

    public Graph(String graphJSON) throws JSONException {
        JSONObject graph = new JSONObject(graphJSON);
        constructFromJSONVertexList(graph.getJSONArray("vertices"));
        constructFromJSONEdgeList(graph.getJSONArray("edges"));
    }

    private void constructFromJSONVertexList(JSONArray jsonVertexList) throws JSONException {
        vertexList = new GraphNode[jsonVertexList.length()];

        for (int i = 0; i < jsonVertexList.length(); i++) {
            JSONObject vertex = jsonVertexList.getJSONObject(i);

            vertexList[i] = new GraphNode(vertex);
        }
    }

    private void constructFromJSONEdgeList(JSONArray jsonEdgeList) throws JSONException {
        adjacencyMatrix = new GraphEdge[vertexList.length][vertexList.length];

        for (int i=0; i < jsonEdgeList.length(); i++) {
            JSONObject edge = jsonEdgeList.getJSONObject(i);
            int from = getVertexId(edge.getString("from"));
            int to = getVertexId(edge.getString("to"));
            String id = edge.getString("id");

            Double weight = null;
            if (edge.has("weight") && !edge.isNull("weight")) {
                weight = edge.getDouble("weight");
            }

            adjacencyMatrix[from][to] = new GraphEdge(GraphEdge.UNVISITED, weight, id);
        }
    }

    public int getVertexId(String name) {
        for (int i = 0; i < vertexList.length; i++) {
            if (vertexList[i].getValue().equals(name)) {
                return i;
            }
        }

        throw new IndexOutOfBoundsException("Vertex not found");
    }
}
