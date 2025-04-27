package com.example.algorithm.SearchTrees.AVLTree;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.SearchTrees.SearchTree;
import com.example.algorithm.SearchTrees.SearchTreeService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class AVLTreeService extends SearchTreeService {
    @Override
    public SearchTree insert(int value, String tree) throws JSONException {
        AVLTree avlTree = convJSON(tree);
        avlTree.add(value);
        return avlTree;
    }

    @Override
    public SearchTree remove(int value, String tree) throws JSONException {
        AVLTree avlTree = convJSON(tree);
        avlTree.remove(value);
        return avlTree;
    }

    @Override
    public AVLTree create(int value) {
        return new AVLTree(new AVLTreeNode(value));
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/avl.txt").toPath()));
        return new Explanation(explanation);
    }

    private AVLTree convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        return new AVLTree(convNodeJSON(root.getString("root")));
    }

    private AVLTreeNode convNodeJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        AVLTreeNode left = null;
        AVLTreeNode right = null;


        if (root.optJSONObject("left") != null) left = convNodeJSON(root.getString("left"));
        if (root.optJSONObject("right") != null) right = convNodeJSON(root.getString("right"));


        int value = root.getInt("value");

        return new AVLTreeNode(value, left, right);
    }
}
