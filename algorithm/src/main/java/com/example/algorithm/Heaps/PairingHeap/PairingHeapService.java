package com.example.algorithm.Heaps.PairingHeap;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Heaps.BinaryHeap.BinaryHeap;
import com.example.algorithm.Heaps.BinaryHeap.BinaryHeapNode;
import com.example.algorithm.Heaps.HeapService;
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
public class PairingHeapService extends HeapService {
    @Override
    public TreeResponse insert(int value, String heapString) throws JSONException {
        PairingHeap heap = convJSON(heapString);
        heap.add(value);
        return toResponse(heap.getRoot());
    }

    @Override
    public TreeResponse create(int value) {
        return toResponse(new PairingHeapNode(value));
    }

    @Override
    public TreeResponse getMinimum(String heapString) throws JSONException {
        PairingHeap heap = convJSON(heapString);
        heap.getMinimum();
        return toResponse(heap.getRoot());
    }

    @Override
    public Explanation getExplanation() throws IOException {
        String explanation = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:explanations/pairingHeap.txt").toPath()));
        return new Explanation(explanation);
    }

    private PairingHeap convJSON(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        if (root.getString("root").equals("null")) {
            return new PairingHeap();
        }
        return new PairingHeap(convNodeJSON(new JSONObject(root.getString("root"))));
    }

    private PairingHeapNode convNodeJSON(JSONObject root) throws JSONException {
        JSONArray childrenJSON = root.getJSONArray("children");

        ArrayList<PairingHeapNode> children = new ArrayList<>();
        for (int i = 0; i < childrenJSON.length(); i++) {
            JSONObject child = childrenJSON.getJSONObject(i);
            children.add(convNodeJSON(child));
        }

        return new PairingHeapNode(children, root.getInt("value"));
    }

    private TreeResponse toResponse(PairingHeapNode root) {
        return new TreeResponse(toResponseRec(root));
    }

    private TreeNodeResponse toResponseRec(PairingHeapNode root) {
        if (root == null) {
            return null;
        }

        ArrayList<TreeNodeResponse> children = new ArrayList<>();
        for (int i=0; i < root.getChildren().size(); i++) {
            children.add(toResponseRec(root.getChildren().get(i)));
        }

        return new TreeNodeResponse(children, String.valueOf(root.getVal()));
    }
}
