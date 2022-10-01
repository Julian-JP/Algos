package com.example.algorithm.Graph.MinimalSpanningTree.JarnikPrim;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.GraphResponse;
import com.example.algorithm.Graph.MinimalSpanningTree.MinimalSpanningTreeGraph;
import com.example.algorithm.Graph.MinimalSpanningTree.MinimalSpanningTreeService;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class JarnikPrimService extends MinimalSpanningTreeService {
    public GraphResponse step(String graphString) throws JSONException {
        MinimalSpanningTreeGraph graph = new MinimalSpanningTreeGraph(graphString);
        return graph.jarnikPrim();
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/jarnikPrim.txt").toPath()));
        return new Explanation(explanation);
    }
}
