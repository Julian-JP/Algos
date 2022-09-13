package com.example.algorithm.SearchTrees;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.SearchTrees.AVLTree.AVLTree;
import com.example.algorithm.SearchTrees.AVLTree.AVLTreeNode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public abstract class SearchTreeService {

    public abstract SearchTree insert(int value, String tree) throws JSONException;

    public abstract SearchTree remove(int value, String tree) throws JSONException;

    public abstract SearchTree create(int value);

    public abstract Explanation getExplanation() throws IOException;
}