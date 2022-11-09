import React, {useEffect, useState} from "react";
import classes from "./GraphControl.module.css";
import useFetch from "../../hooks/useFetch";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit";
import Modal from "../UI/Modal";

const GraphControl = (props) => {

    const DEFAULT_VERTEX_COLOR = "white";
    const MARKED_VERTEX_COLOR = "#e0e0e0";
    const START_END_COLOR = "blue";
    const DEFAULT_LINE_COLOR = "black"

    const [vertices, setVertices] = useState([]);
    const [edges, setEdges] = useState([]);

    const [markedNodes, setMarkedNode] = useState([]);
    const [addVal, setAddVal] = useState(null);
    const [prev, setPrev] = useState([]);

    const [start, setStart] = useState(undefined);
    const [end, setEnd] = useState(undefined);

    const [weight, setWeight] = useState(0);

    const [modalIsOpen, setModalIsOpen] = useState(true);

    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        let edgesLst = [];

        for (let i = 0; i < edges.length; i++) {
            for (let j = 0; j < edges[i].length; j++) {
                if (edges[i][j] !== null) {
                    edgesLst.push({
                        type: "line",
                        from: vertices[i].id,
                        to: vertices[j].id,
                        x1: vertices[i].x,
                        x2: vertices[j].x,
                        y1: vertices[i].y,
                        y2: vertices[j].y,
                        stroke: edges[i][j].color,
                        id: vertices[j].id + "-" + vertices[i].id,
                        directed: props.directed,
                        weight: edges[i][j].weight
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
                id: vertices[i].id,
                value: vertices[i].value,
                opacity: vertices[i].opacity,
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
                if (props.directed) {
                    if (ret[markedNodes[0]][markedNodes[1]] !== null) {
                        ret[markedNodes[0]][markedNodes[1]] = null;
                    } else {
                        ret[markedNodes[0]][markedNodes[1]] = {
                            color: DEFAULT_LINE_COLOR,
                            weight: props.weightedEdges ? weight : null
                        };
                    }
                } else {
                    if (ret[markedNodes[0]][markedNodes[1]] != null || ret[markedNodes[1]][markedNodes[0]] != null) {
                        ret[markedNodes[0]][markedNodes[1]] = null;
                        ret[markedNodes[1]][markedNodes[0]] = null;
                    } else {
                        ret[markedNodes[0]][markedNodes[1]] = {
                            color: DEFAULT_LINE_COLOR,
                            weight: props.weightedEdges ? weight : null
                        };
                        ret[markedNodes[1]][markedNodes[0]] = {
                            color: DEFAULT_LINE_COLOR,
                            weight: props.weightedEdges ? weight : null
                        };
                    }
                }

                return ret;
            });
            setMarkedNode([]);
        }
    }, [markedNodes]);

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
                x: Math.floor(Math.random() * 1000), y: 100, color: DEFAULT_VERTEX_COLOR, value: addVal, id: addVal
            }];
        })
    }

    const handleOnClick = (id) => {
        markNode(id);
    }

    const markVertex = (nodeIndex, marked) => {
        setVertices((oldVertex) => {
            oldVertex[nodeIndex].opacity = marked ? 0.5 : 1;
            return [...oldVertex];
        });
    }

    const markNode = (nodeIndex) => {
        setMarkedNode((old) => {
            if (old.length === 0) {
                markVertex(nodeIndex, true);
                return [nodeIndex];
            } else if (old[1] === nodeIndex) {
                markVertex(nodeIndex, false);
                return [];
            } else {
                markVertex(nodeIndex, false);
                markVertex(old[0], false);

                return [...old, nodeIndex];
            }
        })
    }

    const handleRemoveNode = (event) => {

        setMarkedNode([]);
        let index = vertices.findIndex(elem => event.id === elem.id);
        if (index < 0) return;

        setEdges((old) => {
            return removeIndex(index, old);
        });

        setVertices(old => {
            let temp = [...old];
            return temp.filter(item => item.id !== event.id);
        })

        updateStartEndAfterRemovingIndex(index);
    }

    const removeIndex = (index, matrix) => {
        let newMatrix = Array(matrix.length - 1);
        let oldIndex = 0;

        for (let i = 0; i < matrix.length - 1; i++) {
            if (i === index) {
                oldIndex++;
            }
            newMatrix[i] = [matrix.length - 1];
            for (let j = 0; j < newMatrix.length; j++) {
                newMatrix[i][j] = matrix[oldIndex][j >= index ? j + 1 : j];
            }
            oldIndex++;
        }

        return newMatrix;
    }

    const updateStartEndAfterRemovingIndex = (index) => {
        if (start > index) {
            setStart(old => old - 1);
        } else if (start === index) {
            setStart(null);
        }

        if (end > index) {
            setEnd(old => old - 1);
        } else if (end === index) {
            setEnd(null);
        }
    }

    async function updatePreviousStack() {
        setPrev(old => {
            let temp = [...old];
            let newPrevObj = {
                vertices: JSON.parse(JSON.stringify(vertices)),
                edges: JSON.parse(JSON.stringify(edges)),
                start: start,
                end: end
            }
            temp.push(newPrevObj);
            return temp;
        });
    }

    const next = () => {

        function createGraphFromJSON(response) {
            setEdges(old => {
                return response.edges
            });
            if (props.verteciesRenaming === true) {
                setVertices(old => {
                    let temp = [...old]
                    for (let i = 0; i < temp.length; i++) {
                        temp[i].value = response.vertices[i].value
                    }
                    return temp;
                })
            }
        }

        sendRequest({
            url: 'https://julian-laux.de:8080/algos/' + props.type + '/step',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {edges: edges, start: start, vertices: vertices, end: end}
        }, (response => {
            updatePreviousStack().then(() => createGraphFromJSON(response));
        }));
    }

    const previous = () => {
        if (prev.length < 1) return;

        let previousState = prev[prev.length - 1];
        setEdges(previousState.edges);
        setVertices(previousState.vertices);
        setStart(previousState.start);
        setEnd(previousState.end);

        setPrev(old => {
            let temp = [...old];
            temp.pop();
            return temp;
        })
    }

    const updateStartEndColor = (newEnd, newStart) => {
        setVertices((oldVertex) => {
            if (end != undefined) oldVertex[end].color = DEFAULT_VERTEX_COLOR;
            if (start != undefined) oldVertex[start].color = DEFAULT_VERTEX_COLOR;
            if (newEnd != undefined) oldVertex[newEnd].color = START_END_COLOR;
            if (newStart != undefined) oldVertex[newStart].color = START_END_COLOR;
            return [...oldVertex];
        });
    }

    const handleStartEnd = (isStart) => {
        if (markedNodes.length === 0) {
            return;
        } else if (start === markedNodes[0] && isStart) {
            updateStartEndColor(end, undefined);
            markNode(markedNodes[0], false);
            setMarkedNode([]);
            setStart(undefined);
        } else if (end === markedNodes[0] && !isStart) {
            updateStartEndColor(undefined, start);
            markNode(markedNodes[0], false);
            setMarkedNode([]);
            setEnd(undefined);
        } else if (isStart === true) {
            updateStartEndColor(end, markedNodes[0]);
            markNode(markedNodes[0], false);
            setStart(markedNodes[0]);
            setMarkedNode([]);
        } else if (!isStart) {
            updateStartEndColor(markedNodes[0], start);
            markNode(markedNodes[0], false);
            setEnd(markedNodes[0]);
            setMarkedNode([]);
        }
    }

    const clearPreviousStack = () => {
        setPrev([{edges: edges, vertices: vertices, start: start, end: end}]);
        setEdges(old => {
            let temp = [...old];
            for (let i = 0; i < temp.length; i++) {
                for (let j = 0; j < temp.length; j++) {
                    if (temp[i][j] !== null) temp[i][j].color = DEFAULT_LINE_COLOR;
                }
            }
            return temp;
        });
        setVertices(old => {
            for (let i = 0; i < old.length; i++) {
                old[i].value = old[i].id;
            }
            return [...old];
        })
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
            {props.weightedEdges ? <MultidataInputWithSubmit
                btnLabel={"Edge Weight"}
                data={
                    [{
                        type: "number",
                        min: props.minWeight,
                        onChange: (val) => setWeight(val.target.value),
                        label: "Edge Weight",
                        noLabel: true,
                        defaultValue: 0
                    }]
                }
            /> : null}
            {props.startButton && props.endButton &&
                <div className={classes.algoNavigationContainer}>
                    <button className={classes.leftButton} onClick={() => handleStartEnd(true)}>Start</button>
                    <button className={classes.rightButton} onClick={() => handleStartEnd(false)}>End</button>
                </div>
            }
            {props.startButton && !props.endButton &&
                <div className={classes.algoNavigationContainer}>
                    <button className={classes.fullSizeButton} onClick={() => handleStartEnd(true)}>Start</button>
                </div>
            }
            <div className={classes.algoNavigationContainer}>
                <button className={classes.leftButton} onClick={() => previous(1)}>◄</button>
                <button className={classes.rightButton} onClick={() => next(1)}>►</button>
            </div>

            <Modal open={modalIsOpen} onClose={() => setModalIsOpen(false)}>
                <ul>
                    <li>{"Nodes can be moved via drag and drop"} </li>
                    <li>{"Adding edge from node A to B by first clicking on Node A than clicking on Node B remove the edge the same way"}</li>
                    <li>{"Select the Starting- (or Ending-) node of the algorithm by clicking on the Node and then hit the button Start/End"}</li>
                    <li>{"Start with the first step of the algorithm by pressing the ► button"}</li>
                    <li>{"You can go one step back with the ◄ button"}</li>
                </ul>
            </Modal>
        </div>
    )
}

export default GraphControl;