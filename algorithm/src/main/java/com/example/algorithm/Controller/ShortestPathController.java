package com.example.algorithm.Controller;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.GraphResponse;
import com.example.algorithm.Graph.ShortestPath.BreadthFirstSearch.BreadthFirstSearchService;
import com.example.algorithm.Graph.ShortestPath.ShortestPathService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/algos/ShortestPath/")
@CrossOrigin("*")
public class ShortestPathController {
    private final BreadthFirstSearchService bfService;
    private final Logger logger = LoggerFactory.getLogger(SearchTreeController.class);

    public ShortestPathController(BreadthFirstSearchService bfService) {
        this.bfService = bfService;
    }

    @PostMapping(
            path = "/{searchType}/step"
    )
    public ResponseEntity<GraphResponse> step(@PathVariable("searchType") String searchType, RequestEntity<String> graph) {
        try {
            ShortestPathService service = stringToService(searchType);

            logger.info("New ShortestPath nextstep-request: " + graph.getBody());
            return new ResponseEntity<>(service.step(graph.getBody()), HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("ShortestPath step JSON failed: " + graph);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("ShortestPath step failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{searchtype}/explanation")
    public ResponseEntity<Explanation> ShortestpathgetExpl(@PathVariable("searchtype") String searchType) {
        logger.info("Requested Explanation ShortestPath");
        try {
            ShortestPathService service = stringToService(searchType);
            return new ResponseEntity<>(service.getExplanation(), HttpStatus.OK);
        } catch (IOException e) {
            logger.error("SearchTree explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("SearchTree explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private ShortestPathService stringToService(String service) throws ServiceNotFoundException {
        switch (service) {
            case "BFS": return bfService;
            default: throw new ServiceNotFoundException("Service: " + service + " Not Found");
        }
    }
}
