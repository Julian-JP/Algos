import React, {useState} from 'react';
import classes from "./HeapControl.module.css";
import useFetch from "../../hooks/useFetch";
import InputWithSubmit from "../UI/Input/InputWithSubmit";
import UndRedoFields from "../UI/UndRedoFields";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit";

const HeapControl = ({canvas, type}) => {

    const [heap, setHeap] = useState(null);
    const [addval, setAddval] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    const nodecolor = 'blue';
    const linecolor = 'black';

    const handleNewPrint = (heap) => {
        if (heap != null) {
            printHeap(heap, 5, nodecolor, linecolor);
        } else {
            canvas.clear();
        }
        setHeap(heap);
    }

    const onAdd = (event) => {
        event.preventDefault();
        if (addval === '') return;
        if (heap == null) {
            const createHeapFromJSON = (response) => {
                setHeap(response.root);
                setUndoStack((old) => [...old, null]);
                setRedoStack([]);
                printHeap(response.root, 5, nodecolor, linecolor);
            }
            sendRequest({
                url: 'http://localhost:8080/algos/' + type + '/new/' + addval,
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
                printHeap(response.root, 5, nodecolor, linecolor);
            }

            sendRequest({
                url: 'http://localhost:8080/algos/' + type + '/insert/' + addval,
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
                printHeap(response.root, 5, nodecolor, linecolor);
            }
        }
        sendRequest({
            url: 'http://localhost:8080/algos/' + type + '/remove',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {root: heap}
        }, createHeapFromJSON);
    }

    const printHeap = (tree, depth, color, lcolor) => {
        canvas.clear();
        if (tree != null) {
            printSubHeap(tree, 0, 0, canvas.width, canvas.height, depth, color, lcolor);
        }
    }

    const printSubHeap = ({value, left, right}, x, y, width, height, depth, color, lcolor) => {
        if (left) {
            canvas.drawLine(x + (width / 2), y + (height / 8), x + width / 4, y + height / 4, lcolor);
            printSubHeap(left, x + 0, y + (height / 8), (width / 2), height, depth - 1, color, lcolor);
        }
        if (right) {
            canvas.drawLine(x + (width / 2), y + (height / 8), x + width / 2 + width / 4, y + height / 4, lcolor);
            printSubHeap(right, x + (width / 2), y + (height / 8), width / 2, height, depth - 1, color, lcolor);
        }
        canvas.drawCircle(x + (width / 2), y + (height / 8), 20, color, value);
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
            <form className={classes.undoRedo} onSubmit={onRemove}>
                <button type="submit" className={classes.remove}>Remove</button>
            </form>
            <UndRedoFields
                className={classes.undoRedo}
                currentDrawing={heap}
                undoStackState={[undoStack, setUndoStack]}
                redoStackState={[redoStack, setRedoStack]}
                undoDisable={undoStack.length === 0}
                redoDisable={redoStack.length === 0}
                handleNewPrint={handleNewPrint}
            />
        </div>
    );
};

export default HeapControl;
