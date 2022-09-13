package com.example.algorithm.Controller;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.SearchTrees.AVLTree.AVLTreeService;
import com.example.algorithm.SearchTrees.BinarySearchTree.BinarySearchTreeService;
import com.example.algorithm.SearchTrees.SearchTree;
import com.example.algorithm.SearchTrees.SearchTreeService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/algos/SearchTrees")
@CrossOrigin("*")
public class SearchTreeController {

    private final BinarySearchTreeService bstService;
    private final AVLTreeService avlService;
    private final RedBlackService redBlackService;
    private final Logger logger = LoggerFactory.getLogger(SearchTreeController.class);

    public SearchTreeController(BinarySearchTreeService bstService, AVLTreeService avlService, RedBlackService redBlackService) {
        this.bstService = bstService;
        this.avlService = avlService;
        this.redBlackService = redBlackService;
    }

    @PostMapping(
            path = "/{tree}/insert/{value}"
    )
    public ResponseEntity<SearchTree> insert(@PathVariable("value") int value, @PathVariable("tree") String treeType, RequestEntity<String> tree) {
        try {
            SearchTreeService service = stringToService(treeType);

            logger.info("New BinarySearchtree insert-request: " + value + " in " + tree.getBody());
            return new ResponseEntity<>(service.insert(value, tree.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("BinarySearchtree insert JSON failed: " + tree);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("SearchTree insert failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(
            path = "/{tree}/remove/{value}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SearchTree> BSTremove(@PathVariable("value") int value, @PathVariable("tree") String treeType, RequestEntity<String> tree) {
        logger.info("New BinarySearchtree remove-request: " + value + " in " + tree.getBody());
        try {
            SearchTreeService service = stringToService(treeType);
            return new ResponseEntity<>(service.remove(value, tree.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("BinarySearchtree remove JSON failed: " + tree);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("SearchTree remove failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{tree}/new/{value}")
    public ResponseEntity<SearchTree> BSTcreate(@PathVariable("value") int value, @PathVariable("tree") String treeType) {
        logger.info("New BinarySearchtree create-request: " + value);
        SearchTreeService service = null;
        try {
            service = stringToService(treeType);
        } catch (ServiceNotFoundException e) {
            logger.error("SearchTree insert failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.create(value), HttpStatus.OK);
    }

    @GetMapping("/{tree}/explanation")
    public ResponseEntity<Explanation> BSTgetExpl(@PathVariable("tree") String treeType) {
        logger.info("Requested Explanation BinarySearchtree");
        try {
            SearchTreeService service = stringToService(treeType);
            return new ResponseEntity<>(service.getExplanation(), HttpStatus.OK);
        } catch (IOException e) {
            logger.error("SearchTree explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("SearchTree explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private SearchTreeService stringToService(String service) throws ServiceNotFoundException {
        switch (service) {
            case "BinarySearchTree": return bstService;
            case "AVLTree": return avlService;
            case "RedBlackTree": return redBlackService;
            default: throw new ServiceNotFoundException("Service: " + service + " Not Found");
        }
    }
}
