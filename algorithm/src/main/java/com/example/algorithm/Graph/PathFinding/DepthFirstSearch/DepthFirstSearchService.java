package com.example.algorithm.Graph.PathFinding.DepthFirstSearch;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.GraphResponse;
import com.example.algorithm.Graph.PathFinding.PathFindingGraph;
import com.example.algorithm.Graph.PathFinding.PathFindingService;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class DepthFirstSearchService extends PathFindingService {
    @Override
    public GraphResponse step(String graphString) throws JSONException {
        PathFindingGraph graph = new PathFindingGraph(graphString);
        return graph.depthFirstSearch();
    }

    @Override
    public Explanation getExplanation() throws IOException {
        return new Explanation("Test");
    }
}
