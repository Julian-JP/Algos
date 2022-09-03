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

    public class Algorithm {
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

    public class Category {
        private String name;
        private String[] algorithmUrls;
        private long id;

        public Category(String name, String childrenUrl[], long id) {
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
        String[] shortestPathAlgos = {"/ShortestPath/BFS", "/ShortestPath/DFS", "/ShortestPath/AStar"};
        Category searchTrees = new Category("Searchtrees", searchTreeAlgos, 0);
        Category minHeap = new Category("Heaps", heapAlgos, 1);
        Category shortestPath = new Category("Shortest Path", shortestPathAlgos, 2);

        categories.add(searchTrees);
        categories.add(minHeap);
        categories.add(shortestPath);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/SearchTrees/BinarySearchTree")
    public ResponseEntity<Algorithm> getBinarySearchTree() {
        logger.info("Requested information to: Binary Search Tree");
        Algorithm binarySearchTree = new Algorithm("Binary Search Tree", "SearchTrees/BinarySearchTree", "binarytree",0);
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

    @GetMapping("/ShortestPath/BFS")
    public ResponseEntity<Algorithm> getBFS() {
        logger.info("Requested information to: Breadth-First Search");
        Algorithm bfs = new Algorithm("Breadth-First Search", "ShortestPath/BFS", "graph", 0);
        return new ResponseEntity<>(bfs, HttpStatus.OK);
    }

    @GetMapping("/ShortestPath/DFS")
    public ResponseEntity<Algorithm> getDFS() {
        logger.info("Requested information to: Depth-First Search");
        Algorithm dfs = new Algorithm("Depth-First Search", "ShortestPath/DFS", "graph", 1);
        return new ResponseEntity<>(dfs, HttpStatus.OK);
    }

    @GetMapping("/ShortestPath/AStar")
    public ResponseEntity<Algorithm> getAStar() {
        logger.info("Requested information to: Depth-First Search");
        Algorithm astar = new Algorithm("A-Star Search", "ShortestPath/AStar", "graph", 1);
        return new ResponseEntity<>(astar, HttpStatus.OK);
    }
}
