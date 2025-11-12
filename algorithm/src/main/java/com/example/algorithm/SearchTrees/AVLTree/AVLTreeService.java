package com.example.algorithm.SearchTrees.AVLTree;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.TreeResponse;
import com.example.algorithm.SearchTrees.SearchTreeService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class AVLTreeService extends SearchTreeService {
    @Override
    public TreeResponse insert(int value, String tree) throws JSONException {
        AVLTree avlTree = convJSON(tree);
        avlTree.add(value);
        return toResponse(avlTree.getRoot());
    }

    @Override
    public TreeResponse remove(int value, String tree) throws JSONException {
        AVLTree avlTree = convJSON(tree);
        avlTree.remove(value);
        return toResponse(avlTree.getRoot());
    }

    @Override
    public TreeResponse create(int value) {
        return toResponse(new AVLTree(new AVLTreeNode(value)).getRoot());
    }

    @Override
    public Explanation getExplanation() throws IOException {
        ClassPathResource resource = new ClassPathResource("explanations/avl.txt");
        String explanation;
        try (InputStream in = resource.getInputStream()) {
            explanation = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        return new Explanation(explanation);
    }

    private AVLTree convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        return new AVLTree(convNodeJSON(root.getString("root")));
    }

    private AVLTreeNode convNodeJSON(String json) throws JSONException {
        if (json.equals("null") || json.isEmpty()) {
            return null;
        }

        JSONObject root = new JSONObject(json);

        AVLTreeNode left;
        AVLTreeNode right;

        JSONArray children = root.getJSONArray("children");

        if (children.length() == 2) {
            left = convNodeJSON(children.getString(0));
            right = convNodeJSON(children.getString(1));
        } else {
            throw new JSONException("invalid json 2 children expected");
        }

        int value = root.getInt("value");

        return new AVLTreeNode(value, left, right);
    }
}
