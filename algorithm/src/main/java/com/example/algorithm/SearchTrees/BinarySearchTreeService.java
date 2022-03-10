package com.example.algorithm.SearchTrees;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class BinarySearchTreeService {

    public BinarySearchTree insert(int value, String tree) throws JSONException {
        BinarySearchTree bst = convJSON(tree);
        bst.add(value);
        return bst;
    }

    public BinarySearchTree remove(int value, String tree) {
        return null;
    }

    public BinarySearchTree create(int value) {
        return new BinarySearchTree(new BSTNode(value, null, null));
    }

    private BinarySearchTree convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        return new BinarySearchTree(convNodeJSON(root.getString("root")));
    }

    @org.jetbrains.annotations.NotNull
    private BSTNode convNodeJSON(String json) throws JSONException {
        System.out.println(json);
        JSONObject root = new JSONObject(json);
        BSTNode left = null;
        BSTNode right = null;


        if (root.optJSONObject("left") != null) left = convNodeJSON(root.getString("left"));
        if (root.optJSONObject("right") != null) right = convNodeJSON(root.getString("right"));


        int value = root.getInt("value");

        return new BSTNode(value, left, right);
    }
}
