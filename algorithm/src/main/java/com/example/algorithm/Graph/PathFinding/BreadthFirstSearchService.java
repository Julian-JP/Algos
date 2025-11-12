package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.GraphResponse;
import org.json.JSONException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class BreadthFirstSearchService extends PathFindingService {
    @Override
    public GraphResponse[] execute(String graphString) throws JSONException {
        PathFindingGraph graph = new PathFindingGraph(graphString);

        Deque<List<Integer>> nextToProcess = new ArrayDeque<>();
        ArrayList<Integer> startProcessingList = new ArrayList<>();
        startProcessingList.add(graph.getStart());
        nextToProcess.add(startProcessingList);

        boolean[] visited = new boolean[graph.getVertexList().length];
        visited[graph.getStart()] = true;

        ArrayList<GraphResponse> steps = new ArrayList<>();
        steps.add(new GraphResponse(graph));

        List<Integer> shortestPath = recursiveBreathFirstSearch(steps, graph, nextToProcess, visited);
        colorFinishedPath(shortestPath, graph);
        steps.add(new GraphResponse(graph));

        return steps.toArray(new GraphResponse[0]);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        ClassPathResource resource = new ClassPathResource("explanations/bfs.txt");
        String explanation;
        try (InputStream in = resource.getInputStream()) {
            explanation = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        return new Explanation(explanation);
    }

    private List<Integer> recursiveBreathFirstSearch(ArrayList<GraphResponse> steps, PathFindingGraph graph, Deque<List<Integer>> nextToProcessQueue, boolean[] visited) {
        if (nextToProcessQueue.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> path = nextToProcessQueue.poll();
        int cur = path.getLast();

        for (int i = 0; i < graph.getAdjacencyMatrix()[cur].length; i++) {
            if (graph.getAdjacencyMatrix()[cur][i] != null && !visited[i]) {
                visited[i] = true;

                graph.getAdjacencyMatrix()[cur][i].visit();
                steps.add(new GraphResponse(graph));

                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(i);
                nextToProcessQueue.addLast(newPath);

                if (i == graph.getEnd()) {
                    return newPath;
                }
            }
        }
        return recursiveBreathFirstSearch(steps, graph, nextToProcessQueue, visited);
    }
}
