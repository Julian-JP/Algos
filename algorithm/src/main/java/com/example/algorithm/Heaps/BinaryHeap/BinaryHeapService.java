package com.example.algorithm.Heaps.BinaryHeap;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.TreeNodeResponse;
import com.example.algorithm.ResponseTypes.TreeResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinaryHeapService {

    public TreeResponse insert(int value, String heap) throws JSONException {
        BinaryHeap binaryHeap = convJSON(heap);
        binaryHeap.add(value);
        return toResponse(binaryHeap.getRoot());
    }

    public TreeResponse create(int value) {
        return toResponse(new BinaryHeapNode(value, null, null));
    }

    public TreeResponse getMinimum(String heap) throws JSONException {
        BinaryHeap binaryHeap = convJSON(heap);
        binaryHeap.getMinimum();
        return toResponse(binaryHeap.getRoot());
    }

    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/binaryHeap.txt").toPath()));
        return new Explanation(explanation);
    }

    private BinaryHeap convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        if (root.getString("root") == "null") {
            return new BinaryHeap(null);
        }
        return new BinaryHeap(convNodeJSON(root.getString("root")));
    }

    private BinaryHeapNode convNodeJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);

        BinaryHeapNode left = null;
        BinaryHeapNode right = null;

        JSONArray children = root.getJSONArray("children");

        if (children.length() == 2) {
            left = convNodeJSON(children.getString(0));
            right = convNodeJSON(children.getString(1));
        } else if (children.length() == 1) {
            left = convNodeJSON(children.getString(0));
        }


        int value = root.getInt("value");

        return new BinaryHeapNode(value, left, right);
    }

    private TreeResponse toResponse(BinaryHeapNode root) {
        return new TreeResponse(toResponseRec(root));
    }

    private TreeNodeResponse toResponseRec(BinaryHeapNode root) {
        if (root == null) {
            return null;
        }

        ArrayList<TreeNodeResponse> children = new ArrayList<>();

        if (root.getLeft() != null) {
            children.add(toResponseRec(root.getLeft()));
        }
        if (root.getRight() != null) {
            children.add(toResponseRec(root.getRight()));
        }

        return new TreeNodeResponse(children, String.valueOf(root.getValue()));
    }
}
