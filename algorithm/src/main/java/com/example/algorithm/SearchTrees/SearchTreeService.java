package com.example.algorithm.SearchTrees;

import com.example.algorithm.Explanation.Explanation;
import org.json.JSONException;

import java.io.IOException;

public abstract class SearchTreeService {

    public abstract SearchTree insert(int value, String tree) throws JSONException;

    public abstract SearchTree remove(int value, String tree) throws JSONException;

    public abstract SearchTree create(int value);

    public abstract Explanation getExplanation() throws IOException;

}