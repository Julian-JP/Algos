package com.example.algorithm.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/algos/all")
@CrossOrigin("*")
public class AlgorithmListController {
    private final Logger logger = LoggerFactory.getLogger(AlgorithmListController.class);

    public static class Algorithm {
        private String name;
        private String url;
        private String type;
        private long id;

        public Algorithm(String name, String url, String type, long id) {
            this.name = name;
            this.url = url;
            this.type = type;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public long getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public String getType() {
            return type;
        }
    }

    public static class Category {
        private String name;
        private String[] algorithmUrls;
        private long id;

        public Category(String name, String[] childrenUrl, long id) {
            this.name = name;
            this.algorithmUrls = childrenUrl;
            this.id = id;
        }

        public String[] getalgorithmUrls() {
            return algorithmUrls;
        }

        public String getName() {
            return name;
        }

        public long getId() {
            return id;
        }
    }

    @GetMapping("/AlgorithmCateogories")
    public ResponseEntity<List<Category>> getCategories() {
        logger.info("Requested list of all Categories of Algorithms");
        List<Category> categories = new ArrayList<>();
        String[] searchTreeAlgos = {"/SearchTrees/BinarySearchTree", "/SearchTrees/AVLTree", "/SearchTrees/RedBlackTree"};
        String[] heapAlgos = {"/Heaps/BinaryHeap"};
        String[] pathFindingAlgos = {"/PathFinding/BFS", "/PathFinding/DFS", "/PathFinding/Dijkstra"};
        String[] minimalSpanningTreeAlgos = {"/MinimalSpanningTree/JarnikPrim", "/MinimalSpanningTree/Kruskal"};
        Category searchTrees = new Category("Searchtrees", searchTreeAlgos, 0);
        Category minHeap = new Category("Heaps", heapAlgos, 1);
        Category pathFinding = new Category("PathFinding", pathFindingAlgos, 2);
        Category minimalSpanningTree = new Category("MinimalSpanningTree", minimalSpanningTreeAlgos, 3);

        categories.add(searchTrees);
        categories.add(minHeap);
        categories.add(pathFinding);
        categories.add(minimalSpanningTree);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/SearchTrees/BinarySearchTree")
    public ResponseEntity<Algorithm> getBinarySearchTree() {
        logger.info("Requested information to: Binary Search Tree");
        Algorithm binarySearchTree = new Algorithm("Binary Search Tree", "SearchTrees/BinarySearchTree", "binarytree", 0);
        return new ResponseEntity<>(binarySearchTree, HttpStatus.OK);
    }

    @GetMapping("/SearchTrees/AVLTree")
    public ResponseEntity<Algorithm> getAVLTree() {
        logger.info("Requested information to: AVL Tree");
        Algorithm avlTree = new Algorithm("AVL Tree", "SearchTrees/AVLTree", "binarytree", 1);
        return new ResponseEntity<>(avlTree, HttpStatus.OK);
    }

    @GetMapping("/SearchTrees/RedBlackTree")
    public ResponseEntity<Algorithm> getRedBlackTree() {
        logger.info("Requested information to: Red-Black Tree");
        Algorithm redBlackTree = new Algorithm("Red-Black Tree", "SearchTrees/RedBlackTree", "binarytree", 2);
        return new ResponseEntity<>(redBlackTree, HttpStatus.OK);
    }

    @GetMapping("/Heaps/BinaryHeap")
    public ResponseEntity<Algorithm> getBinaryHeap() {
        logger.info("Requested information to: BinaryHeap");
        Algorithm binaryHeap = new Algorithm("Binary Min-Heap", "Heaps/BinaryHeap", "binaryheap", 0);
        return new ResponseEntity<>(binaryHeap, HttpStatus.OK);
    }

    @GetMapping("/PathFinding/BFS")
    public ResponseEntity<Algorithm> getBFS() {
        logger.info("Requested information to: Breadth-First Search");
        Algorithm bfs = new Algorithm("Breadth-First Search", "PathFinding/BFS", "pathfindingGraph", 0);
        return new ResponseEntity<>(bfs, HttpStatus.OK);
    }

    @GetMapping("/PathFinding/DFS")
    public ResponseEntity<Algorithm> getDFS() {
        logger.info("Requested information to: Depth-First Search");
        Algorithm dfs = new Algorithm("Depth-First Search", "PathFinding/DFS", "pathfindingGraph", 1);
        return new ResponseEntity<>(dfs, HttpStatus.OK);
    }

    @GetMapping("/PathFinding/Dijkstra")
    public ResponseEntity<Algorithm> getDijkstra() {
        logger.info("Requested information to: Dijkstra");
        Algorithm dfs = new Algorithm("Dijkstra's algorithm", "PathFinding/Dijkstra", "weightedNonNegativePathFindingGraph", 1);
        return new ResponseEntity<>(dfs, HttpStatus.OK);
    }

    @GetMapping("/MinimalSpanningTree/Kruskal")
    public ResponseEntity<Algorithm> getkruskal() {
        logger.info("Requested information to: Kruskal");
        Algorithm dfs = new Algorithm("Kruskal's algorithm", "MinimalSpanningTree/Kruskal", "weightedPathFindingGraph", 0);
        return new ResponseEntity<>(dfs, HttpStatus.OK);
    }

    @GetMapping("/MinimalSpanningTree/JarnikPrim")
    public ResponseEntity<Algorithm> getJarnikPrim() {
        logger.info("Requested information to: JarnikPrim");
        Algorithm dfs = new Algorithm("Jarník-Prim algorithm", "MinimalSpanningTree/JarnikPrim", "minimalSpanningTree", 0);
        return new ResponseEntity<>(dfs, HttpStatus.OK);
    }
}
