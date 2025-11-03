package com.example.algorithm.Graph.MinimalSpanningTree.Kruskal;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.GraphResponse;
import com.example.algorithm.Graph.MinimalSpanningTree.MinimalSpanningTreeGraph;
import com.example.algorithm.Graph.MinimalSpanningTree.MinimalSpanningTreeService;
import com.example.algorithm.utils.UnionFind;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class KruskalService extends MinimalSpanningTreeService {
    @Override
    public GraphResponse[] execute(String graphString) throws JSONException {
        MinimalSpanningTreeGraph graph = new MinimalSpanningTreeGraph(graphString);
        return kruskal(graph).toArray(new GraphResponse[0]);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/kruskal.txt").toPath()));
        return new Explanation(explanation);
    }

    private List<GraphResponse> kruskal(MinimalSpanningTreeGraph graph) {
        ArrayList<GraphResponse> steps = new ArrayList<>();
        steps.add(new GraphResponse(graph));

        SortedSet<SimpleEdge> edges = new TreeSet<>(SimpleEdge::comp);
        UnionFind unionFind = new UnionFind(graph.getVertexList().length);

        for (int i=0; i < graph.getVertexList().length; i++){
            for (int j=0; j < graph.getVertexList().length; j++){
                if (graph.containsEdge(i, j)) {
                    edges.add(new SimpleEdge(i, j, graph.getEdge(i, j).getWeight()));
                }
            }
        }


        for (SimpleEdge edge : edges) {
            if (unionFind.find(edge.getFrom()) != unionFind.find(edge.getTo())) {
                unionFind.union(edge.getFrom(), edge.getTo());
                graph.getEdge(edge.getFrom(), edge.getTo()).visit();
                steps.add(new GraphResponse(graph));
            }
        }

        return steps;
    }
}
