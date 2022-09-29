package com.example.algorithm.Controller;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.GraphResponse;
import com.example.algorithm.Graph.PathFinding.BreadthFirstSearch.BreadthFirstSearchService;
import com.example.algorithm.Graph.PathFinding.DepthFirstSearch.DepthFirstSearchService;
import com.example.algorithm.Graph.PathFinding.DijkstraAlgorithm.DijkstraService;
import com.example.algorithm.Graph.PathFinding.PathFindingService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/algos/PathFinding/")
@CrossOrigin("*")
public class PathFindingController {
    private final BreadthFirstSearchService bfService;
    private final DepthFirstSearchService dfService;
    private final DijkstraService dijkstraService;
    private final Logger logger = LoggerFactory.getLogger(SearchTreeController.class);

    public PathFindingController(BreadthFirstSearchService bfService, DepthFirstSearchService dfService, DijkstraService dijkstraService) {
        this.bfService = bfService;
        this.dfService = dfService;
        this.dijkstraService = dijkstraService;
    }

    @PostMapping(
            path = "/{searchType}/step"
    )
    public ResponseEntity<GraphResponse> step(@PathVariable("searchType") String searchType, RequestEntity<String> graph) {
        try {
            PathFindingService service = stringToService(searchType);

            logger.info("New PathFinding nextstep-request: " + graph.getBody());
            GraphResponse temp = service.step(graph.getBody());
            return new ResponseEntity<>(temp, HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("PathFinding step JSON failed: " + graph);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("PathFinding step failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{searchtype}/explanation")
    public ResponseEntity<Explanation> PathFindinggetExpl(@PathVariable("searchtype") String searchType) {
        logger.info("Requested Explanation PathFinding");
        try {
            PathFindingService service = stringToService(searchType);
            return new ResponseEntity<>(service.getExplanation(), HttpStatus.OK);
        } catch (IOException e) {
            logger.error("PathFinding explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("PathFinding explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private PathFindingService stringToService(String service) throws ServiceNotFoundException {
        switch (service) {
            case "BFS": return bfService;
            case "DFS": return dfService;
            case "Dijkstra": return dijkstraService;
            default: throw new ServiceNotFoundException("Service: " + service + " Not Found");
        }
    }
}
