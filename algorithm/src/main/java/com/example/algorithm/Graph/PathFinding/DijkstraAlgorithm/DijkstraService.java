package com.example.algorithm.Graph.PathFinding.DijkstraAlgorithm;

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
public class DijkstraService extends PathFindingService {
    @Override
    public GraphResponse step(String graphString) throws JSONException {
        PathFindingGraph graph = new PathFindingGraph(graphString);
        return graph.dijkstraAlgorithm();
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/dijkstra.txt").toPath()));
        return new Explanation(explanation);
    }
}
