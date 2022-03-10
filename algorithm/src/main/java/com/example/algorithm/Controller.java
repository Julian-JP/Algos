package com.example.algorithm;

import com.example.algorithm.SearchTrees.BinarySearchTree;
import com.example.algorithm.SearchTrees.BinarySearchTreeService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/algos")
@CrossOrigin("*")
public class Controller {

    private final BinarySearchTreeService bstService;

    @Autowired
    public Controller(BinarySearchTreeService bstService) {
        this.bstService = bstService;
    }

    @GetMapping("/bst/insert/{value}")
    public BinarySearchTree BSTinsert(@PathVariable("value") int value, RequestEntity<String> tree) {
        try {
            return bstService.insert(value, tree.getBody());
        } catch (JSONException e) {
            return null;
        }
    }

    @GetMapping("/bst/remove/{value}")
    public BinarySearchTree BSTremove(@PathVariable("value") int value, @RequestParam("tree") String tree) {
        return bstService.remove(value, tree);
    }

    @GetMapping("/bst/new/{value}")
    public BinarySearchTree BSTcreate(@PathVariable("value") int value) {
        return bstService.create(value);
    }
}
