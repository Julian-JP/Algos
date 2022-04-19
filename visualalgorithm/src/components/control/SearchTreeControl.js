import React, { useState } from 'react';
import classes from "./SearchTreeControl.module.css"
import useFetch from "../../hooks/useFetch";

const SearchTreeControl = ({drawCircle, drawLine, clear, canvas, type}) => {

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
            setUndoStack(undoStack.splice(0,undoStack.length));
            setRedoStack((old) => [...old, tree]);
            if (undo != null) {
                printTree(undo, 5, nodecolor, linecolor);
            } else {
                clear();
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
                clear();
            }
            setTree(redo)
        }
    }

    const onAdd = () => {
        if (addval === '' ) return;
        if (tree == null) {
            const createTreeFromJSON = (response) => {
                setTree(response.root);
                setUndoStack((old) => [...old, null]);
                setRedoStack([]);
                printTree(response.root, 5, nodecolor, linecolor);
            }
            sendRequest({
                url: 'http://localhost:8080/algos/'+ type +'/new/' + addval,
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
                url: 'http://localhost:8080/algos/'+ type +'/insert/' + addval,
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: {root: tree}
            }, createTreeFromJSON);
        }
    }

    const onRemove = () => {
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
            url: 'http://localhost:8080/algos/'+ type +'/remove/' + removeval,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {root: tree}
        }, createTreeFromJSON);
    }

    const printTree = (tree, depth, color, lcolor) => {
        clear();
        if (tree != null) {
            printSubTree(tree, 0, 0, canvas.current.width, canvas.current.height, depth, color, lcolor);
        }
    }

    const printSubTree = ({value, left, right}, x, y, width, height, depth, color, lcolor) => {
        if (left) {
            drawLine(x + (width/2), y + (height/8), x + width/4, y + height/4, lcolor);
            printSubTree(left, x+0, y+(height/8), (width/2), height, depth-1, color, lcolor);
        }
        if (right) {
            drawLine(x + (width/2), y + (height/8), x + width/2 + width/4, y + height/4, lcolor);
            printSubTree(right, x+(width/2), y+(height/8), width/2, height, depth-1, color, lcolor);
        }
        drawCircle(x + (width/2), y + (height/8), 20, color, value);
    }

    return (
        <div className={classes.control}>
            <div className={classes.add}>
                <input type="number" className={classes.addBox} onChange={(val) => setAddval(val.target.value)}/>
                <br/>
                <button onClick={onAdd} className={classes.addButton}>Add</button>
            </div>
            <div className={classes.break}></div>
            <div className={classes.remove}>
                <input type={"number"} className={classes.removeBox} onChange={(val) => setRemoveval(val.target.value)}/>
                <br/>
                <button onClick={onRemove} className={classes.removeButton}>Remove</button>
            </div>
            <div className={classes.break}></div>
            <div className={classes.undo}>
                <button className={classes.undobutton} onClick={onUndo}>Undo</button>
            </div>
            <div className={classes.redo}>
                <button className={classes.redobutton} onClick={onRedo}>Redo</button>
            </div>
        </div>
    );
};

export default SearchTreeControl;
