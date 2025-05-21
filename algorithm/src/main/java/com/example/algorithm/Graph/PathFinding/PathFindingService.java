package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.GraphResponse;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class PathFindingService {
    public abstract GraphResponse[] execute(String graph) throws JSONException;
    public abstract Explanation getExplanation() throws IOException;


    protected void colorFinishedPath(ArrayList<GraphResponse> steps, List<Integer> path, PathFindingGraph graph) {
        int prevNode = path.getFirst();
        for (int i=1; i < path.size(); ++i) {
            int currentNode = path.get(i);
            graph.getAdjacencyMatrix()[prevNode][currentNode].finish();
            prevNode = currentNode;
        }

        steps.add(new GraphResponse(graph));
    }
}
