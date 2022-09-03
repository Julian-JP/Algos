package com.example.algorithm.Graph.ShortestPath;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.Graph;
import com.example.algorithm.Graph.GraphResponse;
import org.json.JSONException;

import java.io.IOException;

public abstract class ShortestPathService {
    public abstract GraphResponse step(String graph) throws JSONException;
    public abstract Explanation getExplanation() throws IOException;
}
