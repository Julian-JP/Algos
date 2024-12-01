package com.example.algorithm.Graph.AllShortestPath;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import com.example.algorithm.Graph.GraphNode;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.Arrays;
import static com.example.algorithm.Graph.GraphEdge.UNVISITED;

public class AllShortestPathGraph extends Graph {
    GraphNode[] vertices;
    int start;
    double[] oldReachingCosts;

    public AllShortestPathGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        convNodesFromJSON(graph);
        start = graph.getInt("start");
        if (vertices[start].getWeight() > 0) {
            vertices[start].setWeight(0.0);
            oldReachingCosts[start] = 0.0;
        }
    }

    private void convNodesFromJSON(JSONObject graphJSON) throws JSONException {
        JSONArray vertices = graphJSON.getJSONArray("vertices");
        this.vertices = new GraphNode[vertices.length()];
        this.oldReachingCosts = new double[vertices.length()];

        for (int i = 0; i < vertices.length(); i++) {
            String vertexValue = vertices.getJSONObject(i).getString("value");
            Double vertexWeight = vertices.getJSONObject(i).optDouble("weight", Double.POSITIVE_INFINITY);
            this.vertices[i] = new GraphNode(vertexValue, vertexWeight);
            this.oldReachingCosts[i] = vertexWeight;
        }
    }

    public GraphResponse dijkstra() {
        vertices[start].setWeight(0.0);
        ArrayDeque<Integer> pathToStart = new ArrayDeque<>();
        pathToStart.add(start);
        ArrayDeque<Integer>[] pathsAlreadyKnown = new ArrayDeque[adjacencyMatrix.length];
        Arrays.fill(pathsAlreadyKnown, null);
        pathsAlreadyKnown[start] = pathToStart;
        dijkstraRecursive(pathsAlreadyKnown);

        return new GraphResponse(adjacencyMatrix, vertices);
    }

    private void dijkstraRecursive(ArrayDeque<Integer>[] pathToVertices) {
        double cheapestCost = Double.POSITIVE_INFINITY;
        int indexFrom = -1;
        int indexTo = -1;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int cheapestEdgeIndex = findCheapestEdge(i);
            if (cheapestEdgeIndex >= 0 && adjacencyMatrix[i][cheapestEdgeIndex].getWeight() < cheapestCost && vertices[i].getWeight() < Double.POSITIVE_INFINITY) {
                indexFrom = i;
                indexTo = cheapestEdgeIndex;
                cheapestCost = vertices[indexFrom].getWeight() + adjacencyMatrix[indexFrom][indexTo].getWeight();
            }
        }

        if (indexFrom == -1) {
            colorAllVisitedEdges();
            return;
        }

        vertices[indexTo].setWeight(vertices[indexFrom].getWeight() + adjacencyMatrix[indexFrom][indexTo].getWeight());

        ArrayDeque<Integer> currentUpdated = pathToVertices[indexFrom].clone();
        currentUpdated.add(indexTo);
        pathToVertices[indexTo] = currentUpdated;

        if (adjacencyMatrix[indexFrom][indexTo].tryToVisit()) {
            dijkstraRecursive(pathToVertices);
        }
    }

    public GraphResponse bellmanFord() {
        boolean finished = false;
        boolean changes = true;

        for (int k = 0; k < vertices.length - 1 && changes; k++) {
            changes = false;
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    if (adjacencyMatrix[i][j] != null && vertices[j].getWeight() > vertices[i].getWeight() + adjacencyMatrix[i][j].getWeight()) {
                        if (vertices[i].getWeight() < 0 && adjacencyMatrix[i][j].getWeight() < 0) {
                            vertices[j].setWeight(Double.NEGATIVE_INFINITY);
                        } else {
                            vertices[j].setWeight(vertices[i].getWeight() + adjacencyMatrix[i][j].getWeight());
                        }
                        colorEddgesFromNode(i);
                        if (vertices[j].getWeight() < oldReachingCosts[j])  {
                            finished = true;
                        }
                        changes = true;
                    }
                }
                if (finished) {
                    return new GraphResponse(adjacencyMatrix, vertices);
                }

            }
        }

        changes = true;
        resetEdgeColoring();
        for (int k = 0; k < vertices.length - 1 && changes; k++) {
            changes = false;
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    if (adjacencyMatrix[i][j] != null && vertices[j].getWeight() > vertices[i].getWeight() + adjacencyMatrix[i][j].getWeight()) {
                        vertices[j].setWeight(Double.NEGATIVE_INFINITY);
                        colorEddgesFromNode(i);
                        if (oldReachingCosts[j] > Double.NEGATIVE_INFINITY) {
                            finished = true;
                        }
                        changes = true;
                    }
                }
                if (finished) {
                    return new GraphResponse(adjacencyMatrix, vertices);
                }
            }
        }

        colorEddgesFromNode(vertices.length);
        return new GraphResponse(adjacencyMatrix, vertices);
    }

    private void colorEddgesFromNode(int nodeIndex) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (i == nodeIndex && adjacencyMatrix[i][j] != null) adjacencyMatrix[i][j].tryToVisit();
                else if (adjacencyMatrix[i][j] != null) adjacencyMatrix[i][j].tryToProcess();
            }
        }
    }
    private int findCheapestEdge(int node) {
        int index = -1;
        Double cheapest = Double.POSITIVE_INFINITY;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (i != node && adjacencyMatrix[node][i] != null && adjacencyMatrix[node][i].getWeight() < cheapest && vertices[i].getWeight() == Double.POSITIVE_INFINITY) {
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
                    adjacencyMatrix[i][j].setMarking(UNVISITED);
                }
            }
        }
    }

    private void colorAllVisitedEdges() {
        for (com.example.algorithm.Graph.GraphEdge[] matrix : adjacencyMatrix) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (matrix[j] != null && matrix[j].getMarking() == GraphEdge.VISITED) {
                    matrix[j].finish();
                }
            }
        }
    }
}
