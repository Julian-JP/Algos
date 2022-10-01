package com.example.algorithm.Graph.PathFinding.BreadthFirstSearch;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.GraphResponse;
import com.example.algorithm.Graph.PathFinding.PathFindingGraph;
import com.example.algorithm.Graph.PathFinding.PathFindingService;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class BreadthFirstSearchService extends PathFindingService {
    @Override
    public GraphResponse step(String graphString) throws JSONException {
        PathFindingGraph graph = new PathFindingGraph(graphString);
        return graph.breadthFirstSearch();
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/bfs.txt").toPath()));
        return new Explanation(explanation);
    }
}
