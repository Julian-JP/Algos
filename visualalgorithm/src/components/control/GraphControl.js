import React, {useEffect, useState} from "react";
import classes from "./GraphControl.module.css";
import useFetch from "../../hooks/useFetch";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit";

const GraphControl = (props) => {

    const DEFAULT_VERTEX_COLOR = "white";
    const MARKED_VERTEX_COLOR = "#e0e0e0";

    const [vertices, setVertices] = useState([]);
    const [edges, setEdges] = useState([]);

    const [markedNodes, setMarkedNode] = useState([]);
    const [addVal, setAddVal] = useState(null);
    const [prev, setPrev] = useState([])

    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        let edgesLst = [];

        for (let i = 0; i < edges.length; i++) {
            for (let j = 0; j < edges[i].length; j++) {
                if (edges[i][j] !== null) {
                    edgesLst.push({
                        type: "line",
                        from: vertices[i].value,
                        to: vertices[j].value,
                        x1: vertices[i].x,
                        x2: vertices[j].x,
                        y1: vertices[i].y,
                        y2: vertices[j].y,
                        stroke: edges[i][j].color,
                        id: vertices[j].value + "-" + vertices[i].value
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
                id: vertices[i].value,
                value: vertices[i].value,
                draggable: true,
                onClick: (event) => handleOnClick(i, event),
                onRightClick: handleRemoveNode
            });
        }
        props.setVertices(verticesLst)
    }, [vertices]);

    useEffect(() => {
        if (markedNodes.length >= 2) {
            setEdges((oldEdges) => {
                let ret = [];
                for (let i = 0; i < oldEdges.length; i++) {
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
        if (addVal === '' || vertices.filter(item => (item.value != addVal)).length !== vertices.length) return;

        setEdges(oldEdges => {
            let edgesFromNewNode = [oldEdges.length + 1];
            for (let i = 0; i < oldEdges.length + 1; i++) {
                edgesFromNewNode[i] = [oldEdges.length + 1];
                for (let j = 0; j < oldEdges.length + 1; j++) {
                    if (i < oldEdges.length && j < oldEdges.length) {
                        edgesFromNewNode[i][j] = oldEdges[i][j];
                    } else {
                        edgesFromNewNode[i][j] = null;
                    }
                }
            }
            return edgesFromNewNode;
        })

        setVertices(oldVertices => {
            return [...oldVertices, {
                x: Math.floor(Math.random() * 1000), y: 100, color: DEFAULT_VERTEX_COLOR, value: addVal
            }];
        })
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

        setMarkedNode([]);
        let index = vertices.findIndex(elem => event.id === elem.value);
        if (index < 0) return;

        setEdges((old) => {
            return removeIndex(index, old);
        });

        setVertices(old => {
            return vertices.filter(item => item.value !== event.id);
        })
    }

    const removeIndex = (index, matrix) => {
        let newMatrix = Array(matrix.length - 1);
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

    const next = (steps) => {
        const createGraphFromJSON = (response) => {
            if (JSON.stringify({edges: edges, vertices: vertices}) !== JSON.stringify(response.root)) {
                setPrev((old) => [...old, {edges: edges, vertices: vertices}]);
            }
            setVertices(response.vertices);
            setEdges(response.edges);
        }

        sendRequest({
            url: 'http://localhost:8080/algos/' + props.type + '/step/' + steps,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {edges: edges, vertices: vertices}
        }, createGraphFromJSON);

    }

    return (<div className={classes.container}>
        <MultidataInputWithSubmit
            onSubmit={handleAddNode}
            btnLabel={"Add"}
            data={
                [{
                    type: "text", onChange: (val) => setAddVal(val.target.value), label: "add", noLabel: true
                }]
            }
        />
        <div className={classes.algoNavigationContainer}>
            <button className={classes.prev}>◄</button>
            <button className={classes.next} onClick={() => next(1)}>►</button>
        </div>

    </div>)
}

export default GraphControl;