import React, {useEffect, useState} from 'react';
import classes from "./SearchTreeControl.module.css";
import useFetch from "../../hooks/useFetch";
import UndoRedoFields from "../UI/UndoRedoFields.jsx";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit.jsx";
import configData from "@/configs/config.json"
import drawTree from "@/components/control/TreeDrawComponent.jsx";

const SearchTreeControl = ({svgWidth, svgHeight, type, graphDispatch}) => {

    const [tree, setTree] = useState(null);
    const [addval, setAddval] = useState('');
    const [removeval, setRemoveval] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        let edges = [];
        let vertices = [];
        drawTree(tree, edges, vertices, svgWidth, svgHeight);
        graphDispatch({type: 'redraw_entirely', vertices: vertices, edges: edges});
    }, [tree]);

    const handleAdd = (event) => {
        event.preventDefault();
        if (addval === '') return;
        if (tree == null) {
            const createTreeFromJSON = (response) => {
                setTree(response);
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
                if (JSON.stringify(tree) !== JSON.stringify(response)) {
                    setUndoStack((old) => [...old, tree]);
                    setRedoStack([]);
                }
                setTree(response);
            }

            sendRequest({
                url: configData.BACKEND_URL + type + '/insert/' + addval,
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: tree
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
                setTree(response);
            }
        }
        sendRequest({
            url: configData.BACKEND_URL + type + '/remove/' + removeval,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: tree
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
