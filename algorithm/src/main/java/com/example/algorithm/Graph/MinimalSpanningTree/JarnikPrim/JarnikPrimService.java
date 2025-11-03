package com.example.algorithm.Graph.MinimalSpanningTree.JarnikPrim;

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
public class JarnikPrimService extends MinimalSpanningTreeService {
    public GraphResponse[] execute(String graphString) throws JSONException {
        MinimalSpanningTreeGraph graph = new MinimalSpanningTreeGraph(graphString);

        return prim(graph).toArray(new GraphResponse[0]);
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/jarnikPrim.txt").toPath()));
        return new Explanation(explanation);
    }

    private ArrayList<GraphResponse> prim(MinimalSpanningTreeGraph graph) {
        ArrayList<GraphResponse> steps = new ArrayList<>();
        steps.add(new GraphResponse(graph));

        SortedSet<SimpleEdge> edges = new TreeSet<>(SimpleEdge::comp);
        UnionFind unionFind = new UnionFind(graph.getVertexList().length);

        for (int i=0; i < graph.getVertexList().length; i++) {
            if (graph.containsEdge(graph.getStart(), i)) {
                edges.add(new SimpleEdge(graph.getStart(), i, graph.getEdge(graph.getStart(), i).getWeight()));
            }
        }

        while (!edges.isEmpty()) {
            SimpleEdge edge = edges.first();
            edges.remove(edge);

            if (unionFind.find(edge.getTo()) != unionFind.find(edge.getFrom())) {
                unionFind.union(edge.getTo(), edge.getFrom());

                graph.getEdge(edge.getFrom(), edge.getTo()).visit();
                steps.add(new GraphResponse(graph));

                for (int i=0; i < graph.getVertexList().length; i++) {
                    if (graph.containsEdge(edge.getTo(), i)) {
                        edges.add(new SimpleEdge(edge.getTo(), i, graph.getEdge(edge.getTo(), i).getWeight()));
                    }
                }
            }
        }

        return steps;
    }
}
