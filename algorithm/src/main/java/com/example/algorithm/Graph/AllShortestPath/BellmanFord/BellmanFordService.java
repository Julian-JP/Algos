package com.example.algorithm.Graph.AllShortestPath.BellmanFord;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.AllShortestPath.AllShortestPathGraph;
import com.example.algorithm.Graph.AllShortestPath.AllShortestPathService;
import com.example.algorithm.Graph.AllShortestPath.Dijkstra.AllShortestPathDijkstraService;
import com.example.algorithm.ResponseTypes.GraphResponse;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

@Service
public class BellmanFordService extends AllShortestPathService {
    @Override
    public GraphResponse[] execute(String graphString) throws JSONException {
        AllShortestPathGraph graph = new AllShortestPathGraph(graphString);
        return bellmanford(graph).toArray(new GraphResponse[0]);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/bellmanFord.txt").toPath()));
        return new Explanation(explanation);
    }

    private ArrayList<GraphResponse> bellmanford(AllShortestPathGraph graph) {
        ArrayList<GraphResponse> steps = new ArrayList<>();
        steps.add(new GraphResponse(graph));
        boolean change = true;

        for (int i=0; i < graph.getVertexList().length && change; ++i) {
            change = false;

            for (int j=0; j < graph.getAdjacencyMatrix().length; ++j) {
                for (int k=0; k < graph.getAdjacencyMatrix()[j].length; ++k) {
                    if (graph.getAdjacencyMatrix()[j][k] == null) {
                        continue;
                    }
                    double newCost = steps.getLast().getVertices()[j].getWeight() + graph.getAdjacencyMatrix()[j][k].getWeight();
                    if (newCost >= steps.getLast().getVertices()[k].getWeight()) {
                        continue;
                    }

                    graph.getCost()[k] = newCost;
                    graph.getAdjacencyMatrix()[j][k].visit();
                    change = true;
                }
            }
            if (change) {
                steps.add(new GraphResponse(graph));
            }
        }


        change = true;
        for (int i=0; i < graph.getVertexList().length && change; ++i) {
            change = false;

            for (int j=0; j < graph.getAdjacencyMatrix().length; ++j) {
                for (int k=0; k < graph.getAdjacencyMatrix()[j].length; ++k) {
                    if (graph.getAdjacencyMatrix()[j][k] == null) {
                        continue;
                    }
                    double newCost = steps.getLast().getVertices()[j].getWeight() + graph.getAdjacencyMatrix()[j][k].getWeight();
                    if (newCost >= steps.getLast().getVertices()[k].getWeight()) {
                        continue;
                    }

                    graph.getCost()[k] = Double.NEGATIVE_INFINITY;
                    graph.getAdjacencyMatrix()[j][k].finish();
                    change = true;
                }
            }

            if (change) {
                steps.add(new GraphResponse(graph));
            }
        }

        return steps;
    }
}
