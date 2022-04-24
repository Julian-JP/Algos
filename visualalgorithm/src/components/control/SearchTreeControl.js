import React, {useState} from 'react';
import classes from "./SearchTreeControl.module.css"
import useFetch from "../../hooks/useFetch";

const SearchTreeControl = ({canvas, type}) => {

    const [tree, setTree] = useState(null);
    const [addval, setAddval] = useState('');
    const [removeval, setRemoveval] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    const nodecolor = 'blue';
    const linecolor = 'black';

    const onUndo = () => {
        if (undoStack.length > 0) {
            const undo = undoStack.pop();
            setUndoStack(undoStack.splice(0, undoStack.length));
            setRedoStack((old) => [...old, tree]);
            if (undo != null) {
                printTree(undo, 5, nodecolor, linecolor);
            } else {
                canvas.clear();
            }
            setTree(undo);
        }
    }

    const onRedo = () => {
        if (redoStack.length > 0) {
            const redo = redoStack.pop();
            setRedoStack(redoStack.splice(0, redoStack.length));
            setUndoStack((old) => [...old, tree]);
            if (redo != null) {
                printTree(redo, 5, nodecolor, linecolor);
            } else {
                canvas.clear();
            }
            setTree(redo)
        }
    }

    const onAdd = (event) => {
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

    const onRemove = (event) => {
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
        <div className={classes.control}>
            <form className={classes.card} onSubmit={onAdd}>
                <input type="number" className={classes.inputBox} onChange={(val) => setAddval(val.target.value)}/>
                <button type="submit" className={classes.buttonAddRemove}>Add</button>
            </form>
            <div className={classes.break}></div>
            <form className={classes.card} onSubmit={onRemove}>
                <input type={"number"} className={classes.inputBox} onChange={(val) => setRemoveval(val.target.value)}/>
                <button type="submit" className={classes.buttonAddRemove}>Remove</button>
            </form>
            <div className={classes.break}></div>
            <div className={classes.undo}>
                <button className={classes.buttonUndoRedo} onClick={onUndo} disabled={undoStack.length === 0}>Undo</button>
            </div>
            <div className={classes.redo}>
                <button className={classes.buttonUndoRedo} onClick={onRedo} disabled={redoStack.length === 0}>Redo</button>
            </div>
        </div>
    );
};

export default SearchTreeControl;
