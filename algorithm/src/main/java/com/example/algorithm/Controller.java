package com.example.algorithm;

import com.example.algorithm.SearchTrees.BinarySearchTree;
import com.example.algorithm.SearchTrees.BinarySearchTreeService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/algos")
@CrossOrigin("*")
public class Controller {

    private final BinarySearchTreeService bstService;
    private final Logger logger = LoggerFactory.getLogger(Controller.class);
    @Autowired
    public Controller(BinarySearchTreeService bstService) {
        this.bstService = bstService;
    }

    @GetMapping("/bst/insert/{value}")
    public BinarySearchTree BSTinsert(@PathVariable("value") int value, RequestEntity<String> tree) {
        try {
            logger.info("New BinarySearchtree insert-request: " + value + " in " +  tree.getBody());
            return bstService.insert(value, tree.getBody());
        } catch (JSONException e) {
            logger.error("BinarySearchtree insert JSON failed!");
            return null;
        }
    }

    @GetMapping("/bst/remove/{value}")
    public BinarySearchTree BSTremove(@PathVariable("value") int value, RequestEntity<String> tree) {
        logger.info("New BinarySearchtree remove-request: " + value + " in " + tree.getBody());
        return bstService.remove(value, tree.getBody());
    }

    @GetMapping("/bst/new/{value}")
    public BinarySearchTree BSTcreate(@PathVariable("value") int value) {
        logger.info("New BinarySearchtree create-request: " + value);
        return bstService.create(value);
    }
}
