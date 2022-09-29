package com.example.algorithm.Graph.PathFinding;

import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphEdge;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class PathFindingGraph extends Graph {
    protected int start, end;

    public PathFindingGraph(String graphJSON) throws JSONException {
        super(graphJSON);
        JSONObject graph = new JSONObject(graphJSON);
        start = graph.getInt("start");
        end = graph.getInt("end");
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

    private void breadthFirstSearchRecursive(Queue<ArrayDeque<Integer>> todo, Status[] status) {
        if (todo.isEmpty()) return;

        ArrayDeque<Integer> current = todo.poll();
        for (int i = 0; i < status.length; i++) {
            if (status[i] == Status.notVisited && adjacencyMatrix[current.getLast()][i] != null) {
                ArrayDeque<Integer> currentUpdate = current.clone();

                if (i == end) {
                    finishFoundedPath(currentUpdate);
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

    public GraphResponse depthFirstSearch() {
        Stack<ArrayDeque<Integer>> todo = new Stack<>();
        ArrayDeque<Integer> temp = new ArrayDeque<>();
        temp.add(start);
        todo.add(temp);
        Status[] status = new Status[adjacencyMatrix.length];
        Arrays.fill(status, Status.notVisited);
        status[start] = Status.inQueue;
        depthFirstSearchRecursive(todo, status);
        return new GraphResponse(adjacencyMatrix);
    }

    private boolean depthFirstSearchRecursive(Stack<ArrayDeque<Integer>> todo, Status[] status) {
        if (todo.isEmpty()) return true;

        ArrayDeque<Integer> current = todo.pop();
        for (int i = 0; i < status.length; i++) {
            ArrayDeque<Integer> currentUpdated = current.clone();
            if (status[i] == Status.notVisited && adjacencyMatrix[current.getLast()][i] != null) {

                status[i] = Status.inQueue;

                if (i == end) {
                    finishFoundedPath(currentUpdated);
                    return true;
                }

                if (adjacencyMatrix[current.getLast()][i].isVisited() == false) {
                    return true;
                } else {
                    currentUpdated.add(i);
                    Stack<ArrayDeque<Integer>> todoTemp = new Stack<>();
                    todoTemp.addAll(todo);
                    todoTemp.add(currentUpdated);
                    if (depthFirstSearchRecursive(todoTemp, status)) return true;
                }
            }
        }
        return false;
    }

    public GraphResponse dijkstraAlgorithm() {
        ArrayDeque<Integer> pathToStart = new ArrayDeque<>();
        pathToStart.add(start);
        ArrayDeque<Integer>[] pathsAlreadyKnown = new ArrayDeque[adjacencyMatrix.length];
        Arrays.fill(pathsAlreadyKnown, null);
        pathsAlreadyKnown[start] = pathToStart;
        Double[] initCosts = new Double[adjacencyMatrix.length];
        Arrays.fill(initCosts, Double.POSITIVE_INFINITY);
        initCosts[start] = 0.;
        dijkstraAlgorithmRecursive(initCosts, pathsAlreadyKnown);
        return new GraphResponse(adjacencyMatrix);
    }

    private void dijkstraAlgorithmRecursive(Double[] reachingCosts, ArrayDeque<Integer>[] pathToVertices) {
        int indexCheapestNode = -1;
        int indexVertexToCheapestNode = -1;
        Double cheapestCosts = Double.POSITIVE_INFINITY;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (findCheapestEdge(i, reachingCosts) >= 0 && reachingCosts[i] + adjacencyMatrix[i][findCheapestEdge(i, reachingCosts)].getWeight() < cheapestCosts) {
                cheapestCosts = reachingCosts[i] + adjacencyMatrix[i][findCheapestEdge(i, reachingCosts)].getWeight();
                indexCheapestNode = findCheapestEdge(i, reachingCosts);
                indexVertexToCheapestNode = i;
            }
        }

        reachingCosts[indexCheapestNode] = reachingCosts[indexVertexToCheapestNode] + adjacencyMatrix[indexVertexToCheapestNode][indexCheapestNode].getWeight();

        ArrayDeque<Integer> currentUpdated = pathToVertices[indexVertexToCheapestNode].clone();
        currentUpdated.add(indexCheapestNode);
        pathToVertices[indexCheapestNode] = currentUpdated;

        if (indexCheapestNode == end) {
            colorPath(pathToVertices[end]);
            return;
        }

        if (adjacencyMatrix[indexVertexToCheapestNode][indexCheapestNode].isVisited()) {
            dijkstraAlgorithmRecursive(reachingCosts, pathToVertices);
        }
    }

    private int findCheapestEdge(int node, Double[] reachingCosts) {
        int index = -1;
        Double cheapest = Double.POSITIVE_INFINITY;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (i != node && adjacencyMatrix[node][i] != null && adjacencyMatrix[node][i].getWeight() < cheapest && reachingCosts[i] == Double.POSITIVE_INFINITY) {
                index = i;
                cheapest = adjacencyMatrix[node][i].getWeight();
            }
        }
        return index;
    }

    private void colorPath(ArrayDeque<Integer> path) {
        while (path.size() > 1) {
            Integer first = path.poll();
            Integer second = path.getFirst();
            adjacencyMatrix[first][second].finish();
        }
    }

    private void finishFoundedPath(ArrayDeque<Integer> currentUpdate) {
        currentUpdate.add(end);
        colorPath(currentUpdate);
    }

    enum Status {
        finished, inQueue, notVisited
    }
}
