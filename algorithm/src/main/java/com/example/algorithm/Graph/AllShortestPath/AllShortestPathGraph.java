package com.example.algorithm.Graph.AllShortestPath;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphNode;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.regex.Pattern;

public class AllShortestPathGraph extends Graph {
    GraphNode[] vertices;
    int start;
    double[] reachingCosts;

    public AllShortestPathGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        convNodesFromJSON(graph);
        start = graph.getInt("start");
    }

    private void convNodesFromJSON(JSONObject graphJSON) throws JSONException {
        JSONArray vertices = graphJSON.getJSONArray("vertices");
        this.vertices = new GraphNode[vertices.length()];
        this.reachingCosts = new double[vertices.length()];

        for (int i = 0; i < vertices.length(); i++) {
            String vertex = vertices.getJSONObject(i).getString("value");
            String[] vertexValue = vertex.split(Pattern.quote("|"));
            this.vertices[i] = new GraphNode(vertexValue[0]);
            this.reachingCosts[i] = Double.POSITIVE_INFINITY;
        }
    }

    public GraphResponse dijkstra() {
        reachingCosts[start] = 0;
        ArrayDeque<Integer> pathToStart = new ArrayDeque<>();
        pathToStart.add(start);
        ArrayDeque<Integer>[] pathsAlreadyKnown = new ArrayDeque[adjacencyMatrix.length];
        Arrays.fill(pathsAlreadyKnown, null);
        pathsAlreadyKnown[start] = pathToStart;
        dijkstraRecursive(pathsAlreadyKnown);

        for (int i = 0; i < vertices.length; i++) {
            if (reachingCosts[i] == Double.POSITIVE_INFINITY) {
                vertices[i].setValue(vertices[i].getValue() + "|∞");
            } else if (reachingCosts[i] == Double.NEGATIVE_INFINITY) {
                vertices[i].setValue(vertices[i].getValue() + "|-∞");
            } else {
                vertices[i].setValue(vertices[i].getValue() + "|" + reachingCosts[i]);
            }
        }
        return new GraphResponse(adjacencyMatrix, vertices);
    }

    private void dijkstraRecursive(ArrayDeque<Integer>[] pathToVertices) {
        double cheapestCost = Double.POSITIVE_INFINITY;
        int indexFrom = -1;
        int indexTo = -1;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int cheapestEdgeIndex = findCheapestEdge(i);
            if (cheapestEdgeIndex >= 0 && adjacencyMatrix[i][cheapestEdgeIndex].getWeight() < cheapestCost && reachingCosts[i] < Double.POSITIVE_INFINITY) {
                indexFrom = i;
                indexTo = cheapestEdgeIndex;
                cheapestCost = reachingCosts[indexFrom] + adjacencyMatrix[indexFrom][indexTo].getWeight();
            }
        }

        if (indexFrom == -1) {
            colorAllVisitedEdges();
            return;
        }

        reachingCosts[indexTo] = reachingCosts[indexFrom] + adjacencyMatrix[indexFrom][indexTo].getWeight();

        ArrayDeque<Integer> currentUpdated = pathToVertices[indexFrom].clone();
        currentUpdated.add(indexTo);
        pathToVertices[indexTo] = currentUpdated;

        if (adjacencyMatrix[indexFrom][indexTo].tryToVisit()) {
            dijkstraRecursive(pathToVertices);
        }
    }

    private int findCheapestEdge(int node) {
        int index = -1;
        Double cheapest = Double.POSITIVE_INFINITY;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (i != node && adjacencyMatrix[node][i] != null && adjacencyMatrix[node][i].getWeight() < cheapest && reachingCosts[i] == Double.POSITIVE_INFINITY) {
                index = i;
                cheapest = adjacencyMatrix[node][i].getWeight();
            }
        }
        return index;
    }

    private void colorAllVisitedEdges() {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] != null && adjacencyMatrix[i][j].isVisited()) {
                    adjacencyMatrix[i][j].finish();
                }
            }
        }
    }
}
