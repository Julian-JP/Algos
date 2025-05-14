package com.example.algorithm.config;

import com.example.algorithm.SearchTrees.AVLTree.AVLTreeNode;
import com.example.algorithm.SearchTrees.BinarySearchTree.BSTNode;
import com.example.algorithm.SearchTrees.BinarySearchTreeNodeMixin;
import com.example.algorithm.SearchTrees.RedBlackTree.RedBlackTreeNode;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.mixIn(BSTNode.class, BinarySearchTreeNodeMixin.class);
            builder.mixIn(AVLTreeNode.class, BinarySearchTreeNodeMixin.class);
            builder.mixIn(RedBlackTreeNode.class, BinarySearchTreeNodeMixin.class);
        };
    }
}
