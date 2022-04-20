package com.example.algorithm.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/algos")
@CrossOrigin("*")
public class AlgorithmListController {
    private final Logger logger = LoggerFactory.getLogger(AlgorithmListController.class);
    public class Algorithm {
        private String name;
        private long id;

        public Algorithm(String name, long id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public long getId() {
            return id;
        }
    }

    @GetMapping("/SearchTreeAlogrithms")
    public ResponseEntity<List<Algorithm>> getAlgorithms() {
        logger.info("Requested list of SearchTreeAlogrithms");
        List<Algorithm> algoList = new ArrayList();
        Algorithm binarySearchTree = new Algorithm("Binary Search Tree", 0);
        Algorithm avlTree = new Algorithm("AVL Tree", 1);
        algoList.add(avlTree);
        algoList.add(binarySearchTree);
        return new ResponseEntity<>(algoList, HttpStatus.OK);
    }
}
