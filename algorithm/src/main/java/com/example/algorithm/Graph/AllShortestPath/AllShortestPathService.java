package com.example.algorithm.Graph.AllShortestPath;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.GraphResponse;
import org.json.JSONException;

import java.io.IOException;

public abstract class AllShortestPathService {
    public abstract GraphResponse step(String graph) throws JSONException;
    public abstract Explanation getExplanation() throws IOException;
}
