package com.example.algorithm.Controller;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Heaps.HeapService;
import com.example.algorithm.ResponseTypes.TreeResponse;
import com.example.algorithm.Heaps.BinaryHeap.BinaryHeapService;
import com.example.algorithm.Heaps.PairingHeap.PairingHeapService;
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
    private final PairingHeapService pairingHeapService;

    private final Logger logger = LoggerFactory.getLogger(HeapController.class);

    public HeapController(BinaryHeapService binaryHeapService, PairingHeapService pairingHeapService) {
        this.binaryHeapService = binaryHeapService;
        this.pairingHeapService = pairingHeapService;
    }

    @PostMapping(
            path = "/{heap}/insert/{value}"
    )
    public ResponseEntity<TreeResponse> insert(@PathVariable("value") int value, @PathVariable("heap") String heapType, RequestEntity<String> heap) {
        try {
            HeapService service = stringToService(heapType);

            logger.info("New Heap insert-request: {} in {}", value, heap.getBody());
            return new ResponseEntity<>(service.insert(value, heap.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("Heap insert JSON failed: {}", heap);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("Heap insert failed: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(
            path = "/{tree}/remove",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TreeResponse> binaryHeapRemove(@PathVariable("tree") String treeType, RequestEntity<String> tree) {
        logger.info("New Heap remove-request: {}", tree.getBody());
        try {
            HeapService service = stringToService(treeType);
            return new ResponseEntity<>(service.getMinimum(tree.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("Heap remove JSON failed: {}", tree);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("Heap remove failed: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{heap}/new/{value}")
    public ResponseEntity<TreeResponse> binaryHeapCreate(@PathVariable("value") int value, @PathVariable("heap") String heapType) {
        logger.info("New Heap create-request: {}", value);
        HeapService service;
        try {
            service = stringToService(heapType);
        } catch (ServiceNotFoundException e) {
            logger.error("Heap insert failed: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.create(value), HttpStatus.OK);
    }

    @GetMapping("/{heap}/explanation")
    public ResponseEntity<Explanation> binaryHeapGetExpl(@PathVariable("heap") String heapType) {
        logger.info("Requested Explanation Heap");
        try {
            HeapService service = stringToService(heapType);
            return new ResponseEntity<>(service.getExplanation(), HttpStatus.OK);
        } catch (IOException | ServiceNotFoundException e) {
            logger.error("Heap explanation failed: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private HeapService stringToService(String service) throws ServiceNotFoundException {
        return switch (service) {
            case "BinaryHeap" -> binaryHeapService;
            case "PairingHeap" -> pairingHeapService;
            default -> throw new ServiceNotFoundException("Service: " + service + " Not Found");
        };
    }
}
