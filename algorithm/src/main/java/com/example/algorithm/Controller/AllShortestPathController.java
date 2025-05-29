package com.example.algorithm.Controller;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.Graph.AllShortestPath.AllShortestPathService;
import com.example.algorithm.Graph.AllShortestPath.BellmanFord.BellmanFordService;
import com.example.algorithm.Graph.AllShortestPath.Dijkstra.AllShortestPathDijkstraService;
import com.example.algorithm.ResponseTypes.GraphResponse;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/algos/allShortestPath/")
@CrossOrigin("*")
public class AllShortestPathController {
    private final AllShortestPathDijkstraService dijkstraService;
    private final BellmanFordService bellmanFordService;

    private final Logger logger = LoggerFactory.getLogger(AllShortestPathController.class);

    public AllShortestPathController(AllShortestPathDijkstraService dijkstraService, BellmanFordService bellmanFordService) {
        this.dijkstraService = dijkstraService;
        this.bellmanFordService = bellmanFordService;
    }

    @PostMapping(
            path = "/{searchType}/step"
    )
    public ResponseEntity<GraphResponse> step(@PathVariable("searchType") String searchType, RequestEntity<String> graph) {
        try {
            AllShortestPathService service = stringToService(searchType);

            logger.info("New AllShortestPath nextstep-request: " + graph.getBody());
            GraphResponse temp = service.step(graph.getBody());
            return new ResponseEntity<>(temp, HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("AllShortestPath step JSON failed: " + graph);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("AllShortestPath step failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{searchtype}/explanation")
    public ResponseEntity<Explanation> allShortestPathExplanation(@PathVariable("searchtype") String searchType) {
        logger.info("Requested Explanation AllShortestPath");
        try {
            AllShortestPathService service = stringToService(searchType);
            return new ResponseEntity<>(service.getExplanation(), HttpStatus.OK);
        } catch (IOException | ServiceNotFoundException e) {
            logger.error("AllShortestPath explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private AllShortestPathService stringToService(String service) throws ServiceNotFoundException {
        switch (service) {
            case "BellmanFord": return bellmanFordService;
            case "Dijkstra": return dijkstraService;
            default: throw new ServiceNotFoundException("Service: " + service + " Not Found");
        }
    }
}
