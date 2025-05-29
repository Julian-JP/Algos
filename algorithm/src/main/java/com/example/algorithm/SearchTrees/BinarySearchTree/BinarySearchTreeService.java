package com.example.algorithm.SearchTrees.BinarySearchTree;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.TreeResponse;
import com.example.algorithm.SearchTrees.SearchTreeService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class BinarySearchTreeService extends SearchTreeService {

    public TreeResponse insert(int value, String tree) throws JSONException {
        BinarySearchTree bst = convJSON(tree);
        bst.add(value);
        return toResponse(bst.getRoot());
    }

    public TreeResponse remove(int value, String tree) throws JSONException {
        BinarySearchTree bst = convJSON(tree);
        bst.remove(value);
        return toResponse(bst.getRoot());
    }

    public TreeResponse create(int value) {
        return toResponse(new BinarySearchTree(new BSTNode(value)).getRoot());
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
        if (json.equals("null") || json.isEmpty()) {
            return null;
        }

        JSONObject root = new JSONObject(json);

        BSTNode left;
        BSTNode right;

        JSONArray children = root.getJSONArray("children");

        if (children.length() == 2) {
            left = convNodeJSON(children.getString(0));
            right = convNodeJSON(children.getString(1));
        } else {
            throw new JSONException("invalid json 2 children expected");
        }

        int value = root.getInt("value");

        return new BSTNode(value, left, right);
    }
}
