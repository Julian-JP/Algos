package com.example.algorithm.Heaps;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.TreeResponse;
import org.json.JSONException;

import java.io.IOException;

public abstract class HeapService {
    public abstract TreeResponse insert(int value, String heapString) throws JSONException;

    public abstract TreeResponse create(int value);

    public abstract TreeResponse getMinimum(String heapString) throws JSONException;

    public abstract Explanation getExplanation() throws IOException;
}
