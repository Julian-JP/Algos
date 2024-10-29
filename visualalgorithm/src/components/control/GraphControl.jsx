import React, {useEffect, useRef, useState} from "react";
import classes from "./GraphControl.module.css";
import useFetch from "../../hooks/useFetch";
import MultidataInputWithSubmit from "../UI/Input/MultidataInputWithSubmit.jsx";
import Modal from "../UI/Modal.jsx";
import {node} from "globals";

const GraphControl = (props) => {

    const DEFAULT_VERTEX_COLOR = "white";
    const START_END_COLOR = "blue";
    const DEFAULT_LINE_COLOR = "black"

    const DEFAULT_VERTEX_TEXT_COLOR = "black";
    const START_END_TEXT_COLOR = "white";

    const markedNodes = useRef([]);
    const addVal = useRef(null);
    const prev = useRef([]);

    const start = useRef(undefined);
    const end = useRef(undefined);

    const weight = useRef(0);

    const [modalIsOpen, setModalIsOpen] = useState(true);

    const {isLoading, error, sendRequest} = useFetch();

    const handleAddNode = (event) => {
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
            vertexId: nodeIndex,
            updateVertex: vertex => {
                    vertex.opacity = marked ? 0.5 : 1;
                }
        })
    }

    const markNode = (nodeIndex) => {
        console.log(markedNodes)
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
        let edge = props.graph.edges.find((val) => val.from === from && val.to === to);
        if (edge === undefined) {
            props.graphDispatch({
                type: 'addEdge',
                edge: {
                    color: DEFAULT_LINE_COLOR,
                    weight: props.weightedEdges ? weight.current : null,
                    from: from,
                    to: to,
                    stroke: "white",
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
            vertices: JSON.parse(JSON.stringify(props.graph.vertices)),
            edges: JSON.parse(JSON.stringify(props.graph.edges)),
            start: start,
            end: end
        }
        prev.current.push(newPrevObj);
    }

    const getAdjacencyMatrix = () => {
        let n = props.graph.vertices.length;
        let adj = Array.from({ length: n }, () => Array(n).fill(null));
        props.graph.edges.forEach(edge => {
            const fromIndex = props.graph.vertices.findIndex(v => v.id === edge.from)
            const toIndex = props.graph.vertices.findIndex(v => v.id === edge.to)
            adj[fromIndex][toIndex] = {
                color: edge.color,
                weight: edge.weight
            };
        });
        return adj;
    }

    const next = () => {

        function createGraphFromJSON(response) {
        }

        sendRequest({
            url: 'https://julian-laux.de:8080/algos/' + props.type + '/step',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {edges: getAdjacencyMatrix(), start: start.current, vertices: props.graph.vertices, end: end.current}
        }, (response => {
            updatePreviousStack().then(() => createGraphFromJSON(response));
        }));
    }

    const previous = () => {
        if (prev.current.length < 1) return;

        let previousState = prev[prev.current.length - 1];
        edges.current = previousState.edges;
        vertices.current = previousState.vertices;
        start.current = previousState.start;
        end.current = previousState.end;

        prev.current.pop();
    }


    const updateVertexFill = (vertexId, fillColor, textColor) => {
        props.graphDispatch({
            type: 'changeVertexProperties',
            vertexId: vertexId,
            updateVertex: vertex =>  {
                vertex.fill = fillColor;
                vertex.opacity = 1;
                vertex.textFill = textColor;
            }
        })
    }

    const updateStart = (newStart) => {
        if (start.current !== undefined && end.current !== start.current) {
            updateVertexFill(start.current, DEFAULT_VERTEX_COLOR, DEFAULT_VERTEX_TEXT_COLOR);
        }
        if (newStart !== undefined) {
            updateVertexFill(newStart, START_END_COLOR, START_END_TEXT_COLOR);
        }
        start.current = newStart;
    }

    const updateEnd = (newEnd) => {
        if (end.current !== undefined && end.current !== start.current) {
            updateVertexFill(end.current, DEFAULT_VERTEX_COLOR, DEFAULT_VERTEX_TEXT_COLOR);
        }
        if (newEnd !== undefined) {
            updateVertexFill(newEnd, START_END_COLOR, START_END_TEXT_COLOR);
        }

        end.current = newEnd;
    }

    const handleStartEnd = (isStart) => {
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

    const clearPreviousStack = () => {
        prev.current = [{edges: edges, vertices: vertices, start: start, end: end}];
        for (let i = 0; i < edges.current.length; i++) {
            for (let j = 0; j < edges.current.length; j++) {
                if (edges.current[i][j] !== null) edges.current[i][j].color = DEFAULT_LINE_COLOR;
            }
        }
        for (let i = 0; i < vertices.current.length; i++) {
            vertices.current[i].value = vertices.current[i].id;
        }
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