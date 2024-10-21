import React from "react";
import classes from "./UndoRedoFields.module.css";

const UndoRedoFields = props => {

    const [undoStack, setUndoStack] = props.undoStackState;
    const [redoStack, setRedoStack] = props.redoStackState;
    const currentDrawing = props.currentDrawing;

    const handleUndo = () => {
        if (undoStack.length > 0) {
            const undo = undoStack.pop();
            setUndoStack(undoStack.splice(0, undoStack.length));
            setRedoStack((old) => [...old, currentDrawing]);
            props.setCurrent(undo);
        }
    }

    const handleRedo = () => {
        if (redoStack.length > 0) {
            const redo = redoStack.pop();
            setRedoStack(redoStack.splice(0, redoStack.length));
            setUndoStack((old) => [...old, currentDrawing]);
            props.setCurrent(redo);
        }
    }

    return <div className={props.className}>
        <div className={classes.undo}>
            <button className={classes.buttonUndoRedo} onClick={handleUndo} disabled={props.undoDisable}>Undo</button>
        </div>
        <div className={classes.redo}>
            <button className={classes.buttonUndoRedo} onClick={handleRedo} disabled={props.redoDisable}>Redo</button>
        </div>
    </div>
}

export default UndoRedoFields;