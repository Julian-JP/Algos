package com.example.algorithm.Graph.MinimalSpanningTree;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.GraphResponse;
import lombok.Getter;
import org.json.JSONException;

import java.io.IOException;

public abstract class MinimalSpanningTreeService {
    public abstract GraphResponse[] execute(String graph) throws JSONException;
    public abstract Explanation getExplanation() throws IOException;

    @Getter
    public static class SimpleEdge {
        int from;
        int to;
        double weight;
        public SimpleEdge(int from, int to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int comp(SimpleEdge e2) {
            if (weight == e2.getWeight() && from == e2.getFrom()) {
                return to - e2.getTo();
            } else if (weight == e2.getWeight()) {
                return from - e2.getFrom();
            } else {
                return Double.compare(weight, e2.getWeight());
            }
        }
    }
}
