package com.example.algorithm.SearchTrees.BinarySearchTree;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.SearchTrees.SearchTreeService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class BinarySearchTreeService extends SearchTreeService {

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
        return new BinarySearchTree(new BSTNode(value));
    }

    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/bst.txt").toPath()));
        return new Explanation(explanation);
    }

    private BinarySearchTree convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        return new BinarySearchTree(convNodeJSON(root.getString("root")));
    }

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
