import React, {useEffect, useState} from 'react';
import classes from "./GraphVisualisation.module.css"
import useFetch from "../../hooks/useFetch";
import ControlSelector from "../control/ControlSelector";
import Circle from "../UI/SVG-Components/Circle";

const GraphVisualisation = props => {

    const [explanation, setExplanation] = useState(null);
    const [edges, setEdges] = useState([]);
    const [displayedEdges, setDisplayedEdges] = useState([]);
    const [vertices, setVertices] = useState([]);
    const [verticesLocation, setVerticesLocation] = useState([]);
    const [displayedVertices, setDisplayedVertices] = useState([]);
    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        const applyResponse = (response) => {
            setExplanation(response.explanation);
        };
        sendRequest({
            url: `http://localhost:8080/algos/${props.url}/explanation`, method: 'GET'
        }, applyResponse);

    }, [sendRequest]);

    useEffect(() => {
        convertedEdges();
    }, [edges]);

    useEffect(() => {
        convertVertices();
    }, [vertices]);

    const convertVertices = () => {
        setVerticesLocation((old) => {
            return vertices.map((item) => {
                let index = old.findIndex(vertex => vertex.id === item.id)
                if (index >= 0) {
                    item.x = old[index].x;
                    item.y = old[index].y
                }
                return item;
            });
        });
        setDisplayedVertices(
            vertices.map((item) => {
                return <Circle
                    handleDrag={handleDrag}
                    r={20}
                    cx={item.x}
                    cy={item.y}
                    key={item.id}
                    id={item.id}
                    fill={item.fill}
                    opacity={item.opacity}
                    textFill={item.textFill}
                    stroke={item.stroke}
                    value={item.value}
                    draggable={item.draggable}
                    onLeftClick={item.onClick}
                    onRightClick={item.onRightClick}
                />
            })
        );
    }

    const getVertex = (id) => {
        return verticesLocation.find(item => item.id === id);
    }

    const getInitVertex = (id) => {
        return vertices.find(item => item.id === id)
    }

    const convertedEdges = () => {
        setDisplayedEdges(
            edges.map((item) => {
                let textCoordinates
                if (item.from === item.to && item.from != null) {
                    return <g key={"group" + item.id}>
                        <circle
                            r={15}
                            cx={getVertex(item.to).x}
                            cy={getVertex(item.to).y + 25}
                            key={item.id}
                            id={item.id}
                            fill={"none"}
                            stroke={item.stroke}
                            strokeWidth={3}
                        />
                        {item.weight !== null ? <text
                            alignmentBaseline={"middle"}
                            dominantBaseline={"middle"}
                            textAnchor={"middle"}
                            x={getVertex(item.to).x}
                            y={getVertex(item.to).y + 52}
                        >{convertWeightToText(item.weight)}</text> : null}
                    </g>
                }

                let x1, x2, y1, y2;
                let arrow;

                if (item.from != undefined && item.to !== undefined) {

                    let vertexFromX, vertexToX, vertexFromY, vertexToY;

                    if (getVertex(item.from) && getVertex(item.to)) {
                        vertexFromX = getVertex(item.from).x;
                        vertexToX = getVertex(item.to).x;
                        vertexFromY = getVertex(item.from).y;
                        vertexToY = getVertex(item.to).y;
                    } else if (getInitVertex(item.from) && getInitVertex(item.to)) {
                        vertexFromX = getInitVertex(item.from).x;
                        vertexToX = getInitVertex(item.to).x;
                        vertexFromY = getInitVertex(item.from).y;
                        vertexToY = getInitVertex(item.to).y;
                    }

                    const OFFSETLINE = 4;
                    const OFFSETTEXT = 20

                    let {
                        x1,
                        x2,
                        y1,
                        y2
                    } = calcOffsetCoordinate(vertexFromX, vertexToX, vertexFromY, vertexToY, OFFSETLINE);
                    textCoordinates = calcOffsetCoordinate(vertexFromX, vertexToX, vertexFromY, vertexToY, OFFSETTEXT);

                    arrow = <g key={"group" + item.id}>
                        <marker id="arrowheadblue"
                                markerWidth={10}
                                markerHeight={3}
                                refX="0"
                                refY="1.5"
                                orient="auto"
                                key={"marker-blue" + item.id}
                                fill={"blue"}
                        >
                            <polygon
                                points="0 0, 10 1.5, 0 3"
                                key={"polygon" + item.id}
                            />
                        </marker>
                        <marker id="arrowheadred"
                                markerWidth={10}
                                markerHeight={3}
                                refX="0"
                                refY="1.5"
                                orient="auto"
                                key={"marker-red" + item.id}
                                fill={"red"}
                        >
                            <polygon
                                points="0 0, 10 1.5, 0 3"
                                key={"polygon" + item.id}
                            />
                        </marker>
                        <marker id="arrowheadblack"
                                markerWidth={10}
                                markerHeight={3}
                                refX="0"
                                refY="1.5"
                                orient="auto"
                                key={"marker-black" + item.id}
                                fill={"black"}
                        >
                            <polygon
                                points="0 0, 10 1.5, 0 3"
                                key={"polygon" + item.id}
                            />
                        </marker>
                        <line x1={x1}
                              y1={y1}
                              x2={x1 - (x1 - x2) / 3}
                              y2={y1 - (y1 - y2) / 3}
                              stroke={item.stroke}
                              strokeWidth={3}
                              markerEnd={item.stroke === "black" ? "url(#arrowheadblack)" : (item.stroke === "blue" ? "url(#arrowheadblue)" : "url(#arrowheadred)")}
                              key={"markerline" + item.id}
                        />
                        <line
                            x1={x1}
                            x2={x2}
                            y1={y1}
                            y2={y2}
                            id={item.id}
                            stroke={item.stroke}
                            strokeWidth={3}
                            key={item.id}
                        />
                    </g>
                    if (item.directed) {
                        return <g>
                            {arrow}
                            {item.weight != null ? <text
                                alignmentBaseline={"middle"}
                                dominantBaseline={"middle"}
                                textAnchor={"middle"}
                                x={textCoordinates.x2 + (textCoordinates.x1 - textCoordinates.x2) / 2}
                                y={textCoordinates.y2 + (textCoordinates.y1 - textCoordinates.y2) / 2}
                            >{convertWeightToText(item.weight)}</text> : null}
                        </g>
                    } else {
                        return <g>
                            <line
                                x1={vertexFromX}
                                x2={vertexToX}
                                y1={vertexFromY}
                                y2={vertexToY}
                                id={item.id}
                                stroke={item.stroke}
                                strokeWidth={3}
                                key={item.id}
                            />
                            {item.weight != null && item.from > item.to ? <text
                                alignmentBaseline={"middle"}
                                dominantBaseline={"middle"}
                                textAnchor={"middle"}
                                x={textCoordinates.x2 + (textCoordinates.x1 - textCoordinates.x2) / 2}
                                y={textCoordinates.y2 + (textCoordinates.y1 - textCoordinates.y2) / 2}
                            >{convertWeightToText(item.weight)}</text> : null}
                        </g>
                    }
                } else {
                    x1 = item.x1;
                    x2 = item.x2;
                    y1 = item.y1;
                    y2 = item.y2;
                    return <g>
                        <line
                        x1={x1}
                        x2={x2}
                        y1={y1}
                        y2={y2}
                        id={item.id}
                        stroke={item.stroke}
                        strokeWidth={3}
                        key={item.id}
                    /></g>
                }

            })
        );
    }

    const convertWeightToText = (weight) => {
        if (weight === "Infinity") {
            return "∞"
        } else if (weight === "-Infinity") {
            return "-∞";
        } else {
            return weight;
        }
    }

    const calcOffsetCoordinate = (vertexFromX, vertexToX, vertexFromY, vertexToY, offset) => {
        let distanceX = Math.abs(vertexToX - vertexFromX);
        let distanceY = Math.abs(vertexToY - vertexFromY);

        if (vertexFromX <= vertexToX && distanceX >= distanceY) {
            return {x1: vertexFromX, x2: vertexToX, y1: (vertexFromY + offset), y2: (vertexToY + offset)};
        } else if (vertexFromX > vertexToX && distanceX >= distanceY) {
            return {x1: vertexFromX, x2: vertexToX, y1: (vertexFromY - offset), y2: (vertexToY - offset)};
        } else if (vertexFromX <= vertexToX && distanceX < distanceY) {
            return {x1: (vertexFromX - offset), x2: (vertexToX - offset), y1: vertexFromY, y2: vertexToY};
        } else {
            return {x1: (vertexFromX + offset), x2: (vertexToX + offset), y1: vertexFromY, y2: vertexToY};
        }
    }

    const handleDrag = (id, newX, newY) => {
        setVerticesLocation(old => {
            let ret = [];
            for (let i = 0; i < old.length; i++) {
                if (old[i].value === id) {
                    old[i].x = newX;
                    old[i].y = newY;
                    ret.push(old[i]);
                } else {
                    ret.push(old[i]);
                }
            }
            return ret;
        })
        setEdges(old => {
            let ret = [];
            for (let i = 0; i < old.length; i++) {
                if (old[i].from === id) {
                    old[i].x1 = newX;
                    old[i].y1 = newY;
                    ret.push(old[i]);
                } else if (old[i].to === id) {
                    old[i].x2 = newX;
                    old[i].y2 = newY;
                    ret.push(old[i]);
                } else {
                    ret.push(old[i]);
                }
            }
            return ret;
        });
    }

    const explanationDiv = isLoading ? <div className={classes.explanation}>Loading...</div> : error ?
        <div className={classes.explanation}>Fehler beim Laden</div> :
        <div className={classes.explanation} dangerouslySetInnerHTML={{__html: explanation}}></div>

    return (<div className={classes.background}>
        <div className={classes.card}>
            <svg className={classes.canvas} width={"100%"} height={"100%"}>
                {displayedEdges}
                {displayedVertices}
            </svg>
            <ControlSelector
                class={classes.control}
                type={props.displayedType}
                setVertices={setVertices}
                setEdges={setEdges}
                url={props.url}
            />
        </div>
        {explanationDiv}
    </div>)
}

export default GraphVisualisation;
