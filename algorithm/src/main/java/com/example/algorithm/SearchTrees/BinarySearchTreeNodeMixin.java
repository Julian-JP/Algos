package com.example.algorithm.SearchTrees;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class BinarySearchTreeNodeMixin {

    @JsonProperty("left")
    abstract BinarySearchTreeNodeMixin getLeft();

    @JsonProperty("right")
    abstract BinarySearchTreeNodeMixin getRight();

    @JsonProperty("value")
    abstract Integer getValue();

    @JsonProperty("color")
    abstract String getColor();

    @JsonCreator
    public BinarySearchTreeNodeMixin(
            @JsonProperty("left") BinarySearchTreeNodeMixin left,
            @JsonProperty("right") BinarySearchTreeNodeMixin right,
            @JsonProperty("value") Integer value,
            @JsonProperty("color") String color
    ) {}
}
