package com.example.algorithm.Graph.AllShortestPath.Dijkstra;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.AllShortestPath.AllShortestPathGraph;
import com.example.algorithm.Graph.AllShortestPath.AllShortestPathService;
import com.example.algorithm.ResponseTypes.GraphResponse;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class AllShortestPathDijkstraService extends AllShortestPathService {
    @Override
    public GraphResponse[] execute(String graphString) throws JSONException {
        AllShortestPathGraph graph = new AllShortestPathGraph(graphString);
        return dijkstra(graph).toArray(new GraphResponse[0]);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/dijkstraAllShortestPath.txt").toPath()));
        return new Explanation(explanation);
    }

    class DijkstraElement {
        double cost;
        int element;

        public DijkstraElement(double cost, int element) {
            this.cost = cost;
            this.element = element;
        }
    }


    private ArrayList<GraphResponse> dijkstra(AllShortestPathGraph graph) {
        ArrayList<GraphResponse> steps = new ArrayList<>();
        steps.add(new GraphResponse(graph));
        PriorityQueue<DijkstraElement> queue = new PriorityQueue<>(Comparator.comparingDouble(value -> value.cost));
        queue.add(new DijkstraElement(0.0, graph.getStart()));

        while (!queue.isEmpty()) {
            DijkstraElement next = queue.poll();

            for (int i=0; i < graph.getAdjacencyMatrix()[next.element].length; i++) {
                if (graph.getAdjacencyMatrix()[next.element][i] != null &&
                        graph.getCost()[i] > graph.getAdjacencyMatrix()[next.element][i].getWeight() + next.cost) {

                    double newCost = graph.getAdjacencyMatrix()[next.element][i].getWeight() + next.cost;
                    graph.getCost()[i] = newCost;


                    graph.getAdjacencyMatrix()[next.element][i].visit();

                    steps.add(new GraphResponse(graph));

                    queue.add(new DijkstraElement(newCost, i));
                }
            }
        }

        return steps;
    }
}
