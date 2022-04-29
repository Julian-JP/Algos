package com.example.algorithm.Controller;

import com.example.algorithm.Heaps.BinaryHeap;
import com.example.algorithm.Heaps.BinaryHeapService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BinaryHeap> insert(@PathVariable("value") int value, @PathVariable("heap") String heapType, RequestEntity<String> heap) {
        try {
            BinaryHeapService service = stringToService(heapType);

            logger.info("New BinarySearchtree insert-request: " + value + " in " + heap.getBody());
            return new ResponseEntity<>(service.insert(value, heap.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("BinarySearchtree insert JSON failed: " + heap);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("Heap insert failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{heap}/new/{value}")
    public ResponseEntity<BinaryHeap> BSTcreate(@PathVariable("value") int value, @PathVariable("heap") String heapType) {
        logger.info("New BinarySearchtree create-request: " + value);
        BinaryHeapService service = null;
        try {
            service = stringToService(heapType);
        } catch (ServiceNotFoundException e) {
            logger.error("Heap insert failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.create(value), HttpStatus.OK);
    }

    private BinaryHeapService stringToService(String service) throws ServiceNotFoundException {
        switch (service) {
            case "BinaryHeap": return binaryHeapService;
            default: throw new ServiceNotFoundException("Service: " + service + " Not Found");
        }
    }
}
