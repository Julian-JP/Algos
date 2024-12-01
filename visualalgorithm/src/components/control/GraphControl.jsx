import React, {useEffect, useRef, useState} from "react";
import classes from "./GraphControl.module.css";
import useFetch from "../../hooks/useFetch";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit.jsx";
import Modal from "../UI/Modal.jsx";
import {node} from "globals";

const GraphControl = (props) => {
    const SERVER_URL = "http://localhost:8080/algos/"

    const DEFAULT_VERTEX_COLOR = "white";
    const START_COLOR = "Aqua";
    const END_COLOR = "Chartreuse";

    const MARKED_EDGE_LINE_COLOR = "blue";
    const FINAL_EDGE_LINE_COLOR = "red";
    const PROCESSED_EDGE_LINE_COLOR = "gray";

    const DEFAULT_LINE_COLOR = "white"
    const DEFAULT_TEXT_COLOR = "black";

    const START_END_TEXT_COLOR = "black";

    const markedNodes = useRef([]);
    const addVal = useRef(null);
    const prev = useRef([]);

    const start = useRef(undefined);
    const end = useRef(undefined);

    const weight = useRef(0);

    const [modalIsOpen, setModalIsOpen] = useState(true);

    const {isLoading, error, sendRequest} = useFetch();

    const handleAddNode = (event) => {
        resetEdgeColor();
        resetVertexWeight();
        event.preventDefault();
        if (addVal.current === '' || props.graph.vertices.filter(item => (item.value !== addVal.current)).length !== props.graph.vertices.length) {
            return;
        }
        let newVertex = {
            type: "circle",
            x: Math.floor(Math.random() * (props.svgWidth - 50)) + 25,
            y: Math.floor(Math.random() * (props.svgHeight - 50)) + 25,
            fill: DEFAULT_VERTEX_COLOR,
            value: addVal.current,
            id: addVal.current,
            marked: false,
            stroke: "black",
            textFill: "black",
            opacity: 1,
            draggable: true,
            onClick: (event) => handleOnClick(event.id),
            onRightClick: handleRemoveNode
        };
        props.graphDispatch({type: 'addVertex', vertex: newVertex});
    }

    const handleOnClick = (id) => {
        markNode(id);
    }

    const markVertex = (nodeIndex, marked) => {
        props.graphDispatch({
            type: 'changeVertexProperties',
            updateVertices: vertices => {
                return vertices.map(v => {
                    if (v.id === nodeIndex) v.opacity = marked ? 0.5 : 1
                });
            }
        });
    }

    const markNode = (nodeIndex) => {
        if (markedNodes.current.length === 0) {
            markVertex(nodeIndex, true);
            markedNodes.current = [nodeIndex];
        } else if (markedNodes.current[0] === nodeIndex) {
            addRemoveEdge(markedNodes.current[0], nodeIndex)
            markVertex(nodeIndex, false);
            markedNodes.current = [];
        } else {
            addRemoveEdge(markedNodes.current[0], nodeIndex)
            markVertex(nodeIndex, false);
            markVertex(markedNodes.current[0], false);
            markedNodes.current = [];
        }
    }

    const addRemoveEdge = (from, to) => {
        resetEdgeColor();
        resetVertexWeight();
        let edge = props.graph.edges.find((val) => val.from === from && val.to === to);
        if (edge === undefined) {
            props.graphDispatch({
                type: 'addEdge',
                edge: {
                    color: DEFAULT_TEXT_COLOR,
                    weight: props.weightedEdges ? weight.current : null,
                    from: from,
                    to: to,
                    stroke: DEFAULT_LINE_COLOR,
                    id: from + "-" + to,
                    directed: true
                }
            })
        } else {
            props.graphDispatch({
                type: 'removeEdge',
                from: from,
                to: to
            })
        }
    }

    const handleRemoveNode = (event) => {
        markedNodes.current = [];

        props.graphDispatch({
            type: 'removeVertex',
            vertexId: event.id
        })

        updateStartEndAfterRemovingIndex(event.id);
    }

    const updateStartEndAfterRemovingIndex = (index) => {
        if (start.current > index) {
            start.current -= 1;
        } else if (start.current === index) {
            start.current = null;
        }

        if (end.current > index) {
            end.current -= 1;
        } else if (end.current === index) {
            end.current = null;
        }
    }

    async function updatePreviousStack() {
        let newPrevObj = {
            vertices: props.graph.vertices,
            edges: props.graph.edges,
            start: start,
            end: end
        }
        console.log(newPrevObj)
        prev.current.push(newPrevObj);
        console.log(prev.current)
    }

    const getEdgeStateDependingOnColor = (edge) => {
        switch (edge.stroke) {
            case DEFAULT_LINE_COLOR: return 0;
            case MARKED_EDGE_LINE_COLOR: return 1;
            case FINAL_EDGE_LINE_COLOR: return 2;
            case PROCESSED_EDGE_LINE_COLOR: return 3;
        }
    }

    const getAdjacencyMatrix = () => {
        let n = props.graph.vertices.length;
        let adj = Array.from({ length: n }, () => Array(n).fill(null));
        props.graph.edges.forEach(edge => {
            const fromIndex = props.graph.vertices.findIndex(v => v.id === edge.from)
            const toIndex = props.graph.vertices.findIndex(v => v.id === edge.to)
            adj[fromIndex][toIndex] = {
                marking: getEdgeStateDependingOnColor(edge),
                weight: edge.weight
            };
        });
        return adj;
    }

    const getVertexList = () => {
        return props.graph.vertices.map(v => {
            return {
                value: v.value,
                weight: v.weight === "∞" ? "Infinity" : (v.weight === "-∞" ? "-Infinity" : v.weight)
            }
        })
    }

    const updateEdge = (from, to, updatedEdge) => {
        let newEdgeColor = DEFAULT_LINE_COLOR;

        switch(updatedEdge.marking) {
            case 0: newEdgeColor = DEFAULT_LINE_COLOR;
            break;
            case 1: newEdgeColor = MARKED_EDGE_LINE_COLOR;
            break;
            case 2: newEdgeColor = FINAL_EDGE_LINE_COLOR;
            break;
            case 3: newEdgeColor = PROCESSED_EDGE_LINE_COLOR;

        }

        const edgeId = props.graph.edges.find(e => e.from === from && e.to === to).id;

        props.graphDispatch({
            type: 'changeEdgeProperties',
            updateEdge: (e => {
                if (e.id === edgeId) e.stroke = newEdgeColor;
                return e;
            })
        });
    }

    const updateVertices = (updatedVertices) => {
        props.graphDispatch({
            type: 'changeVertexProperties',
            updateVertices: (vertices => {
                for (let vertexId = 0; vertexId < updatedVertices.length; ++vertexId) {
                    if (updatedVertices[vertexId].value !== vertices[vertexId].value) {
                        vertices[vertexId].value = updatedVertices[vertexId].value;
                    }
                    if (updatedVertices[vertexId].weight !== vertices[vertexId].weight) {
                        if (updatedVertices[vertexId].weight === "Infinity") {
                            vertices[vertexId].weight = "∞";
                        } else if (updatedVertices[vertexId].weight === "-Infinity") {
                            vertices[vertexId].weight = "-∞";
                        } else {
                            vertices[vertexId].weight = updatedVertices[vertexId].weight;
                        }
                    }
                }
                return vertices;
            })
        });
    }

    const resetEdgeColor = () => {
        prev.current = [];
        props.graphDispatch({
            type: 'changeEdgeProperties',
            updateEdge: (e => {
                e.stroke = DEFAULT_LINE_COLOR;
                return e;
            })
        })
    }

    const resetVertexWeight = () => {
        props.graphDispatch({
            type: 'changeVertexProperties',
            updateVertices: (vertices => {
                for (let vertexId = 0; vertexId < vertices.length; ++vertexId) {
                    vertices[vertexId].weight = undefined;
                }
                return vertices;
            })
        })
    }

    const next = () => {

        const adjMatrix = getAdjacencyMatrix();
        const vertexList = getVertexList();

        function createGraphFromJSON(response) {
            if (response.edges != null) {
                for (let from = 0; from < response.edges.length; from++) {
                    for (let to = 0; to < response.edges.length; to++) {
                        if (response.edges[from][to] !== adjMatrix[from][to]) {
                            updateEdge(vertexList[from].value, vertexList[to].value, response.edges[from][to])
                        }
                    }
                }
            }
            if (response.vertices != null) {
                updateVertices(response.vertices)
            }
        }

        sendRequest({
            url: SERVER_URL + props.type + '/step',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {
                edges: adjMatrix,
                start: props.graph.vertices.findIndex(v => v.value === start.current),
                vertices: vertexList,
                end: props.graph.vertices.findIndex(v => v.value === end.current)
            }
        }, (response => {
            updatePreviousStack().then(() => createGraphFromJSON(response));
        }));
    }

    const previous = () => {
        if (prev.current.length < 1) return;

        let previousState = prev.current[prev.current.length - 1];
        console.log(prev)
        console.log(prev.current.length - 1)
        console.log(previousState)
        props.graphDispatch({
            type: 'redraw',
            vertices: previousState.vertices,
            edges: previousState.edges
        })
        console.log(props.graph)
        start.current = previousState.start;
        end.current = previousState.end;

        prev.current.pop();
    }


    const updateVertexFill = (vertexId, fillColor, textColor) => {
        props.graphDispatch({
            type: 'changeVertexProperties',
            updateVertices: vertices =>  {
                for (let i = 0; i < vertices.length; i++) {
                    if (vertices[i].id === vertexId) {
                        vertices[i].fill = fillColor;
                        vertices[i].opacity = 1;
                        vertices[i].textFill = textColor;
                    }
                }
                console.log(vertices)
                return vertices;
            }
        })
    }

    const updateStart = (newStart) => {
        if (start.current !== undefined && end.current !== start.current) {
            updateVertexFill(start.current, DEFAULT_VERTEX_COLOR, DEFAULT_TEXT_COLOR);
        }
        if (newStart !== undefined) {
            updateVertexFill(newStart, START_COLOR, START_END_TEXT_COLOR);
        }
        start.current = newStart;
    }

    const updateEnd = (newEnd) => {
        if (end.current !== undefined && end.current !== start.current) {
            updateVertexFill(end.current, DEFAULT_VERTEX_COLOR, DEFAULT_TEXT_COLOR);
        }
        if (newEnd !== undefined) {
            updateVertexFill(newEnd, END_COLOR, START_END_TEXT_COLOR);
        }

        end.current = newEnd;
    }

    const handleStartEnd = (isStart) => {
        resetEdgeColor();
        resetVertexWeight();
        if (markedNodes.current.length === 0) {
            return;
        } else if (start.current === markedNodes.current[0] && isStart) {
            updateStart(undefined);
        } else if (end.current === markedNodes.current[0] && !isStart) {
            updateEnd(undefined);
        } else if (isStart === true) {
            updateStart(markedNodes.current[0]);
        } else if (!isStart) {
            updateEnd(markedNodes.current[0]);
        }
        markedNodes.current = [];
    }

    return (<div className={classes.container}>
            <MultidataInputWithSubmit
                onSubmit={handleAddNode}
                btnLabel={"Add"}
                data={
                    [{
                        type: "text", onChange: (val) => addVal.current = val.target.value, label: "add", noLabel: true
                    }]
                }
            />
            {props.weightedEdges ? <MultidataInputWithSubmit
                btnLabel={"Edge Weight"}
                data={
                    [{
                        type: "number",
                        min: props.minWeight,
                        onChange: (val) => weight.current = val.target.value,
                        label: "Edge Weight",
                        noLabel: true,
                        defaultValue: 0
                    }]
                }
            /> : null}
            {props.startButton && props.endButton &&
                <div className={`${classes.algoNavigationContainer} ${classes.valid}`}>
                    <button className={classes.leftButton} onClick={() => handleStartEnd(true)}>Start</button>
                    <button className={classes.rightButton} onClick={() => handleStartEnd(false)}>End</button>
                </div>
            }
            {props.startButton && !props.endButton &&
                <div className={`${classes.algoNavigationContainer} ${classes.valid}`}>
                    <button className={classes.fullSizeButton} onClick={() => handleStartEnd(true)}>Start</button>
                </div>
            }
            <div className={`${classes.algoNavigationContainer} ${classes.valid}`}>
                <button className={classes.leftButton} onClick={() => previous(1)}>◄</button>
                <button className={classes.rightButton} onClick={() => next(1)}>►</button>
            </div>

            <Modal open={modalIsOpen} onClose={() => setModalIsOpen(false) }>
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