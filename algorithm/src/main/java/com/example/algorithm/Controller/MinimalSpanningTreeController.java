package com.example.algorithm.Controller;

import com.example.algorithm.Explanation.Explanation;
import com.example.algorithm.ResponseTypes.GraphResponse;
import com.example.algorithm.Graph.MinimalSpanningTree.JarnikPrim.JarnikPrimService;
import com.example.algorithm.Graph.MinimalSpanningTree.Kruskal.KruskalService;
import com.example.algorithm.Graph.MinimalSpanningTree.MinimalSpanningTreeService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/algos/MinimalSpanningTree/")
@CrossOrigin("*")
public class MinimalSpanningTreeController {
    private final JarnikPrimService jarnikPrimService;
    private final KruskalService kruskalService;
    private final Logger logger = LoggerFactory.getLogger(MinimalSpanningTreeController.class);

    public MinimalSpanningTreeController(JarnikPrimService jarnikPrimService, KruskalService kruskalService) {
        this.jarnikPrimService = jarnikPrimService;
        this.kruskalService = kruskalService;
    }

    @PostMapping(
            path = "/{algorithm}/step"
    )
    public ResponseEntity<GraphResponse> step(@PathVariable("algorithm") String algorithm, RequestEntity<String> graph) {
        try {
            MinimalSpanningTreeService service = stringToService(algorithm);

            logger.info("New MinimalSpanningTree nextstep-request: " + graph.getBody());
            GraphResponse temp = service.step(graph.getBody());
            return new ResponseEntity<>(temp, HttpStatus.OK);
        } catch (JSONException e) {
            logger.error("MinimalSpanningTree step JSON failed: " + graph);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("MinimalSpanningTree step failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{algorithm}/explanation")
    public ResponseEntity<Explanation> PathFindinggetExpl(@PathVariable("algorithm") String algorithm) {
        logger.info("Requested Explanation PathFinding");
        try {
            MinimalSpanningTreeService service = stringToService(algorithm);
            return new ResponseEntity<>(service.getExplanation(), HttpStatus.OK);
        } catch (IOException e) {
            logger.error("MinimalSpanningTree explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ServiceNotFoundException e) {
            logger.error("MinimalSpanningTree explanation failed: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private MinimalSpanningTreeService stringToService(String service) throws ServiceNotFoundException {
        switch (service) {
            case "JarnikPrim": return jarnikPrimService;
            case "Kruskal": return kruskalService;
            default: throw new ServiceNotFoundException("Service: " + service + " Not Found");
        }
    }
}
