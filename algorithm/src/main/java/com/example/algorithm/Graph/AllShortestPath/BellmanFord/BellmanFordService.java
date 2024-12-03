package com.example.algorithm.Graph.AllShortestPath.BellmanFord;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.AllShortestPath.AllShortestPathGraph;
import com.example.algorithm.Graph.AllShortestPath.AllShortestPathService;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class BellmanFordService extends AllShortestPathService {
    @Override
    public GraphResponse step(String graphString) throws JSONException {
        AllShortestPathGraph graph = new AllShortestPathGraph(graphString);
        return new GraphResponse(graph);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/bellmanFord.txt").toPath()));
        return new Explanation(explanation);
    }
}
