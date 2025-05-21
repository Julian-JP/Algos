import React, {useEffect, useReducer, useRef, useState} from 'react';
import classes from "./GraphVisualisation.module.css"
import useFetch from "../../hooks/useFetch";
import ControlSelector from "../control/ControlSelector.jsx";
import Circle from "../UI/SVG-Components/Circle.jsx";
import configData from "@/configs/config.json"

const GraphVisualisation = props => {

    const [explanation, setExplanation] = useState(null);
    const [graph, graphDispatch] = useReducer(graphReducer, {
        vertices: [],
        edges: [],
        directed: true
    });
    const [svgDimension, setSvgDimension] = useState({ width: 0, height: 0});

    const {isLoading, error, sendRequest} = useFetch();

    const svgRef = useRef();

    const lastMovement = useRef(Date.now());

    function processVertices(newVertices, oldVertices) {
        return newVertices.map(vertex => {
            return {
                ...vertex,
                ...oldVertices.find(v => v.id === vertex.id)
            }
        })
    }

    function graphReducer(graph, graphAction) {
        switch(graphAction.type) {
            case 'redraw_entirely': {
                let new_vertices = graphAction.vertices
                let new_edges = graphAction.edges
                return {
                    ...graph,
                    vertices: new_vertices,
                    edges: new_edges
                };
            }
            case 'redraw': {
                let new_vertices = [...processVertices(graphAction.vertices, graph.vertices)];
                let new_edges = graphAction.edges
                return {
                    ...graph,
                    vertices: new_vertices,
                    edges: new_edges
                };
            }
            case 'addVertex': {
                if (graphAction.vertex !== undefined && ! graph.vertices.find(vertex => vertex.id === graphAction.vertex.id)) {
                    let new_vertices = [...graph.vertices, graphAction.vertex]
                    return {
                        ...graph,
                        vertices: new_vertices,
                    };
                } else {
                    return graph
                }
            }
            case 'removeVertex': {
                let new_vertices = graph.vertices.filter(vertex => vertex.id !== graphAction.vertexId);
                let new_edges = graph.edges.filter(edge => edge.from !== graphAction.vertexId && edge.to !== graphAction.vertexId);
                return {
                    ...graph,
                    vertices: new_vertices,
                    edges: new_edges
                };
            }
            case 'addEdge': {
                if (graphAction.edge !== undefined && ! graph.edges.find(edge => edge.from === graphAction.edge.from && edge.to === graphAction.edge.to)) {
                    let new_edges = [...graph.edges, graphAction.edge]
                    return {
                        ...graph,
                        edges: new_edges
                    };
                } else {
                    return graph;
                }
            }
            case 'removeEdge': {
                let new_edges = graph.edges.filter(edge => edge.from !== graphAction.from || edge.to !== graphAction.to);
                return {
                    ...graph,
                    edges: new_edges
                };
            }
            case 'moveVertex': {
                let vertexIndex = graph.vertices.findIndex(item => item.id === graphAction.vertexId)
                let new_vertices = [...graph.vertices];

                new_vertices[vertexIndex].x = graphAction.newX;
                new_vertices[vertexIndex].y = graphAction.newY;
                return {
                    ...graph,
                    vertices: new_vertices
                };
            }
            case 'changeVertexProperties': {
                let new_vertices = [...graph.vertices];
                graphAction.updateVertices(new_vertices);
                return {
                    ...graph,
                    vertices: new_vertices
                };
            }
            case 'changeEdgeProperties': {
                let new_edges = [...graph.edges];
                new_edges.map(edge => graphAction.updateEdge(edge));
                return {
                    ...graph,
                    edges: new_edges
                };
            }
        }

        return graph;
    }

    useEffect(() => {
        const applyResponse = (response) => {
            setExplanation(response.explanation);
        };
        sendRequest({
            url: configData.BACKEND_URL + `${props.url}/explanation`, method: 'GET'
        }, applyResponse);

    }, [sendRequest]);

    useEffect(() => {
        if (svgRef.current) {
            const width = svgRef.current.clientWidth;
            const height = svgRef.current.clientHeight;
            setSvgDimension({ width, height });
        }
    }, []);

    const generateEdgeId = (from, to) => {
        return from + "-" + to;
    }

    const convertVertex = (item) => {
        let text = item.weight !== undefined ? item.value + "|" + item.weight : item.value;
        return <Circle
            handleDragStart={handleDragStart}
            handleDrag={handleDrag}
            handleDragStop={handleDragStop}
            r={20}
            cx={item.x}
            cy={item.y}
            key={item.id}
            id={item.id}
            fill={item.fill}
            opacity={item.opacity}
            textFill={item.textFill}
            stroke={item.stroke}
            value={text}
            draggable={item.draggable}
            onLeftClick={item.onClick}
            onRightClick={item.onRightClick}
        />
    }

    const getVertex = (id) => {
        return  graph.vertices.find(item => item.id === id);
    }

    const convertEdge = (edge) => {
        if (edge.from === edge.to && edge.from != null) {
            return convertSelfEdge(edge);
        }

        let vertexFrom = getVertex(edge.from);
        let vertexTo = getVertex(edge.to);

        if (graph.directed) {
            return convertDirectedEdge(edge, vertexFrom, vertexTo)
        } else {
            return convertUndirectedEdge(edge, vertexFrom, vertexTo);
        }
    }

    const convertArrow = (item, lineCoordinates) => {
        return <g key={"arrowGroup" + generateEdgeId(item.from, item.to)}>
            <marker id="arrowheadblue"
                    markerWidth={10}
                    markerHeight={3}
                    refX="0"
                    refY="1.5"
                    orient="auto"
                    key={"marker-blue" + generateEdgeId(item.from, item.to)}
                    fill={"blue"}
            >
                <polygon
                    points="0 0, 10 1.5, 0 3"
                    key={"polygon1" + generateEdgeId(item.from, item.to)}
                />
            </marker>
            <marker id="arrowheadred"
                    markerWidth={10}
                    markerHeight={3}
                    refX="0"
                    refY="1.5"
                    orient="auto"
                    key={"marker-red" + generateEdgeId(item.from, item.to)}
                    fill={"red"}
            >
                <polygon
                    points="0 0, 10 1.5, 0 3"
                    key={"polygon2" + generateEdgeId(item.from, item.to)}
                />
            </marker>
            <marker id="arrowheadgray"
                    markerWidth={10}
                    markerHeight={3}
                    refX="0"
                    refY="1.5"
                    orient="auto"
                    key={"marker-gray" + generateEdgeId(item.from, item.to)}
                    fill={"gray"}
            >
                <polygon
                    points="0 0, 10 1.5, 0 3"
                    key={"polygon" + generateEdgeId(item.from, item.to)}
                />
            </marker>
            <marker id="arrowheadblack"
                    markerWidth={10}
                    markerHeight={3}
                    refX="0"
                    refY="1.5"
                    orient="auto"
                    key={"marker-black" + generateEdgeId(item.from, item.to)}
                    fill={"black"}
            >
                <polygon
                    points="0 0, 10 1.5, 0 3"
                    key={"polygon" + generateEdgeId(item.from, item.to)}
                />
            </marker>
            <marker id="arrowheadwhite"
                    markerWidth={10}
                    markerHeight={3}
                    refX="0"
                    refY="1.5"
                    orient="auto"
                    key={"marker-white" + generateEdgeId(item.from, item.to)}
                    fill={"white"}
            >
                <polygon
                    points="0 0, 10 1.5, 0 3"
                    key={"polygon" + generateEdgeId(item.from, item.to)}
                />
            </marker>
            <line x1={lineCoordinates.x1}
                  y1={lineCoordinates.y1}
                  x2={lineCoordinates.x1 - (lineCoordinates.x1 - lineCoordinates.x2) / 3}
                  y2={lineCoordinates.y1 - (lineCoordinates.y1 - lineCoordinates.y2) / 3}
                  stroke={item.stroke}
                  strokeWidth={3}
                  markerEnd={item.stroke === "white" ? "url(#arrowheadwhite)" : item.stroke === "black" ? "url(#arrowheadblack)" : (item.stroke === "blue" ? "url(#arrowheadblue)" : (item.stroke === "red" ? "url(#arrowheadred)" : "url(#arrowheadgray)"))}
                  key={"markerline" + generateEdgeId(item.from, item.to)}
            />
            <line
                x1={lineCoordinates.x1}
                x2={lineCoordinates.x2}
                y1={lineCoordinates.y1}
                y2={lineCoordinates.y2}
                id={generateEdgeId(item.from, item.to)}
                stroke={item.stroke}
                strokeWidth={3}
                key={"line" + generateEdgeId(item.from, item.to)}
            />
        </g>
    }

    const convertDirectedEdge = (item, vertexFrom, vertexTo) => {
        const OFFSETLINE = 5;
        const OFFSETTEXT = 20;

        let lineCoordinates = calculateEdge(vertexFrom.x, vertexFrom.y, vertexTo.x, vertexTo.y, OFFSETLINE);
        let textCoordinates = calculateEdge(vertexFrom.x, vertexFrom.y, vertexTo.x, vertexTo.y, OFFSETTEXT);

        return <g key={"directedEdgeGroup" + generateEdgeId(item.from, item.to)}>
            {convertArrow(item, lineCoordinates)}
            {item.weight != null ? <text
                alignmentBaseline={"middle"}
                dominantBaseline={"middle"}
                textAnchor={"middle"}
                x={textCoordinates.x2 + (textCoordinates.x1 - textCoordinates.x2) / 2}
                y={textCoordinates.y2 + (textCoordinates.y1 - textCoordinates.y2) / 2}
                stroke={item.stroke}
                key={"Text" + generateEdgeId(item.from, item.to)}
            >{convertWeightToText(item.weight)}</text> : null}
        </g>
    }

    const convertUndirectedEdge = (item, vertexFrom, vertexTo) => {
        const OFFSETLINE = 0;
        const OFFSETTEXT = 15

        let lineCoordinates = calculateEdge(vertexFrom.x, vertexFrom.y, vertexTo.x, vertexTo.y, OFFSETLINE);
        let textCoordinates = calculateEdge(vertexFrom.x, vertexFrom.y, vertexTo.x, vertexTo.y, OFFSETTEXT);



        return <g key={"edgeGroup" + generateEdgeId(item.from, item.to)}>
            <line
                x1={lineCoordinates.x1}
                x2={lineCoordinates.x2}
                y1={lineCoordinates.y1}
                y2={lineCoordinates.y2}
                id={generateEdgeId(item.from, item.to)}
                stroke={item.stroke}
                strokeWidth={3}
                key={"line" + generateEdgeId(item.from, item.to)}
            />
            {item.weight != null && item.from > item.to ? <text
                alignmentBaseline={"middle"}
                dominantBaseline={"middle"}
                textAnchor={"middle"}
                x={textCoordinates.x2 + (textCoordinates.x1 - textCoordinates.x2) / 2}
                y={textCoordinates.y2 + (textCoordinates.y1 - textCoordinates.y2) / 2}
                stroke={item.stroke}
                key={"Text" + generateEdgeId(item.from, item.to)}
            >{convertWeightToText(item.weight)}</text> : null}
        </g>
    }

    const convertSelfEdge = (item) => {
        return <g key={"selfEdgeGroup" + generateEdgeId(item.from, item.to)}>
            <circle
                r={15}
                cx={getVertex(item.to).x}
                cy={getVertex(item.to).y + 25}
                key={generateEdgeId(item.from, item.to)}
                id={generateEdgeId(item.from, item.to)}
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
                stroke={item.stroke}
            >{convertWeightToText(item.weight)}</text> : null}
        </g>
    }

    const calculateEdge = (cx1, cy1, cx2, cy2, distance) => {
        // Berechne den Vektor zwischen den beiden Kreisen
        let dx = cx2 - cx1;
        let dy = cy2 - cy1;

        // Berechne die Länge des Vektors
        let length = Math.sqrt(dx * dx + dy * dy);

        // Normalisiere den Vektor
        let unitX = dx / length;
        let unitY = dy / length;

        // Berechne den senkrechten Vektor
        let perpX = - unitY;
        let perpY = unitX;

        // Berechne die Koordinaten der parallelen Linien
        let x1 = cx1 + (perpX * distance);
        let y1 = cy1 + (perpY * distance);
        let x2 = cx2 + (perpX * distance);
        let y2 = cy2 + (perpY * distance);
        return {x1, x2, y1, y2};
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

    const handleDrag = (id, newX, newY) => {
        if (Date.now() - lastMovement.current > 40) {
            graphDispatch({type: 'moveVertex', newX, newY, vertexId: id});
            lastMovement.current = Date.now();
        }
    }

    const handleDragStart = (id) => {
    }
    const handleDragStop = (id, newX, newY) => {
        graphDispatch({type: 'moveVertex', newX, newY, vertexId: id});
    }


    const explanationDiv = isLoading ? <div className={classes.explanation}>Loading...</div> : error ?
        null :
        <div className={classes.explanation} dangerouslySetInnerHTML={{__html: explanation}}></div>

    return (<div className={classes.background}>
        <div className={classes.card}>
            <svg ref={svgRef} key={"drawingBoard"} className={classes.canvas} width={"100%"} height={"100%"}>
                {graph.edges.map(item => convertEdge(item))}
                {graph.vertices.map(item => convertVertex(item))}
            </svg>
            <ControlSelector
                svgWidth={svgDimension.width}
                svgHeight={svgDimension.height}
                class={classes.control}
                type={props.displayedType}
                graph={graph}
                graphDispatch={graphDispatch}
                url={props.url}
            />
        </div>
        {explanationDiv}
    </div>)
}

export default GraphVisualisation;
