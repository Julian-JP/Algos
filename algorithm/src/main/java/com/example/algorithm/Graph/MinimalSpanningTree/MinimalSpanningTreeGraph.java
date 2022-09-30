package com.example.algorithm.Graph.MinimalSpanningTree;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MinimalSpanningTreeGraph extends Graph {
    private int start;

    public MinimalSpanningTreeGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        start = graph.getInt("start");
    }

    public GraphResponse jarnikPrim() {
        Boolean[] connectedNodes = new Boolean[adjacencyMatrix.length];
        Arrays.fill(connectedNodes, false);
        connectedNodes[start] = true;
        jarnikPrimRecurisve(connectedNodes);
        return new GraphResponse(adjacencyMatrix);
    }

    private void jarnikPrimRecurisve(Boolean[] connectedNodes) {
        Double cheapestEdge = Double.POSITIVE_INFINITY;
        int cheapestEdgeIndex = -1;
        int cheapestEdgeIndexFrom = -1;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int cheapestNodetemp = findCheapestNotConnectedNode(connectedNodes, i);
            if (connectedNodes[i] && cheapestNodetemp >= 0 && adjacencyMatrix[i][cheapestNodetemp].getWeight() < cheapestEdge) {
                cheapestEdge = adjacencyMatrix[i][cheapestNodetemp].getWeight();
                cheapestEdgeIndex = cheapestNodetemp;
                cheapestEdgeIndexFrom = i;
            }
        }
        connectedNodes[cheapestEdgeIndex] = true;

        boolean isVisisted = adjacencyMatrix[cheapestEdgeIndexFrom][cheapestEdgeIndex].isVisited() || adjacencyMatrix[cheapestEdgeIndex][cheapestEdgeIndexFrom].isVisited();
        if (Arrays.stream(connectedNodes).filter((elem) -> !elem).count() == 0) {
            colorAllEdges();
            return;
        }

        if (isVisisted) {
            jarnikPrimRecurisve(connectedNodes);
        }



    }

    private int findCheapestNotConnectedNode(Boolean[] connectedNodes, int node) {
        Double cheapestEdge = Double.POSITIVE_INFINITY;
        int cheapestEdgeIndex = -1;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (!connectedNodes[i] && adjacencyMatrix[node][i] != null && adjacencyMatrix[node][i].getWeight() < cheapestEdge) {
                cheapestEdge = adjacencyMatrix[node][i].getWeight();
                cheapestEdgeIndex = i;
            }
        }
        return cheapestEdgeIndex;
    }

    private void colorAllEdges() {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] != null && adjacencyMatrix[i][j].getColor().equals("blue")) {
                    adjacencyMatrix[i][j].finish();
                }
            }
        }
    }
}
