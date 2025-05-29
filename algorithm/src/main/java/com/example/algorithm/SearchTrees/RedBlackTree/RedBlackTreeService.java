package com.example.algorithm.SearchTrees.RedBlackTree;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.TreeResponse;
import com.example.algorithm.SearchTrees.BinarySearchTree.BSTNode;
import com.example.algorithm.SearchTrees.BinarySearchTree.BinarySearchTree;
import com.example.algorithm.SearchTrees.SearchTree;
import com.example.algorithm.SearchTrees.SearchTreeService;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class RedBlackTreeService extends SearchTreeService {
    @Override
    public TreeResponse insert(int value, String tree) throws JSONException {
        RedBlackTree redBlackTree = convJSON(tree);
        redBlackTree.add(value);
        return toResponse(redBlackTree.getRoot());
    }

    @Override
    public TreeResponse remove(int value, String tree) throws JSONException {
        RedBlackTree redBlackTree = convJSON(tree);
        redBlackTree.remove(value);
        return toResponse(redBlackTree.getRoot());
    }

    @Override
    public TreeResponse create(int value) {
        return toResponse(new RedBlackTree(new RedBlackTreeNode(value, false, null)).getRoot());
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/redblackTree.txt").toPath()));
        return new Explanation(explanation);
    }

    private RedBlackTree convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        return new RedBlackTree(convNodeJSON(root.getString("root"), null));
    }

    private RedBlackTreeNode convNodeJSON(String json, RedBlackTreeNode parent) throws JSONException {
        if (json.equals("null") || json.isEmpty()) {
            return null;
        }

        JSONObject root = new JSONObject(json);

        RedBlackTreeNode curNode;
        String color = root.getString("color");

        if (root.getString("value").equals("null")) {
            curNode = RedBlackTreeNode.getNilNode(parent);
        } else {
            int value = root.getInt("value");
            curNode = new RedBlackTreeNode(value, null, null, color, parent);
        }

        JSONArray children = root.getJSONArray("children");

        if (children.length() == 2) {
            curNode.setLeft(convNodeJSON(children.getString(0), curNode));
            curNode.setRight(convNodeJSON(children.getString(1), curNode));
        } else {
            throw new JSONException("invalid json 2 children expected");
        }

        return curNode;
    }
}
