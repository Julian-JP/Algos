package com.example.algorithm.Controller;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.SearchTrees.BinarySearchTree.BinarySearchTree;
import com.example.algorithm.SearchTrees.BinarySearchTree.BinarySearchTreeService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/algos")
@CrossOrigin("*")
public class BinaryTreeController {

    private final BinarySearchTreeService bstService;
    private final Logger logger = LoggerFactory.getLogger(BinaryTreeController.class);
    @Autowired
    public BinaryTreeController(BinarySearchTreeService bstService) {
        this.bstService = bstService;
    }

    @PostMapping(
            path = "/BinarySearchTree/insert/{value}"
    )
    public ResponseEntity<BinarySearchTree> BSTinsert(@PathVariable("value") int value, RequestEntity<String> tree) {
        try {
            logger.info("New BinarySearchtree insert-request: " + value + " in " +  tree);
            return new ResponseEntity<>(bstService.insert(value, tree.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("BinarySearchtree insert JSON failed: " + tree);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(
            path = "/BinarySearchTree/remove/{value}",
            produces = MediaType.APPLICATION_JSON_VALUE
        )
    public ResponseEntity<BinarySearchTree> BSTremove(@PathVariable("value") int value, RequestEntity<String> tree) {
        logger.info("New BinarySearchtree remove-request: " + value + " in " + tree.getBody());
        try {
            return new ResponseEntity<>(bstService.remove(value, tree.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("BinarySearchtree remove JSON failed: " + tree);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/BinarySearchTree/new/{value}")
    public ResponseEntity<BinarySearchTree> BSTcreate(@PathVariable("value") int value) {
        logger.info("New BinarySearchtree create-request: " + value);
        return new ResponseEntity<>(bstService.create(value), HttpStatus.OK);
    }

    @GetMapping("BinarySearchTree/explanation")
    public ResponseEntity<Explanation> BSTgetExpl() {
        logger.info("Requested Explanation BinarySearchtree");
        try {
            return new ResponseEntity<>(bstService.getExplanation(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
