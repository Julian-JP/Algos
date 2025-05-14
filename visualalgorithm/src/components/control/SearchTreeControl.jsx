import React, {useEffect, useState} from 'react';
import classes from "./SearchTreeControl.module.css";
import useFetch from "../../hooks/useFetch";
import UndoRedoFields from "../UI/UndoRedoFields.jsx";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit.jsx";
import configData from "@/configs/config.json"

const SearchTreeControl = ({svgWidth, svgHeight, type, graphDispatch}) => {

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
        graphDispatch({type: 'redraw', vertices: vertices, edges: edges});
    }, [tree]);

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

        color = tree.color === undefined ? color : tree.color
        let strokeColor = color === "black" ? "white" : "black"

        vertices.push({
            type: "circle",
            x: (((svgWidth - 20) / (2 ** curDepth + 1)) * numElemInLine) + 10,
            y: (((svgHeight - 20) / (depth + 1)) * (curDepth + 1))  + 10,
            fill: color,
            stroke: strokeColor,
            textFill: strokeColor,
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
                url: configData.BACKEND_URL + type + '/new/' + addval,
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
                url: configData.BACKEND_URL + type + '/insert/' + addval,
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
            url: configData.BACKEND_URL + type + '/remove/' + removeval,
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
            <UndoRedoFields
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
