package com.example.algorithm.Heaps.PairingHeap;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Heaps.BinaryHeap.BinaryHeap;
import com.example.algorithm.Heaps.BinaryHeap.BinaryHeapNode;
import com.example.algorithm.ResponseTypes.TreeNodeResponse;
import com.example.algorithm.ResponseTypes.TreeResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class PairingHeapService {
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


        if (root.optJSONObject("left") != null) left = convNodeJSON(root.getString("left"));
        if (root.optJSONObject("right") != null) right = convNodeJSON(root.getString("right"));


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

        return new TreeNodeResponse(List.of(toResponseRec(root.getLeft()), toResponseRec(root.getRight())), String.valueOf(root.getValue()));
    }
}
