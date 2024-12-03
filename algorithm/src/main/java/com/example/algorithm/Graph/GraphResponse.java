package com.example.algorithm.Graph;

import lombok.Getter;

import java.util.ArrayList;

@Getter
class EdgeListEntry {
    String id;

    String from;
    String to;
    Double weight;
    Integer marking;

    public EdgeListEntry(String from, String to, GraphEdge edge) {
        this.from = from;
        this.to = to;
        this.weight = edge.getWeight();
        this.marking = edge.getMarking();
        this.id = edge.getId();
    }
}

public class GraphResponse {
    @Getter
    private EdgeListEntry[] edges;
    @Getter
    private GraphNode[] vertices;

    public GraphResponse(Graph graph) {
        ArrayList<EdgeListEntry> edgeList = new ArrayList<>();
        for (int i=0; i < graph.adjacencyMatrix.length; ++i) {
            for (int j=0; j < graph.adjacencyMatrix[i].length; ++j) {
                if (graph.adjacencyMatrix[i][j] != null) {
                    String from = graph.vertexList[i].getValue();
                    String to = graph.vertexList[j].getValue();
                    edgeList.add(new EdgeListEntry(from, to, graph.adjacencyMatrix[i][j]));
                }
            }
        }
        this.edges = edgeList.toArray(new EdgeListEntry[0]);

        vertices = new GraphNode[graph.vertexList.length];

        for (int i=0; i < graph.vertexList.length; ++i) {
            vertices[i] = new GraphNode(graph.vertexList[i]);
        }
    }
}
