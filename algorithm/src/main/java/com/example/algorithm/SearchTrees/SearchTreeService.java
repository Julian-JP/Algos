package com.example.algorithm.SearchTrees;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.TreeNodeResponse;
import com.example.algorithm.ResponseTypes.TreeResponse;
import com.example.algorithm.SearchTrees.AVLTree.AVLTree;
import com.example.algorithm.SearchTrees.AVLTree.AVLTreeNode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class SearchTreeService {

    public abstract TreeResponse insert(int value, String tree) throws JSONException;

    public abstract TreeResponse remove(int value, String tree) throws JSONException;

    public abstract TreeResponse create(int value);

    public abstract Explanation getExplanation() throws IOException;

    public TreeResponse toResponse(SearchTreeNode root) {
        return new TreeResponse(toResponseRec(root));
    }

    private TreeNodeResponse toResponseRec(SearchTreeNode root) {
        if (root == null) {
            return null;
        }

        ArrayList<TreeNodeResponse> children = new ArrayList<>();

        children.add(toResponseRec(root.getLeft()));
        children.add(toResponseRec(root.getRight()));

        return new TreeNodeResponse(children, root.getColor(), String.valueOf(root.getValue()));
    }
}