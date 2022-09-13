package com.example.algorithm.SearchTrees.RedBlackTree;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.SearchTrees.SearchTree;
import com.example.algorithm.SearchTrees.SearchTreeService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RedBlackTreeService extends SearchTreeService {
    @Override
    public SearchTree insert(int value, String tree) throws JSONException {
        RedBlackTree redBlackTree = convJSON(tree);
        redBlackTree.add(value);
        return redBlackTree;
    }

    @Override
    public SearchTree remove(int value, String tree) throws JSONException {
        RedBlackTree redBlackTree = convJSON(tree);
        redBlackTree.remove(value);
        return redBlackTree;
    }

    @Override
    public SearchTree create(int value) {
        return new RedBlackTree(new RedBlackTreeNode(value, false));
    }

    @Override
    public Explanation getExplanation() throws IOException {
        return null;
    }

    private RedBlackTree convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        return new RedBlackTree(convNodeJSON(root.getString("root")));
    }

    @org.jetbrains.annotations.NotNull
    private RedBlackTreeNode convNodeJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        RedBlackTreeNode left = null;
        RedBlackTreeNode right = null;
        String color;


        if (root.optJSONObject("left") != null) left = convNodeJSON(root.getString("left"));
        if (root.optJSONObject("right") != null) right = convNodeJSON(root.getString("right"));
        color = root.getString("color");


        int value = root.getInt("value");

        return new RedBlackTreeNode(value, left, right, color);
    }
}
