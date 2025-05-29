import React, {useEffect, useState} from 'react';
import classes from "./HeapControl.module.css";
import useFetch from "../../hooks/useFetch";
import UndoRedoFields from "../UI/UndoRedoFields.jsx";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit.jsx";
import configData from "@/configs/config.json"
import drawTree from "@/components/control/TreeDrawComponent.jsx";

const HeapControl = ({svgWidth, svgHeight, graphDispatch, type}) => {

    const [heap, setHeap] = useState(null);
    const [addval, setAddval] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        let edges = [];
        let vertices = [];
        drawTree(heap, edges, vertices, svgWidth, svgHeight);
        graphDispatch({type: 'redraw', vertices: vertices, edges: edges});
    }, [heap]);

    const onAdd = (event) => {
        event.preventDefault();
        if (addval === '') return;
        if (heap == null) {
            const createHeapFromJSON = (response) => {
                setHeap(response);
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
                setHeap(response);
            }

            sendRequest({
                url: configData.BACKEND_URL + type + '/insert/' + addval,
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: heap
            }, createHeapFromJSON);
        }
    }

    const onRemove = (event) => {
        event.preventDefault();

        const createHeapFromJSON = (response) => {
            if (heap != null && JSON.stringify(heap) !== JSON.stringify(response.root)) {
                setUndoStack((old) => [...old, heap]);
                setRedoStack([]);
                setHeap(response);
            }
        }
        sendRequest({
            url: configData.BACKEND_URL + type + '/remove',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: heap
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
