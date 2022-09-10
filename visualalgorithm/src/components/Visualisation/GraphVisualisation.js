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
            let ret = vertices.map((item) => {
                let index = old.findIndex(vertex => vertex.id === item.id)
                if (index >= 0) {
                    item.x = old[index].x;
                    item.y = old[index].y
                }
                return item;
            })
            return ret;
        });
        setDisplayedVertices(
            vertices.map((item, index) => {
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
                if (item.from === item.to) {
                    return <circle
                        r={15}
                        cx={getVertex(item.to).x}
                        cy={getVertex(item.to).y + 25}
                        key={item.id}
                        id={item.id}
                        fill={"none"}
                        stroke={item.stroke}
                        strokeWidth={3}
                    />
                }

                let x1, x2, y1, y2;
                let arrow;
                let offsetflip = (getVertex(item.from).x < getVertex(item.to).x && getVertex(item.from).y < getVertex(item.to).y)
                || (getVertex(item.from).x > getVertex(item.to).x && getVertex(item.from).y > getVertex(item.to).y) ? -1 : 1;

                if (item.from != undefined && item.to !== undefined) {
                    let offset = item.from > item.to ? 3 : -3;
                    if (getVertex(item.from) && getVertex(item.to)) {
                        x1 = getVertex(item.from).x + (offset * offsetflip);
                        x2 = getVertex(item.to).x + (offset * offsetflip);
                        y1 = getVertex(item.from).y + offset;
                        y2 = getVertex(item.to).y + offset;
                    } else if (getInitVertex(item.from) && getInitVertex(item.to)) {
                        x1 = getInitVertex(item.to).x + offset;
                        x2 = getInitVertex(item.from).x + offset;
                        y1 = getInitVertex(item.from).y + offset;
                        y2 = getInitVertex(item.to).y + offset;
                    }
                    arrow = <g key={"marker" + item.id}>
                        <defs>
                            <marker id="arrowhead" markerWidth={10} markerHeight={3}
                                    refX="0" refY="1.5" orient="auto">
                                <polygon points="0 0, 10 1.5, 0 3" fill={item.stroke}/>
                            </marker>
                        </defs>
                        <line x1={x1} y1={y1} x2={x1-(x1-x2)/3} y2={y1-(y1-y2)/3} stroke={item.stroke} strokeWidth={3}
                              markerEnd="url(#arrowhead)"/>
                    </g>
                } else {
                    x1 = item.x1;
                    x2 = item.x2;
                    y1 = item.y1;
                    y2 = item.y2;
                }
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
                    />
                    {item.directed ? arrow : null}
                </g>
            })
        );
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
