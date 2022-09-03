package com.example.algorithm.Graph.ShortestPath.BreadthFirstSearch;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphResponse;
import com.example.algorithm.Graph.ShortestPath.ShortestPathGraph;
import com.example.algorithm.Graph.ShortestPath.ShortestPathService;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BreadthFirstSearchService extends ShortestPathService {
    @Override
    public GraphResponse step(String graphString) throws JSONException {
        ShortestPathGraph graph = new ShortestPathGraph(graphString);
        return null;
    }

    @Override
    public Explanation getExplanation() throws IOException {
        return new Explanation("Test");
    }
}
