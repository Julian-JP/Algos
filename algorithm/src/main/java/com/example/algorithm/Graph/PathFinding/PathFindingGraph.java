package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;

import java.util.*;

public class PathFindingGraph extends Graph {
    public PathFindingGraph(String graphJSON) throws JSONException {
        super(graphJSON);
    }

    public GraphResponse breadthFirstSearch() {
        Queue<ArrayDeque<Integer>> todo = new ArrayDeque<>();
        ArrayDeque<Integer> temp = new ArrayDeque<>();
        temp.add(start);
        todo.add(temp);
        Status[] status = new Status[adjacencyMatrix.length];
        Arrays.fill(status, Status.notVisited);
        status[start] = Status.inQueue;
        breadthFirstSearchRecursive(todo, status);
        return new GraphResponse(adjacencyMatrix);
    }

    public GraphResponse depthFirstSearch() {
        return new GraphResponse(adjacencyMatrix);
    }

    private void breadthFirstSearchRecursive(Queue<ArrayDeque<Integer>> todo, Status[] status) {
        if (todo.isEmpty()) return;

        ArrayDeque<Integer> current = todo.poll();
        for (int i = 0; i < status.length; i++) {
            if (status[i] == Status.notVisited && adjacencyMatrix[current.getLast()][i] != null) {
                ArrayDeque<Integer> currentUpdate = current.clone();

                if (i == end) {
                    currentUpdate.add(i);
                    colorPath(currentUpdate);
                    return;
                }
                if (adjacencyMatrix[currentUpdate.getLast()][i].isVisited()) {
                    currentUpdate.add(i);
                    todo.add(currentUpdate);
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
            Integer second = path.getFirst();
            adjacencyMatrix[first][second].finish();
        }
    }

    enum Status {
        finished, inQueue, notVisited
    }
}
