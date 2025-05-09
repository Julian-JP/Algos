import React, {useEffect, useState} from 'react';
import classes from "./HeapControl.module.css";
import useFetch from "../../hooks/useFetch";
import UndoRedoFields from "../UI/UndoRedoFields.jsx";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit.jsx";
import configData from "@/configs/config.json"

const HeapControl = ({svgWidth, svgHeight, graphDispatch, type}) => {

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
        graphDispatch({type: 'redraw', vertices: vertices, edges: edges});
    }, [heap]);


    const drawSubtree = (tree, numElemInLine, edges, vertices, color, lcolor, depth, curDepth) => {
        if (tree === null) {
            return;
        }

        if (tree.left) {
            let newNumElemInLine = (numElemInLine * 2) - 1;
            let newCurDepth = curDepth + 1;
            edges.push({
                type: "line",
                from: curDepth + "," + numElemInLine,
                to: newCurDepth + "," + newNumElemInLine,
                id: "L2" + curDepth + "," + numElemInLine,
                stroke: lcolor,
                directed: false
            })
            drawSubtree(tree.left, newNumElemInLine, edges, vertices, color, lcolor, depth, newCurDepth);
        }
        if (tree.right) {
            let newNumElemInLine = (numElemInLine * 2);
            let newCurDepth = curDepth + 1;
            edges.push({
                type: "line",
                from: curDepth + "," + numElemInLine,
                to: newCurDepth + "," + newNumElemInLine,
                id: "L1" + curDepth + "," + numElemInLine,
                stroke: lcolor,
                directed: false
            })
            drawSubtree(tree.right, newNumElemInLine, edges, vertices, color, lcolor, depth, newCurDepth);
        }

        vertices.push({
            type: "circle",
            x: (((svgWidth - 20) / (2 ** curDepth + 1)) * numElemInLine) + 10,
            y: (((svgHeight - 20) / (depth + 1)) * (curDepth + 1))  + 10,
            fill: color,
            stroke: "black",
            textFill: "black",
            value: tree.value,
            id: curDepth + "," + numElemInLine,
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
                url: configData.BACKEND_URL + type + '/new/' + addval,
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
                url: configData.BACKEND_URL + type + '/insert/' + addval,
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
            url: configData.BACKEND_URL + type + '/remove',
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
                        type: "number",
                        onChange: (val) => setAddval(val.target.value),
                        label: "add",
                        noLabel: true
                    }]
                }
            />
            <form onSubmit={onRemove}>
                <button type="submit" className={classes.remove}>Remove</button>
            </form>
            <UndoRedoFields
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
