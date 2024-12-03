package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class DijkstraService extends PathFindingService {
    @Override
    public GraphResponse[] execute(String graphString) throws JSONException {
        PathFindingGraph graph = new PathFindingGraph(graphString);

        ArrayList<GraphResponse> steps = new ArrayList<>();
        PriorityQueue<DijkstraElement> nextToProcess = new PriorityQueue<>(Comparator.comparingDouble(value -> value.cost));
        List<Integer> temp = new ArrayList<>();
        temp.add(graph.getStart());
        nextToProcess.add(new DijkstraElement(0.0, temp));

        double[] cost = new double[graph.getVertexList().length];
        dijkstra(steps, nextToProcess, graph, cost);

        return steps.toArray(new GraphResponse[0]);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/dijkstra.txt").toPath()));
        return new Explanation(explanation);
    }

    class DijkstraElement {
        double cost;
        List<Integer> path;

        public DijkstraElement(double cost, List<Integer> path) {
            this.cost = cost;
            this.path = path;
        }
    }

    private void dijkstra(ArrayList<GraphResponse> steps, PriorityQueue<DijkstraElement> nextToProcess, PathFindingGraph graph, double[] cost) {
        while (!nextToProcess.isEmpty()) {
            DijkstraElement current = nextToProcess.poll();

            int currentIndex = current.path.getLast();
            if (currentIndex == graph.getEnd()) {
                colorFinishedPath(steps, current.path, graph);
                return;
            }

            for (int i=0; i < graph.getAdjacencyMatrix()[currentIndex].length; i++) {
                if (graph.getAdjacencyMatrix()[currentIndex][i] != null &&
                        cost[i] > graph.getAdjacencyMatrix()[currentIndex][i].getWeight() + current.cost) {

                    double newCost = graph.getAdjacencyMatrix()[currentIndex][i].getWeight() + current.cost;
                    cost[i] = newCost;
                    graph.getAdjacencyMatrix()[currentIndex][i].visit();

                    steps.add(new GraphResponse(graph));

                    List<Integer> newPath = new ArrayList<>(current.path);
                    nextToProcess.add(new DijkstraElement(newCost, newPath));
                }
            }
        }
    }
}
