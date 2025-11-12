package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.GraphResponse;
import org.json.JSONException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepthFirstSearchService extends PathFindingService {
    @Override
    public GraphResponse[] execute(String graphString) throws JSONException {
        PathFindingGraph graph = new PathFindingGraph(graphString);

        ArrayList<GraphResponse> steps = new ArrayList<>();
        steps.add(new GraphResponse(graph));
        boolean[] visited = new boolean[graph.getVertexList().length];
        visited[graph.getStart()] = true;

        List<Integer> reversedPath = recursiveDepthFirstSearch(steps, graph, visited, graph.getStart());
        List<Integer> path = reversedPath.reversed();
        colorFinishedPath(path, graph);
        steps.add(new GraphResponse(graph));

        return steps.toArray(new GraphResponse[0]);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        ClassPathResource resource = new ClassPathResource("explanations/dfs.txt");
        String explanation;
        try (InputStream in = resource.getInputStream()) {
            explanation = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        return new Explanation(explanation);
    }

    private List<Integer> recursiveDepthFirstSearch(ArrayList<GraphResponse> steps, PathFindingGraph graph, boolean[] visited, int current) {
        if (graph.getEnd() == current) {
            ArrayList<Integer> res = new ArrayList<>();
            res.add(current);
            return res;
        }

        for (int i=0; i < graph.getAdjacencyMatrix()[current].length; i++) {
            if (visited[i] || graph.getAdjacencyMatrix()[current][i] == null) {
                continue;
            }
            visited[i] = true;

            if (i != graph.getEnd()) {
                graph.getAdjacencyMatrix()[current][i].visit();
                steps.add(new GraphResponse(graph));
            }

            List<Integer> finished = recursiveDepthFirstSearch(steps, graph, visited, i);
            if (!finished.isEmpty()) {
                finished.add(current);
                return finished;
            }

        }

        return new ArrayList<>();
    }
}
