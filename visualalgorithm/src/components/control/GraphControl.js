import React, {useEffect, useState} from "react";
import classes from "./GraphControl.module.css";
import UndRedoFields from "../UI/UndRedoFields";
import useFetch from "../../hooks/useFetch";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit";

const GraphControl = (props) => {

    const DEFAULT_VERTEX_COLOR = "red";
    const MARKED_VERTEX_COLOR = "blue";

    const [vertices, setVertices] = useState([{value: 1, x: 100, y: 20, color: DEFAULT_VERTEX_COLOR},{value: 2, x: 200, y: 20, color: DEFAULT_VERTEX_COLOR},{value: 3, x: 50, y: 200, color: DEFAULT_VERTEX_COLOR}]);
    const [edges, setEdges] = useState([[null, {color: "black"}, null], [null, null, null], [null, null, null]]);

    const [addNode, setAddNode] = useState('');
    const [removeNode, setRemoveNode] = useState('');
    const [undoStack, setUndoStack] = useState([]);
    const [redoStack, setRedoStack] = useState([]);
    const [markedNodes, setMarkedNode] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        let edgesLst = [];

        for (let i = 0; i < edges.length; i++) {
            for (let j = 0; j < edges[i].length; j++) {
                if (edges[i][j] !== null) {
                    edgesLst.push({
                        type: "line",
                        from: i,
                        to: j,
                        x1: vertices[i].x,
                        x2: vertices[j].x,
                        y1: vertices[i].y,
                        y2: vertices[j].y,
                        stroke: edges[i][j].color
                    });
                }
            }
        }
        props.setEdges(edgesLst);
    }, [edges])

    useEffect(() => {
        let verticesLst = [];
        for (let i = 0; i < vertices.length; i++) {
            verticesLst.push({
                type: "circle",
                x: vertices[i].x,
                y: vertices[i].y,
                fill: vertices[i].color,
                stroke: "black",
                textFill: "black",
                value: vertices[i].value,
                draggable: true,
                onClick: (event) => handleOnClick(i, event)
            });
        }
        props.setVertices(verticesLst)
    }, [vertices]);

    useEffect(() => {
        if (markedNodes.length >= 2) {
            setEdges((oldEdges) => {
                let ret = [];
                for (let i=0; i<oldEdges.length; i++) {
                    ret[i] = [...oldEdges[i]];
                }

                if (ret[markedNodes[0]][markedNodes[1]] !== null || ret[markedNodes[1]][markedNodes[0]] !== null) {
                    ret[markedNodes[0]][markedNodes[1]] = null;
                    ret[markedNodes[1]][markedNodes[0]] = null;
                } else {
                    ret[markedNodes[0]][markedNodes[1]] = {color: "black"};
                }

                return ret;
            });
            setMarkedNode([])
        }
    }, [markedNodes])

    const handleAddNode = (event) => {
        event.preventDefault();
        if (addNode === '') return;

        setUndoStack((old) => [...old, {vertices: vertices, edges: edges}]);
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

    const colorMarkVertex = (nodeIndex, mark) => {
        setVertices((oldVertex) => {
            oldVertex[nodeIndex].color = mark ? MARKED_VERTEX_COLOR : DEFAULT_VERTEX_COLOR;
            return [...oldVertex];
        });
    }

    const markNode = (nodeIndex) => {
        setMarkedNode((old) => {
            if (old.length === 0) {
                colorMarkVertex(nodeIndex, true);
                return [nodeIndex];
            } else if (old[1] === nodeIndex) {
                colorMarkVertex(nodeIndex, false);
                return [];
            } else {
                colorMarkVertex(nodeIndex, false);
                colorMarkVertex(old[0], false);

                return [...old, nodeIndex];
            }
        })
    }

    const handleRemoveNode = (event) => {
        event.preventDefault();

        let index = vertices.findIndex(elem => removeNode === elem.value);
        if (index < 0) return;

        setUndoStack((old) => [...old, {vertices: vertices, edges: edges}]);
        setRedoStack([]);

        setEdges(old => {
            return removeIndex(index, old);
        });
        setVertices(old => {
            return vertices.filter(item => item.value !== removeNode);
        });
    }

    const setGraph = ({edges, vertices}) => {
        setEdges(edges);
        setVertices(vertices);
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
            currentDrawing={{vertices: vertices, edges: edges}}
            undoStackState={[undoStack, setUndoStack]}
            redoStackState={[redoStack, setRedoStack]}
            undoDisable={undoStack.length === 0}
            redoDisable={redoStack.length === 0}
            setCurrent={setGraph}
        />
    </div>)
}

export default GraphControl;