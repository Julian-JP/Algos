package com.example.algorithm;

import com.example.algorithm.SearchTrees.BinarySearchTree;
import com.example.algorithm.SearchTrees.BinarySearchTreeService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

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

    @PostMapping(
            path = "/bst/insert/{value}"
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
            path = "/bst/remove/{value}",
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

    @PostMapping("/bst/new/{value}")
    public ResponseEntity<BinarySearchTree> BSTcreate(@PathVariable("value") int value) {
        logger.info("New BinarySearchtree create-request: " + value);
        return new ResponseEntity<>(bstService.create(value), HttpStatus.OK);
    }
}
