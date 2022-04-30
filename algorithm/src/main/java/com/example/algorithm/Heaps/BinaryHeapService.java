package com.example.algorithm.Heaps;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class BinaryHeapService {

    public BinaryHeapResponse insert(int value, String heap) throws JSONException {
        BinaryHeap binaryHeap = convJSON(heap);
        binaryHeap.add(value);
        return new BinaryHeapResponse(binaryHeap.getRoot());
    }

    public BinaryHeapResponse create(int value) {
        return new BinaryHeapResponse(new BinaryHeapNode(value, null, null));
    }

    public BinaryHeapResponse getMinimum(String heap) throws JSONException {
        BinaryHeap binaryHeap = convJSON(heap);
        binaryHeap.getMinimum();
        return new BinaryHeapResponse(binaryHeap.getRoot());
    }

    private BinaryHeap convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        return new BinaryHeap(convNodeJSON(root.getString("root")));
    }

    @org.jetbrains.annotations.NotNull
    private BinaryHeapNode convNodeJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        BinaryHeapNode left = null;
        BinaryHeapNode right = null;


        if (root.optJSONObject("left") != null) left = convNodeJSON(root.getString("left"));
        if (root.optJSONObject("right") != null) right = convNodeJSON(root.getString("right"));


        int value = root.getInt("value");

        return new BinaryHeapNode(value, left, right);
    }
}
