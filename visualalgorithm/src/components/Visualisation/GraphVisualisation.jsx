import React, {useEffect, useReducer, useRef, useState} from 'react';
import classes from "./GraphVisualisation.module.css"
import useFetch from "../../hooks/useFetch";
import ControlSelector from "../control/ControlSelector.jsx";
import Circle from "../UI/SVG-Components/Circle.jsx";

const GraphVisualisation = props => {

    const [explanation, setExplanation] = useState(null);
    const [graph, graphDispatch] = useReducer(graphReducer, {
        vertices: [],
        edges: []
    });
    const [svgDimension, setSvgDimension] = useState({ width: 0, height: 0});

    const {isLoading, error, sendRequest} = useFetch();

    const svgRef = useRef();

    const lastMovement = useRef(Date.now());

    function graphReducer(graph, graphAction) {
        switch(graphAction.type) {
            case 'redraw': {
                graph.vertices = graphAction.vertices;
                graph.edges = graphAction.edges
                return {vertices: graph.vertices, edges: graph.edges};
            }
            case 'addVertex': {
                if (graphAction.vertex !== undefined && ! graph.vertices.find(vertex => vertex.id === graphAction.vertex.id)) {
                    graph.vertices.push(graphAction.vertex)
                }
                return {vertices: graph.vertices, edges: graph.edges};
            }
            case 'removeVertex': {
                graph.vertices = graph.vertices.filter(vertex => vertex.id !== graphAction.vertexId);
                graph.edges = graph.edges.filter(edge => edge.from !== graphAction.vertexId && edge.to !== graphAction.vertexId);
                return {vertices: graph.vertices, edges: graph.edges};
            }
            case 'addEdge': {
                if (graphAction.edge !== undefined && ! graph.edges.find(edge => edge.from === graphAction.edge.from && edge.to === graphAction.edge.to)) {
                    graph.edges.push(graphAction.edge)
                }
                return {vertices: graph.vertices, edges: graph.edges};
            }
            case 'removeEdge': {
                graph.edges = graph.edges.filter(edge => edge.from !== graphAction.from || edge.to !== graphAction.to);
                return {vertices: graph.vertices, edges: graph.edges};
            }
            case 'moveVertex': {
                let vertexIndex = graph.vertices.findIndex(item => item.id === graphAction.vertexId)
                graph.vertices[vertexIndex].x = graphAction.newX;
                graph.vertices[vertexIndex].y = graphAction.newY;
                return {vertices: graph.vertices, edges: graph.edges};
            }
            case 'changeVertexProperties': {
                graphAction.updateVertices(graph.vertices);
                return {vertices: graph.vertices, edges: graph.edges};
            }
            case 'changeEdgeProperties': {
                graph.edges.map(edge => graphAction.updateEdge(edge));
                return {vertices: graph.vertices, edges: graph.edges};
            }
        }
    }

    useEffect(() => {
        const applyResponse = (response) => {
            setExplanation(response.explanation);
        };
        sendRequest({
            url: `https://julian-laux.de:8080/algos/${props.url}/explanation`, method: 'GET'
        }, applyResponse);

    }, [sendRequest]);

    useEffect(() => {
        if (svgRef.current) {
            const width = svgRef.current.clientWidth;
            const height = svgRef.current.clientHeight;
            setSvgDimension({ width, height });
        }
    }, []);

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

    const convertEdge = (item) => {
        if (item.from === item.to && item.from != null) {
            return convertSelfEdge(item);
        }

        let vertexFrom = getVertex(item.from);
        let vertexTo = getVertex(item.to);

        if (item.directed) {
            return convertDirectedEdge(item, vertexFrom, vertexTo)
        } else {
            return convertUndirectedEdge(item, vertexFrom, vertexTo);
        }
    }

    const convertArrow = (item, lineCoordinates) => {
        return <g key={"arrowGroup" + item.id}>
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
                    key={"polygon1" + item.id}
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
                    key={"polygon2" + item.id}
                />
            </marker>
            <marker id="arrowheadgray"
                    markerWidth={10}
                    markerHeight={3}
                    refX="0"
                    refY="1.5"
                    orient="auto"
                    key={"marker-gray" + item.id}
                    fill={"gray"}
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
            <marker id="arrowheadwhite"
                    markerWidth={10}
                    markerHeight={3}
                    refX="0"
                    refY="1.5"
                    orient="auto"
                    key={"marker-white" + item.id}
                    fill={"white"}
            >
                <polygon
                    points="0 0, 10 1.5, 0 3"
                    key={"polygon" + item.id}
                />
            </marker>
            <line x1={lineCoordinates.x1}
                  y1={lineCoordinates.y1}
                  x2={lineCoordinates.x1 - (lineCoordinates.x1 - lineCoordinates.x2) / 3}
                  y2={lineCoordinates.y1 - (lineCoordinates.y1 - lineCoordinates.y2) / 3}
                  stroke={item.stroke}
                  strokeWidth={3}
                  markerEnd={item.stroke === "white" ? "url(#arrowheadwhite)" : item.stroke === "black" ? "url(#arrowheadblack)" : (item.stroke === "blue" ? "url(#arrowheadblue)" : (item.stroke === "red" ? "url(#arrowheadred)" : "url(#arrowheadgray)"))}
                  key={"markerline" + item.id}
            />
            <line
                x1={lineCoordinates.x1}
                x2={lineCoordinates.x2}
                y1={lineCoordinates.y1}
                y2={lineCoordinates.y2}
                id={item.id}
                stroke={item.stroke}
                strokeWidth={3}
                key={"line" + item.id}
            />
        </g>
    }

    const convertDirectedEdge = (item, vertexFrom, vertexTo) => {
        const OFFSETLINE = 5;
        const OFFSETTEXT = 20;

        let lineCoordinates = calculateEdge(vertexFrom.x, vertexFrom.y, vertexTo.x, vertexTo.y, OFFSETLINE);
        let textCoordinates = calculateEdge(vertexFrom.x, vertexFrom.y, vertexTo.x, vertexTo.y, OFFSETTEXT);

        return <g key={"directedEdgeGroup" + item.id}>
            {convertArrow(item, lineCoordinates)}
            {item.weight != null ? <text
                alignmentBaseline={"middle"}
                dominantBaseline={"middle"}
                textAnchor={"middle"}
                x={textCoordinates.x2 + (textCoordinates.x1 - textCoordinates.x2) / 2}
                y={textCoordinates.y2 + (textCoordinates.y1 - textCoordinates.y2) / 2}
                stroke={item.stroke}
                key={"Text" + item.id}
            >{convertWeightToText(item.weight)}</text> : null}
        </g>
    }

    const convertUndirectedEdge = (item, vertexFrom, vertexTo) => {
        const OFFSETLINE = 0;
        const OFFSETTEXT = 15

        let lineCoordinates = calculateEdge(vertexFrom.x, vertexFrom.y, vertexTo.x, vertexTo.y, OFFSETLINE);
        let textCoordinates = calculateEdge(vertexFrom.x, vertexFrom.y, vertexTo.x, vertexTo.y, OFFSETTEXT);



        return <g key={"edgeGroup" + item.id}>
            <line
                x1={lineCoordinates.x1}
                x2={lineCoordinates.x2}
                y1={lineCoordinates.y1}
                y2={lineCoordinates.y2}
                id={item.id}
                stroke={item.stroke}
                strokeWidth={3}
                key={"line" + item.id}
            />
            {item.weight != null && item.from > item.to ? <text
                alignmentBaseline={"middle"}
                dominantBaseline={"middle"}
                textAnchor={"middle"}
                x={textCoordinates.x2 + (textCoordinates.x1 - textCoordinates.x2) / 2}
                y={textCoordinates.y2 + (textCoordinates.y1 - textCoordinates.y2) / 2}
                stroke={item.stroke}
                key={"Text" + item.id}
            >{convertWeightToText(item.weight)}</text> : null}
        </g>
    }

    const convertSelfEdge = (item) => {
        return <g key={"selfEdgeGroup" + item.id}>
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
