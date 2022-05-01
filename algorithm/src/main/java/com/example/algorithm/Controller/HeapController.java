package com.example.algorithm.Controller;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Heaps.BinaryHeap;
import com.example.algorithm.Heaps.BinaryHeapNode;
import com.example.algorithm.Heaps.BinaryHeapResponse;
import com.example.algorithm.Heaps.BinaryHeapService;
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
@RequestMapping("/algos/Heaps")
@CrossOrigin("*")
public class HeapController {

    private final BinaryHeapService binaryHeapService;
    private final Logger logger = LoggerFactory.getLogger(HeapController.class);

    public HeapController(BinaryHeapService binaryHeapService) {
        this.binaryHeapService = binaryHeapService;
    }

    @PostMapping(
            path = "/{heap}/insert/{value}"
    )
    public ResponseEntity<BinaryHeapResponse> insert(@PathVariable("value") int value, @PathVariable("heap") String heapType, RequestEntity<String> heap) {
        try {
            BinaryHeapService service = stringToService(heapType);

            logger.info("New Heap insert-request: " + value + " in " + heap.getBody());
            return new ResponseEntity<>(service.insert(value, heap.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("Heap insert JSON failed: " + heap);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("Heap insert failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(
            path = "/{tree}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BinaryHeapResponse> BSTremove(@PathVariable("tree") String treeType, RequestEntity<String> tree) {
        logger.info("New Heap remove-request: " + tree.getBody());
        try {
            BinaryHeapService service = stringToService(treeType);
            return new ResponseEntity<>(service.getMinimum(tree.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("Heap remove JSON failed: " + tree);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("Heap remove failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{heap}/new/{value}")
    public ResponseEntity<BinaryHeapResponse> BSTcreate(@PathVariable("value") int value, @PathVariable("heap") String heapType) {
        logger.info("New Heap create-request: " + value);
        BinaryHeapService service = null;
        try {
            service = stringToService(heapType);
        } catch (ServiceNotFoundException e) {
            logger.error("Heap insert failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.create(value), HttpStatus.OK);
    }

    @GetMapping("/{heap}/explanation")
    public ResponseEntity<Explanation> BSTgetExpl(@PathVariable("heap") String heapType) {
        logger.info("Requested Explanation Heap");
        try {
            BinaryHeapService service = stringToService(heapType);
            return new ResponseEntity<>(service.getExplanation(), HttpStatus.OK);
        } catch (IOException e) {
            logger.error("Heap explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("Heap explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private BinaryHeapService stringToService(String service) throws ServiceNotFoundException {
        switch (service) {
            case "BinaryHeap": return binaryHeapService;
            default: throw new ServiceNotFoundException("Service: " + service + " Not Found");
        }
    }
}
