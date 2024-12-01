package com.example.algorithm.Graph.MinimalSpanningTree;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MinimalSpanningTreeGraph extends Graph {
    private int start;

    public MinimalSpanningTreeGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        if (graph.has("start")) {
            start = graph.getInt("start");
        } else {
            start = 0;
        }
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

        boolean isVisisted = adjacencyMatrix[cheapestEdgeIndexFrom][cheapestEdgeIndex].tryToVisit() || adjacencyMatrix[cheapestEdgeIndex][cheapestEdgeIndexFrom].tryToVisit();
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
                if (adjacencyMatrix[i][j] != null && adjacencyMatrix[i][j].getMarking() == GraphEdge.VISITED) {
                    adjacencyMatrix[i][j].finish();
                }
            }
        }
    }

    public GraphResponse kruskal() {
        PriorityQueue<Integer[]> edges = new PriorityQueue<>((e1,e2) -> Double.compare(adjacencyMatrix[e1[0]][e1[1]].getWeight(), adjacencyMatrix[e2[0]][e2[1]].getWeight()));

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] != null) {
                    Integer[] temp = {i, j};
                    edges.add(temp);
                }
            }
        }
        int[] connectedComponents = new int[adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            connectedComponents[i] = i;
        }
        kruskalRecurisve(edges, connectedComponents);
        return new GraphResponse(adjacencyMatrix);
    }

    private void kruskalRecurisve(PriorityQueue<Integer[]> edges, int[] connectedComponents) {
        if (edges.size() == 0) {
            colorAllEdges();
            return;
        }

        Integer[] edge = edges.poll();
        if (connectedComponents[edge[0]] != connectedComponents[edge[1]]) {
            boolean isVisisted = adjacencyMatrix[edge[0]][edge[1]].tryToVisit() || adjacencyMatrix[edge[1]][edge[0]].tryToVisit();
            mergeComponents(edge[0], edge[1], connectedComponents);
            if (isVisisted) {
                kruskalRecurisve(edges, connectedComponents);
            }
        } else {
            kruskalRecurisve(edges, connectedComponents);
        }
    }

    private void mergeComponents(int a, int b, int[] connectedComponents) {
        int valueToChange = connectedComponents[b];
        for (int i = 0; i < connectedComponents.length; i++) {
            if (connectedComponents[i] == valueToChange) {
                connectedComponents[i] = connectedComponents[a];
            }
        }
    }
}
