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

    const DEFAULT_LINE_COLOR = "white"
    const DEFAULT_TEXT_COLOR = "black";

    const START_END_TEXT_COLOR = "black";

    const markedNodes = useRef([]);
    const addVal = useRef(null);

    const graphSteps = useRef([]);
    const currentStep = useRef(undefined);

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
                    id: from + "-" + to
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

    const resetEdgeColor = () => {
        currentStep.current = undefined;
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
        if (currentStep.current !== undefined) {
            if (currentStep.current >= graphSteps.current.length) {
                return;
            }

            currentStep.current++;
            console.log(graphSteps)

            let nextState = graphSteps.current[currentStep.current];
            props.graphDispatch({
                type: 'redraw',
                vertices: nextState.vertices,
                edges: nextState.edges
            })
            start.current = nextState.start;
            end.current = nextState.end;
            return;
        }

        function createGraphFromJSON(response) {
           graphSteps.current = response;

            currentStep.current = 1;

            let nextState = graphSteps.current[currentStep.current];
            props.graphDispatch({
                type: 'redraw',
                vertices: nextState.vertices,
                edges: nextState.edges
            })
            start.current = nextState.start;
            end.current = nextState.end;
        }

        sendRequest({
            url: SERVER_URL + props.type + '/step',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: {
                edges: props.graph.edges,
                start: start.current,
                vertices: props.graph.vertices,
                end: end.current
            }
        }, (response => {
            createGraphFromJSON(response);
        }));
    }

    const previous = () => {
        if (currentStep.current <= 0) return;

        currentStep.current--;

        let previousState = graphSteps.current[currentStep.current];
        props.graphDispatch({
            type: 'redraw',
            vertices: previousState.vertices,
            edges: previousState.edges
        })
        start.current = previousState.start;
        end.current = previousState.end;
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