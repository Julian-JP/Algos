package com.example.algorithm.SearchTrees;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class BinarySearchTreeService {

    public BinarySearchTree insert(int value, String tree) throws JSONException {
        BinarySearchTree bst = convJSON(tree);
        bst.add(value);
        return bst;
    }

    public BinarySearchTree remove(int value, String tree) throws JSONException {
        BinarySearchTree bst = convJSON(tree);
        bst.remove(value);
        return bst;
    }

    public BinarySearchTree create(int value) {
        return new BinarySearchTree(new BSTNode(value, null, null));
    }

    public String getExplanation() throws IOException {
        return Files.readString(Paths.get("/src/main/resources/explanations/bst.txt"), StandardCharsets.UTF_8);
    }

    private BinarySearchTree convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        return new BinarySearchTree(convNodeJSON(root.getString("root")));
    }

    @org.jetbrains.annotations.NotNull
    private BSTNode convNodeJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        BSTNode left = null;
        BSTNode right = null;


        if (root.optJSONObject("left") != null) left = convNodeJSON(root.getString("left"));
        if (root.optJSONObject("right") != null) right = convNodeJSON(root.getString("right"));


        int value = root.getInt("value");

        return new BSTNode(value, left, right);
    }
}
