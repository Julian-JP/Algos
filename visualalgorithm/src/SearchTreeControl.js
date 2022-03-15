import React, { useState } from 'react';
import "./SearchTreeControl.css"

const SearchTreeControl = ({drawCircle, drawLine, clear, canvas}) => {

    const [tree, setTree] = useState(null);
    const [addval, setAddval] = useState('');
    const [removeval, setRemoveval] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const nodecolor = 'blue';
    const linecolor = 'black';

    const onUndo = () => {
        console.log(undoStack.length);
        if (undoStack.length > 0) {
            const undo = undoStack.pop();
            console.log(undo);
            setUndoStack(undoStack.splice(0,undoStack.length));
            setRedoStack((old) => [...old, tree]);
            console.log(undoStack);
            if (undo != null) {
                printTree(undo, 5, nodecolor, linecolor);
            } else {
                console.log("clear");
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
            fetch('http://localhost:8080/algos/bst/new/' + addval, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(res => {return res.json()}).then(data => {
                setTree(data.root);
                setUndoStack((old) => [...old, null]);
                setRedoStack([]);
                printTree(data.root, 5, nodecolor, linecolor);
            });
        } else {
            fetch('http://localhost:8080/algos/bst/insert/' + addval, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({root: tree}),
            }).then(res => {return res.json()}).then(data => {
                if (JSON.stringify(tree) !== JSON.stringify(data.root)) {
                    setUndoStack((old) => [...old, tree]);
                    setRedoStack([]);
                }
                return data.root;
            }).then((data) => {
                setTree(data);
                printTree(data, 5, nodecolor, linecolor);
            });
        }
    }

    const onRemove = () => {
        if (removeval === '' || tree == null) return;

        fetch('http://localhost:8080/algos/bst/remove/' + removeval, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({root: tree}),
        }).then(res => {return res.json()}).then(data => {
            if (tree != null && JSON.stringify(tree) !== JSON.stringify(data.root)) {
                setRedoStack([]);
                setUndoStack((old) => [...old, tree]);
            }
            return data.root;
        }).then(data => {
            console.log(data);
            setTree(data);
            printTree(data, 5, nodecolor, linecolor);
        });
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
        <div className={"control"}>
            <div className={"add"}>
                <input type="number" className={"addBox"} onChange={(val) => setAddval(val.target.value)}/>
                <br/>
                <button onClick={onAdd} className={"addButton"}>Add</button>
            </div>
            <div className={"break"}></div>
            <div className={"remove"}>
                <input type={"number"} className={"removeBox"} onChange={(val) => setRemoveval(val.target.value)}/>
                <br/>
                <button onClick={onRemove} className={"removeButton"}>Remove</button>
            </div>
            <div className={"break"}></div>
            <div className={"undo"}>
                <button className={"undobutton"} onClick={onUndo}>Undo</button>
            </div>
            <div className={"redo"}>
                <button className={"redobutton"} onClick={onRedo}>Redo</button>
            </div>
        </div>
    );
};

export default SearchTreeControl;
