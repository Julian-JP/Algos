package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.GraphResponse;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
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
        ArrayList<GraphResponse> steps = new ArrayList<>();
        steps.add(new GraphResponse(graph));

        recursiveBreathFirstSearch(steps, graph, nextToProcess, visited);

        return steps.toArray(new GraphResponse[0]);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/bfs.txt").toPath()));
        return new Explanation(explanation);
    }

    private void recursiveBreathFirstSearch(ArrayList<GraphResponse> steps, PathFindingGraph graph, Deque<List<Integer>> nextToProcessQueue, boolean[] visited) {
        if (nextToProcessQueue.isEmpty()) {
            return;
        }

        boolean progress = false;

        List<Integer> nextToProcess = nextToProcessQueue.poll();
        int indexNextToProcess = nextToProcess.getLast();

        for (int i=0; i < graph.getAdjacencyMatrix()[indexNextToProcess].length; ++i) {
            if (!visited[i] && graph.getAdjacencyMatrix()[indexNextToProcess][i] != null) {
                visited[i] = true;
                ArrayList<Integer> newNextToProcess = new ArrayList<>(nextToProcess);
                newNextToProcess.add(i);
                nextToProcessQueue.add(newNextToProcess);

                graph.getAdjacencyMatrix()[indexNextToProcess][i].visit();
                progress = true;

                if (i == graph.getEnd()) {
                    colorFinishedPath(steps, nextToProcess, graph);
                    return;
                }
            }
        }

        if (progress) {
            steps.add(new GraphResponse(graph));
        }
        recursiveBreathFirstSearch(steps, graph, nextToProcessQueue, visited);
    }
}
