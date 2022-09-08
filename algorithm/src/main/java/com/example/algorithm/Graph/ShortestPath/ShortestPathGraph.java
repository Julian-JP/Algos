package com.example.algorithm.Graph.ShortestPath;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;

public class ShortestPathGraph extends Graph {
    public ShortestPathGraph(GraphEdge[][] adjazensMatrix, int start, int end) {
        super(adjazensMatrix, start, end);
    }
    public ShortestPathGraph(String graphJSON) throws JSONException {
        super(graphJSON);
    }

    public GraphResponse breadthFirstSearch() {
        Status[] visited = new Status[adjazensMatrix.length];
        visited[start] = Status.inQueue;
        breadthFirstSearchRecursive(visited);
        return new GraphResponse(adjazensMatrix, start, end);
    }

    private boolean breadthFirstSearchRecursive(Status[] inQueue) {
        for (int i = 0; i < inQueue.length; i++) {
            for (int j = 0; j < adjazensMatrix.length && inQueue[i] == Status.inQueue; j++) {
                if (adjazensMatrix[i][j] != null) {
                    if (adjazensMatrix[i][j].isVisited() == false) {
                        return true;
                    } else if (inQueue[j] == Status.notVisited) {
                        inQueue[j] = Status.inQueue;
                    }
                }
            }
            inQueue[i] = Status.finished;
        }
        return false;
    }

    enum Status {
        finished, inQueue, notVisited;
    }
}
