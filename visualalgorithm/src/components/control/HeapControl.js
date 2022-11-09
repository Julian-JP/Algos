import React, {useEffect, useState} from 'react';
import classes from "./HeapControl.module.css";
import useFetch from "../../hooks/useFetch";
import UndRedoFields from "../UI/UndRedoFields";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit";

const HeapControl = ({setEdges, setVertices, type}) => {

    const [heap, setHeap] = useState(null);
    const [addval, setAddval] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    const nodecolor = 'white';
    const linecolor = 'black';

    useEffect(() => {
        let edges = [];
        let vertices = [];
        drawSubtree(heap, 1, edges, vertices, nodecolor, linecolor, getDepth(heap), 0);
        setVertices(vertices);
        setEdges(edges);
    }, [heap]);


    const drawSubtree = (tree, numElemInLine, edges, vertices, color, lcolor, depth, curDepth) => {
        if (tree === null) {
            return;
        }
        let x1 = ((100 / (2 ** curDepth + 1)) * numElemInLine) + "%";
        let y1 = ((100 / (depth + 1)) * (curDepth + 1)) + "%";

        if (tree.left) {
            edges.push({
                type: "line",
                x1: x1,
                y1: y1,
                x2: ((100 / (2 ** (curDepth + 1) + 1)) * ((numElemInLine * 2) - 1)) + "%",
                y2: ((100 / (depth + 1)) * (curDepth + 2)) + "%",
                stroke: lcolor,
                directed: false
            })
            drawSubtree(tree.left, (numElemInLine * 2) - 1, edges, vertices, color, lcolor, depth, curDepth + 1);
        }
        if (tree.right) {
            edges.push({
                type: "line",
                x1: x1,
                y1: y1,
                x2: ((100 / (2**(curDepth+1) + 1)) * (numElemInLine * 2)) + "%",
                y2: ((100 / (depth + 1)) * (curDepth+2)) + "%",
                stroke: lcolor,
                directed: false
            })
            drawSubtree(tree.right, (numElemInLine * 2), edges, vertices, color, lcolor, depth, curDepth + 1);
        }

        vertices.push({
            type: "circle",
            x: ((100 / (2 ** curDepth + 1)) * numElemInLine) + "%",
            y: ((100 / (depth + 1)) * (curDepth + 1)) + "%",
            fill: color,
            stroke: "black",
            textFill: "black",
            value: tree.value,
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

    const onAdd = (event) => {
        event.preventDefault();
        if (addval === '') return;
        if (heap == null) {
            const createHeapFromJSON = (response) => {
                setHeap(response.root);
                setUndoStack((old) => [...old, null]);
                setRedoStack([]);
            }
            sendRequest({
                url: 'https://julian-laux.de:8080/algos/' + type + '/new/' + addval,
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }, createHeapFromJSON);
        } else {
            const createHeapFromJSON = (response) => {
                if (JSON.stringify(heap) !== JSON.stringify(response.root)) {
                    setUndoStack((old) => [...old, heap]);
                    setRedoStack([]);
                }
                setHeap(response.root);
            }

            sendRequest({
                url: 'https://julian-laux.de:8080/algos/' + type + '/insert/' + addval,
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: {root: heap}
            }, createHeapFromJSON);
        }
    }

    const onRemove = (event) => {
        event.preventDefault();

        const createHeapFromJSON = (response) => {
            if (heap != null && JSON.stringify(heap) !== JSON.stringify(response.root)) {
                setUndoStack((old) => [...old, heap]);
                setRedoStack([]);
                setHeap(response.root);
            }
        }
        sendRequest({
            url: 'https://julian-laux.de:8080/algos/' + type + '/remove',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {root: heap}
        }, createHeapFromJSON);
    }

    return (
        <div className={classes.container}>
            <MultidataInputWithSubmit
                className={classes.undoRedo}
                onSubmit={onAdd}
                btnLabel={"Add"}
                data={
                    [{
                        type: "number", onChange: (val) => setAddval(val.target.value), label: "add", noLabel: true
                    }]
                }
            />
            <form onSubmit={onRemove}>
                <button type="submit" className={classes.remove}>Remove</button>
            </form>
            <UndRedoFields
                currentDrawing={heap}
                undoStackState={[undoStack, setUndoStack]}
                redoStackState={[redoStack, setRedoStack]}
                undoDisable={undoStack.length === 0}
                redoDisable={redoStack.length === 0}
                setCurrent={setHeap}
            />
        </div>
    );
};

export default HeapControl;
