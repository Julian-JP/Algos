import React, {useEffect, useState} from "react";
import classes from "./GraphControl.module.css";
import UndRedoFields from "../UI/UndRedoFields";
import useFetch from "../../hooks/useFetch";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit";

const GraphControl = (props) => {

    const [graph, setGraph] = useState({
        vertices: [{value: 1, x: 100, y: 20}, {value: 2, x: 200, y: 20}, {value: 3, x: 50, y: 200}],
        edges: [[null, {color: "black"}, null], [null, null, null], [null, null, null]]
    });

    const [addNode, setAddNode] = useState('');
    const [removeNode, setRemoveNode] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);
    const [markedNodes, setMarkedNode] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        let edgesLst = [];
        let verticesLst = [];
        let edgeCount = 0;

        for (let i = 0; i < graph.edges.length; i++) {
            for (let j = 0; j < graph.edges[i].length; j++) {
                if (graph.edges[i][j] !== null) {
                    edgesLst.push({
                        type: "line",
                        from: graph.vertices[i].value,
                        to: graph.vertices[j].value,
                        x1: graph.vertices[i].x,
                        x2: graph.vertices[j].x,
                        y1: graph.vertices[i].y,
                        y2: graph.vertices[j].y,
                        stroke: graph.edges[i][j].color
                    });
                    edgeCount++;
                }
            }
        }

        for (let i = 0; i < graph.vertices.length; i++) {
            verticesLst.push({
                type: "circle",
                x: graph.vertices[i].x,
                y: graph.vertices[i].y,
                fill: "red",
                stroke: "black",
                textFill: "black",
                value: graph.vertices[i].value,
                draggable: true,
                onClick: (event) => handleOnClick(i, event)
            });
        }

        props.vertex.setVertices(verticesLst);
        props.edge.setEdges(edgesLst);
    }, [graph])

    const handleAddNode = (event) => {
        event.preventDefault();
        if (addNode === '') return;

        setUndoStack((old) => [...old, {vertices: graph.vertices, edges: graph.edges}]);
        setRedoStack([]);

        setGraph((graphOld) => {
            let oldVertices = graphOld.vertices;
            let oldEdges = graphOld.edges;

            let newVertices = [...oldVertices, {
                x: Math.floor(Math.random() * 1000), y: 100, color: "red", value: addNode
            }];
            let edgesFromNewNode = [newVertices.length];

            for (let i = 0; i < newVertices.length; i++) {
                edgesFromNewNode[i] = [newVertices];
                for (let j = 0; j < newVertices.length; j++) {
                    if (i < newVertices.length - 1 && j < newVertices.length - 1) {
                        edgesFromNewNode[i][j] = oldEdges[i][j];
                    } else {
                        edgesFromNewNode[i][j] = null;
                    }
                }
            }
            return {vertices: newVertices, edges: edgesFromNewNode}
        });
    }

    const handleOnClick = (id, event) => {
        markNode(id);
    }

    const markNode = (nodeIndex) => {
        setMarkedNode((old) => {
            if (old.length === 0) {
                return [nodeIndex];
            } else if (old[1] === nodeIndex) {
                return [];
            } else {
                setGraph(({vertices: oldVertices, edges: oldEdges}) => {
                    let ret = [];
                    for (let i=0; i<oldEdges.length; i++) {
                        ret[i] = [...oldEdges[i]];
                    }

                    if (ret[old[0]][nodeIndex] !== null) {
                        ret[old[0]][nodeIndex] = null;
                    } else {
                        ret[old[0]][nodeIndex] = {color: "black"};
                    }

                    console.log(ret)

                    return {vertices: oldVertices, edges: ret};
                });
                return [];
            }
        })
    }

    const handleRemoveNode = (event) => {
        event.preventDefault();

        let index = graph.vertices.findIndex(elem => removeNode === elem.value);
        if (index < 0) return;

        setUndoStack((old) => [...old, {vertices: graph.vertices, edges: graph.edges}]);
        setRedoStack([]);

        setGraph(({vertices, edges}) => {
            let newVertices = vertices.filter(item => item.value !== removeNode);
            let newEdges = removeIndex(index, edges);
            let newGraph = {vertices: newVertices, edges: newEdges}
            return newGraph;
        })
    }

    const removeIndex = (index, matrix) => {
        let newMatrix = [matrix.length - 1];
        let oldIndex = 0;

        for (let i = 0; i < matrix.length - 1; i++) {
            if (i === index) {
                oldIndex++;
            }
            newMatrix[i] = [matrix.length - 1];
            for (let j = 0; j < matrix.length - 1; j++) {
                newMatrix[i][j] = matrix[oldIndex][j >= index ? j + 1 : j];
            }
            oldIndex++;
        }
        return newMatrix;
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
            currentDrawing={{vertices: graph.vertices, edges: graph.edges}}
            undoStackState={[undoStack, setUndoStack]}
            redoStackState={[redoStack, setRedoStack]}
            undoDisable={undoStack.length === 0}
            redoDisable={redoStack.length === 0}
            setCurrent={setGraph}
        />
    </div>)
}

export default GraphControl;