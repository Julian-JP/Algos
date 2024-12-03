package com.example.algorithm.Graph;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphNode {
    @Getter
    @Setter
    Double weight;

    @Getter
    @Setter
    String value;

    @Getter
    String id;

    public GraphNode(JSONObject object) throws JSONException {
        this.value = object.getString("value");
        this.id = object.getString("id");

        if (object.has("weight") && !object.isNull("weight")) {
            this.weight = object.getDouble("weight");
        }
    }

    public GraphNode(GraphNode graphNode) {
        this.weight = graphNode.weight;
        this.value = graphNode.value;
        this.id = graphNode.id;
    }
}
