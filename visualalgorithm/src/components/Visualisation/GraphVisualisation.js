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
        setDisplayedVertices(
            vertices.map(item => {
                return <Circle
                    handleDrag={handleDrag}
                    r={20}
                    cx={item.x}
                    cy={item.y}
                    key={item.x + "" + item.y + item.textFill}
                    id={item.value}
                    fill={item.fill}
                    textFill={item.textFill}
                    stroke={item.stroke}
                    value={item.value}
                    draggable={item.draggable}
                    onLeftClick={item.onClick}
                />
            })
        )
    }

    const convertedEdges = () => {
        setDisplayedEdges(
            edges.map((item) => {
                return <line
                    x1={item.x1}
                    x2={item.x2}
                    y1={item.y1}
                    y2={item.y2}
                    stroke={item.stroke}
                    strokeWidth={3}
                    key={item.x1 + "" + item.x2}
                />
            })
        );
    }

    const addVertex = (vertex) => {
        setVertices((old) => [...old, vertex]);
    }

    const removeVertex = (id) => {
        setVertices((old) => {
            old.filter((vertex) => vertex.id === id);
            return [...old];
        });
    }

    const addEdge = (edge) => {
        setEdges((old) => {
            return [...old, edge];
        });
    }

    const removeEdge = (from, to) => {
        setEdges(old => {
            old.filter((edge) => edge.from === from && edge.to === to);
            return [...old];
        });
    }

    const handleDrag = (id, newX, newY) => {
        setEdges(old => {
            let ret = [];
            for (let i=0; i<old.length; i++) {
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
                type={props.displayedType}
                vertex={{addVertex, removeVertex, setVertices}}
                edge={{addEdge, removeEdge, setEdges}}
                url={props.url}
            />
        </div>
        {explanationDiv}
    </div>)
}

export default GraphVisualisation;
