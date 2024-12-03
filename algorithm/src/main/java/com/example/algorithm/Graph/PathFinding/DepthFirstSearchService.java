package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.PriorityQueue;

@Service
public class DepthFirstSearchService extends PathFindingService {
    @Override
    public GraphResponse[] execute(String graphString) throws JSONException {
        PathFindingGraph graph = new PathFindingGraph(graphString);

        ArrayList<GraphResponse> steps = new ArrayList<>();
        boolean[] visited = new boolean[graph.getVertexList().length];

        recursiveDepthFirstSearch(steps, graph, visited, graph.getStart());

        return steps.toArray(new GraphResponse[0]);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/dfs.txt").toPath()));
        return new Explanation(explanation);
    }

    private boolean recursiveDepthFirstSearch(ArrayList<GraphResponse> steps, PathFindingGraph graph, boolean[] visited, int current) {
        if (graph.getEnd() == current) {
            return true;
        }

        for (int i=0; i < graph.getAdjacencyMatrix()[current].length; i++) {
            if (visited[i]) {
                continue;
            } else {
                visited[i] = true;
            }


            graph.getAdjacencyMatrix()[current][i].visit();
            steps.add(new GraphResponse(graph));

            boolean finished = recursiveDepthFirstSearch(steps, graph, visited, i);
            if (finished) {
                graph.getAdjacencyMatrix()[current][i].finish();
                steps.add(new GraphResponse(graph));
                return true;
            }
        }

        return false;
    }
}
