package com.example.algorithm.Graph.AllShortestPath;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphNode;
import com.example.algorithm.Graph.GraphResponse;
import org.jetbrains.annotations.NotNull;
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
    double[] oldReachingCosts;

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
        this.oldReachingCosts = new double[vertices.length()];

        for (int i = 0; i < vertices.length(); i++) {
            String vertex = vertices.getJSONObject(i).getString("value");
            String[] vertexValue = vertex.split(Pattern.quote("|"));
            this.vertices[i] = new GraphNode(vertexValue[0]);
            this.reachingCosts[i] = Double.POSITIVE_INFINITY;
            if (vertexValue.length > 1 && vertexValue[1].equals("-∞")) {
                oldReachingCosts[i] = Double.NEGATIVE_INFINITY;
            } else if (vertexValue.length > 1 && !vertexValue[1].equals("∞")) {
                oldReachingCosts[i] = Double.valueOf(vertexValue[1]);
            } else {
                oldReachingCosts[i] = Double.POSITIVE_INFINITY;
            }
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

        return updateVerticesValues();
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

    public GraphResponse bellmanFord() {
        reachingCosts[start] = 0;
        boolean finished = false;
        boolean changes = true;

        for (int k = 0; k < vertices.length - 1 && changes; k++) {
            changes = false;
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    if (adjacencyMatrix[i][j] != null && reachingCosts[j] > reachingCosts[i] + adjacencyMatrix[i][j].getWeight()) {
                        reachingCosts[j] = reachingCosts[i] + adjacencyMatrix[i][j].getWeight();
                        colorEddgesFromNode(i);
                        if (reachingCosts[j] < oldReachingCosts[j])  {
                            finished = true;
                        }
                        changes = true;
                    }
                }
                if (finished) {
                    return updateVerticesValues();
                }

            }
        }

        changes = true;
        resetEdgeColoring();
        for (int k = 0; k < vertices.length - 1 && changes; k++) {
            changes = false;
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    if (adjacencyMatrix[i][j] != null && reachingCosts[j] > reachingCosts[i] + adjacencyMatrix[i][j].getWeight()) {
                        reachingCosts[j] = Double.NEGATIVE_INFINITY;
                        colorEddgesFromNode(i);
                        if (oldReachingCosts[j] > Double.NEGATIVE_INFINITY) {
                            finished = true;
                        }
                        changes = true;
                    }
                }
                if (finished) {
                    return updateVerticesValues();
                }
            }
        }

        colorEddgesFromNode(vertices.length);
        return updateVerticesValues();
    }

    private void colorEddgesFromNode(int nodeIndex) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (i == nodeIndex && adjacencyMatrix[i][j] != null) adjacencyMatrix[i][j].tryToVisit();
                else if (adjacencyMatrix[i][j] != null) adjacencyMatrix[i][j].tryToProcess();
            }
        }
    }



    @NotNull
    private GraphResponse updateVerticesValues() {
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

    private void resetEdgeColoring() {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] != null) {
                    adjacencyMatrix[i][j].setUnvisited();
                }
            }
        }
    }

    private void colorAllVisitedEdges() {
        for (com.example.algorithm.Graph.GraphEdge[] matrix : adjacencyMatrix) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (matrix[j] != null && matrix[j].isVisited()) {
                    matrix[j].finish();
                }
            }
        }
    }
}
