import React, {useState} from "react";
import classes from "./GraphControl.module.css";
import UndRedoFields from "../UI/UndRedoFields";
import useFetch from "../../hooks/useFetch";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit";

const GraphControl = ({canvas, type}) => {

    const [{vertices, edges}, setGraph] = useState({
        vertices: [], edges: []
    });

    const [addNode, setAddNode] = useState('');
    const [removeNode, setRemoveNode] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    const handleNewPrint = (newGraph) => {
        if (newGraph != null) {
            printGraph(newGraph.vertices, newGraph.edges);
        } else {
            canvas.clear();
        }
        setGraph(newGraph);
    }

    const handleAddNode = (event) => {
        event.preventDefault();
        if (addNode === '') return;

        setUndoStack((old) => [...old, {vertices, edges}]);
        setRedoStack([]);

        setGraph((graphOld) => {
            let oldVertices = graphOld.vertices;
            let oldEdges = graphOld.edges;

            let newVertices = [...oldVertices, {
                x: Math.floor(Math.random() * 1000), y: 100, color: "red", value: addNode
            }];
            let edgesFromNewNode = [null, null];
            for (let i = 0; i < oldEdges.length; i++) {
                oldEdges[i].push(null);
                edgesFromNewNode.push(null);
            }

            edgesFromNewNode = [...oldEdges, edgesFromNewNode];
            printGraph(newVertices, edgesFromNewNode);
            return {vertices: newVertices, edges: edgesFromNewNode}
        });
    }

    const handleRemoveNode = (event) => {
        //TODO
        event.preventDefault();
        setUndoStack((old) => [...old, {vertices, edges}]);
        setRedoStack([]);
        setGraph(({vertices, edges}) => {
            vertices = vertices.filter(item => item.value != removeNode);
            let edgesNew = new Array(edges.length -1);
            for (let i=0; i<edges.length; i++) {
                if (i != vertices.findIndex(vertex => vertex.value == removeNode)) {
                    for (let j = 0; j < edges.length; j++) {

                    }
                }
            }
            printGraph(vertices, edges);
            return {vertices, edges}
        })
    }

    const printGraph = (vertices, edges) => {
        canvas.clear();
        for (let i = 0; i < edges.length; i++) {
            for (let j = 0; j < edges[i].length; j++) {
                if (edges[i][j] !== null) {
                    canvas.drawLine(vertices[i].x, vertices[i].y, vertices[j].x, vertices[j].y, edges[i][j].color);
                }
            }
        }

        for (let vertex of vertices) {
            canvas.drawCircle(vertex.x, vertex.y, 20, vertex.color, vertex.value);
        }
    }

    return (<div className={classes.container}>
        <MultidataInputWithSubmit
            btnLabel={"Add"}
            data={[{
                type: "number", onChange: (event) => setAddNode(event.target.value), label: "Add", noLabel: true
            }]}
            onSubmit={handleAddNode}
        />
        <MultidataInputWithSubmit
            btnLabel={"Remove"}
            data={[{
                type: "number", label: "Remove", onChange: (event) => setRemoveNode(event.target.value), noLabel: true
            }]}
            onSubmit={handleRemoveNode}
        />
        <MultidataInputWithSubmit
            className={classes.change}
            btnLabel={"Change"}
            data={[{
                type: "text", onChange: () => {
                }, label: "Node"
            }, {
                type: "number", onChange: () => {
                }, label: "X"
            }, {
                type: "number", onChange: () => {
                }, label: "Y"
            }, {
                type: "number", onChange: () => {
                }, label: "Color"
            }]}/>
        <UndRedoFields
            className={classes.undoRedo}
            currentDrawing={{vertices, edges}}
            undoStackState={[undoStack, setUndoStack]}
            redoStackState={[redoStack, setRedoStack]}
            undoDisable={undoStack.length === 0}
            redoDisable={redoStack.length === 0}
            handleNewPrint={handleNewPrint}
        />
    </div>)
}

export default GraphControl;