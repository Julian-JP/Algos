package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;

import java.util.*;

public class PathFindingGraph extends Graph {
    public PathFindingGraph(GraphEdge[][] adjazensMatrix, int start, int end) {
        super(adjazensMatrix, start, end);
    }
    public PathFindingGraph(String graphJSON) throws JSONException {
        super(graphJSON);
    }

    public GraphResponse breadthFirstSearch() {
        Queue<ArrayDeque<Integer>> todo = new ArrayDeque<>();
        ArrayDeque<Integer> temp = new ArrayDeque<>();
        temp.add(start);
        todo.add(temp);
        Status[] status = new Status[adjazensMatrix.length];
        Arrays.fill(status, Status.notVisited);
        status[start] = Status.inQueue;
        breadthFirstSearchRecursive(todo, status);
        return new GraphResponse(adjazensMatrix, start, end);
    }

    public GraphResponse depthFirstSearch() {
        return new GraphResponse(adjazensMatrix, start, end);
    }

    private void breadthFirstSearchRecursive(Queue<ArrayDeque<Integer>> todo, Status[] status) {
        if (todo.isEmpty()) return;

        ArrayDeque<Integer> current = todo.poll();
        for (int i = 0; i < status.length; i++) {
            if (status[i] == Status.notVisited && adjazensMatrix[current.getLast()][i] != null) {
                ArrayDeque<Integer> currentUpdatet = current.clone();

                if (i == end) {
                    currentUpdatet.add(i);
                    colorPath(currentUpdatet);
                    return;
                }
                if (adjazensMatrix[currentUpdatet.getLast()][i].isVisited()) {
                    currentUpdatet.add(i);
                    todo.add(currentUpdatet);
                    status[i] = Status.inQueue;
                } else {
                    return;
                }
            }
        }
        status[current.getLast()] = Status.finished;
        breadthFirstSearchRecursive(todo, status);
    }

    private void colorPath(ArrayDeque<Integer> path) {
        while (path.size() > 1) {
            Integer first = path.poll();
            Integer seconde = path.getFirst();
            adjazensMatrix[first][seconde].finish();
        }
    }

    enum Status {
        finished, inQueue, notVisited;
    }
}
