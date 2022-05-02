import React, {useState} from "react";
import InputWithSubmit from "../UI/InputWithSubmit";
import classes from "./GraphControl.module.css";
import UndRedoFields from "../UI/UndRedoFields";
import useFetch from "../../hooks/useFetch";

const GraphControl = ({canvas, type}) => {

    const [{vertices, edges}, setGraph] = useState({
        vertices:
            [{x: 50, y: 50, color: 'blue', value: 10},
                {x: 900, y: 50, color: 'red', value: 6},
                {x: 50, y: 500, color: 'blue', value: 7},],
        edges:
            [[{color: "purple"}, {color: "black"}, {color: "green"}], [null, null, null], [null, null, null]]
    });

    const [addNode, setAddNode] = useState('');
    const [removeNode, setRemoveNode] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    const handleNewPrint = (newGraph) => {
        if (newGraph != null) {
            printGraph(newGraph);
        } else {
            canvas.clear();
        }
        setGraph(newGraph);
    }

    const handleAddNode = (event) => {
        event.preventDefault();
        if (addNode === '') return;

        setGraph(({oldVertices, edges}) => {
            return {[...oldVertices, {x: 100, y:500, color: "red", value: addNode}], null}
        }
        )
    }

    const printGraph = () => {
        canvas.clear();
        for (var i = 0; i < edges.length; i++) {
            for (var j = 0; j < edges[i].length; j++) {
                if (edges[i][j] !== null) {
                    canvas.drawLine(vertices[i].x, vertices[i].y, vertices[j].x, vertices[j].y, edges[i][j].color);
                }
            }
        }


        for (let vertex of vertices) {
            canvas.drawCircle(vertex.x, vertex.y, 20, vertex.color, vertex.value);
        }
    }

    return (
        <React.Fragment>
            <form onClick={printGraph}>
                <InputWithSubmit type="text" onChange={(val) => null} btnLabel="Add"/>
            </form>
            <div className={classes.break}/>
            <form>
                <InputWithSubmit type="text" onChange={(val) => null} btnLabel="Remove"/>
            </form>
            <div className={classes.break}></div>
            <UndRedoFields
                currentDrawing={{vertices, edges}}
                undoStackState={[undoStack, setUndoStack]}
                redoStackState={[redoStack, setRedoStack]}
                undoDisable={undoStack.length === 0}
                redoDisable={redoStack.length === 0}
                handleNewPrint={handleNewPrint}
            />
        </React.Fragment>
    )
}

export default GraphControl;