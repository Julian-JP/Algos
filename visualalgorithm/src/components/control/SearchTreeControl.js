import React, {useEffect, useState} from 'react';
import classes from "./SearchTreeControl.module.css";
import useFetch from "../../hooks/useFetch";
import UndRedoFields from "../UI/UndRedoFields";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit";

const SearchTreeControl = ({setEdges, setVertices, type}) => {

    const [tree, setTree] = useState(null);
    const [addval, setAddval] = useState('');
    const [removeval, setRemoveval] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    const nodecolor = 'white';
    const linecolor = 'black';

    useEffect(() => {
        let edges = [];
        let vertices = [];
        drawSubtree(tree, 1, edges, vertices, nodecolor, linecolor, getDepth(tree), 0);
        setVertices(vertices);
        setEdges(edges);
    }, [tree]);

    const drawSubtree = (tree, numElemInLine, edges, vertices, color, lcolor, depth, curDepth) => {
        if (tree === null) {
            return;
        }
        if (tree.left) {
            edges.push({
                type: "line",
                x1: ((100 / (2 ** curDepth + 1)) * numElemInLine) + "%",
                y1: ((100 / (depth + 1)) * (curDepth + 1)) + "%",
                x2: ((100 / (2 ** (curDepth + 1) + 1)) * ((numElemInLine * 2) - 1)) + "%",
                y2: ((100 / (depth + 1)) * (curDepth + 2)) + "%",
                stroke: lcolor
            })
            drawSubtree(tree.left, (numElemInLine * 2) - 1, edges, vertices, color, lcolor, depth, curDepth + 1);
        }
        if (tree.right) {
            edges.push({
                type: "line",
                x1: ((100 / (2**curDepth + 1)) * numElemInLine) + "%",
                y1: ((100 / (depth + 1)) * (curDepth+1)) + "%",
                x2: ((100 / (2**(curDepth+1) + 1)) * (numElemInLine * 2)) + "%",
                y2: ((100 / (depth + 1)) * (curDepth+2)) + "%",
                stroke: lcolor
            })
            drawSubtree(tree.right, (numElemInLine * 2), edges, vertices, color, lcolor, depth, curDepth + 1);
        }

        vertices.push({
            type: "circle",
            x: ((100 / (2 ** curDepth + 1)) * numElemInLine) + "%",
            y: ((100 / (depth + 1)) * (curDepth + 1)) + "%",
            fill: tree.color != null ? tree.color : color,
            stroke: "black",
            textFill: tree.color !== "black" ? "black" : "white",
            value: tree.value === null ? "NIL" : tree.value,
            draggable: false
        });
    }

    const getDepth = (tree) => {
        if (tree === null) {
            return 0;
        }
        let depth1 = 0;
        let depth2 = 0;
        if (tree.left) {
            depth1 = getDepth(tree.left)
        }
        if (tree.right) {
            depth2 = getDepth(tree.right);
        }
        return (depth1 > depth2 ? depth1 : depth2) + 1;
    }

    const handleAdd = (event) => {
        event.preventDefault();
        if (addval === '') return;
        if (tree == null) {
            const createTreeFromJSON = (response) => {
                setTree(response.root);
                setUndoStack((old) => [...old, null]);
                setRedoStack([]);
            }
            sendRequest({
                url: 'https://julian-laux.de:8080/algos/' + type + '/new/' + addval,
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }, createTreeFromJSON);
        } else {
            const createTreeFromJSON = (response) => {
                if (JSON.stringify(tree) !== JSON.stringify(response.root)) {
                    setUndoStack((old) => [...old, tree]);
                    setRedoStack([]);
                }
                setTree(response.root);
            }

            sendRequest({
                url: 'https://julian-laux.de:8080/algos/' + type + '/insert/' + addval,
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: {root: tree}
            }, createTreeFromJSON);
        }
    }

    const handleRemove = (event) => {
        event.preventDefault();
        if (removeval === '' || tree == null) return;

        const createTreeFromJSON = (response) => {
            if (tree != null && JSON.stringify(tree) !== JSON.stringify(response.root)) {
                setUndoStack((old) => [...old, tree]);
                setRedoStack([]);
                setTree(response.root);
            }
        }
        sendRequest({
            url: 'https://julian-laux.de:8080/algos/' + type + '/remove/' + removeval,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {root: tree}
        }, createTreeFromJSON);
    }

    return (
        <div className={classes.container}>
            <MultidataInputWithSubmit
                className={classes.undoRedo}
                onSubmit={handleAdd}
                btnLabel={"Add"}
                data={
                    [{
                        type: "number", onChange: (val) => setAddval(val.target.value), label: "add", noLabel: true
                    }]
                }
            />
            <MultidataInputWithSubmit
                className={classes.undoRedo}
                onSubmit={handleRemove}
                btnLabel={"Remove"}
                data={
                    [{
                        type: "number",
                        onChange: (val) => setRemoveval(val.target.value),
                        label: "remove",
                        noLabel: true
                    }]
                }
            />
            <UndRedoFields
                className={classes.undoRedo}
                currentDrawing={tree}
                undoStackState={[undoStack, setUndoStack]}
                redoStackState={[redoStack, setRedoStack]}
                undoDisable={undoStack.length === 0}
                redoDisable={redoStack.length === 0}
                setCurrent={setTree}
            />
        </div>
    );
};

export default SearchTreeControl;
