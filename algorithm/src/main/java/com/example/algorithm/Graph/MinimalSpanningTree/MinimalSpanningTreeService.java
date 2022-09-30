package com.example.algorithm.Graph.MinimalSpanningTree;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;

import java.io.IOException;

public abstract class MinimalSpanningTreeService {
    public abstract GraphResponse step(String graph) throws JSONException;
    public abstract Explanation getExplanation() throws IOException;
}
