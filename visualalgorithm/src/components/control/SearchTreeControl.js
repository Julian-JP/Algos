import React, {useState} from 'react';
import classes from "./SearchTreeControl.module.css";
import useFetch from "../../hooks/useFetch";
import InputWithSubmit from "../UI/Input/InputWithSubmit";
import UndRedoFields from "../UI/UndRedoFields";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit";

const SearchTreeControl = ({canvas, type}) => {

    const [tree, setTree] = useState(null);
    const [addval, setAddval] = useState('');
    const [removeval, setRemoveval] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    const nodecolor = 'blue';
    const linecolor = 'black';

    const handleNewPrint = (newTree) => {
        if (newTree != null) {
            printTree(newTree, 5, nodecolor, linecolor);
        } else {
            canvas.clear();
        }
        setTree(newTree);
    }

    const handleAdd = (event) => {
        event.preventDefault();
        if (addval === '') return;
        if (tree == null) {
            const createTreeFromJSON = (response) => {
                setTree(response.root);
                setUndoStack((old) => [...old, null]);
                setRedoStack([]);
                printTree(response.root, 5, nodecolor, linecolor);
            }
            sendRequest({
                url: 'http://localhost:8080/algos/' + type + '/new/' + addval,
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
                printTree(response.root, 5, nodecolor, linecolor);
            }

            sendRequest({
                url: 'http://localhost:8080/algos/' + type + '/insert/' + addval,
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
                printTree(response.root, 5, nodecolor, linecolor);
            }
        }
        sendRequest({
            url: 'http://localhost:8080/algos/' + type + '/remove/' + removeval,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {root: tree}
        }, createTreeFromJSON);
    }

    const printTree = (tree, depth, color, lcolor) => {
        canvas.clear();
        if (tree != null) {
            printSubTree(tree, 0, 0, canvas.width, canvas.height, depth, color, lcolor);
        }
    }

    const printSubTree = ({value, left, right}, x, y, width, height, depth, color, lcolor) => {
        if (left) {
            canvas.drawLine(x + (width / 2), y + (height / 8), x + width / 4, y + height / 4, lcolor);
            printSubTree(left, x + 0, y + (height / 8), (width / 2), height, depth - 1, color, lcolor);
        }
        if (right) {
            canvas.drawLine(x + (width / 2), y + (height / 8), x + width / 2 + width / 4, y + height / 4, lcolor);
            printSubTree(right, x + (width / 2), y + (height / 8), width / 2, height, depth - 1, color, lcolor);
        }
        canvas.drawCircle(x + (width / 2), y + (height / 8), 20, color, value);
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
                        type: "number", onChange: (val) => setRemoveval(val.target.value), label: "remove", noLabel: true
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
                handleNewPrint={handleNewPrint}
            />
        </div>
    );
};

export default SearchTreeControl;
