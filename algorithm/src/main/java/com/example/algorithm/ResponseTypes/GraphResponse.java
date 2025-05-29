package com.example.algorithm.ResponseTypes;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import com.example.algorithm.Graph.GraphNode;
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
        for (int i = 0; i < graph.getAdjacencyMatrix().length; ++i) {
            for (int j = 0; j < graph.getAdjacencyMatrix()[i].length; ++j) {
                if (graph.getAdjacencyMatrix()[i][j] != null) {
                    String from = graph.getVertexList()[i].getValue();
                    String to = graph.getVertexList()[j].getValue();
                    edgeList.add(new EdgeListEntry(from, to, graph.getAdjacencyMatrix()[i][j]));
                }
            }
        }
        this.edges = edgeList.toArray(new EdgeListEntry[0]);

        vertices = new GraphNode[graph.getVertexList().length];

        for (int i = 0; i < graph.getVertexList().length; ++i) {
            vertices[i] = new GraphNode(graph.getVertexList()[i]);
        }
    }
}
