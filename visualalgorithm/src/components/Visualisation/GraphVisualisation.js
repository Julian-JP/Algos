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
            let ret = vertices.map((item, index) => {
                if (index < old.length) {
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
        );
    }

    const convertedEdges = () => {
        setDisplayedEdges(
            edges.map((item) => {
                let x1,x2,y1,y2;

                if (item.from != undefined && item.to !== undefined) {
                    if (verticesLocation[item.from] && verticesLocation[item.to]) {
                        x1 = verticesLocation[item.from].x;
                        x2 = verticesLocation[item.to].x;
                        y1 = verticesLocation[item.from].y;
                        y2 = verticesLocation[item.to].y;
                    } else if (vertices[item.from] && vertices[item.to]) {
                        x1 = vertices[item.from].x;
                        x2 = vertices[item.to].x;
                        y1 = vertices[item.from].y;
                        y2 = vertices[item.to].y;
                    }
                } else {
                    x1 = item.x1;
                    x2 = item.x2;
                    y1 = item.y1;
                    y2 = item.y2;
                }
                return <line
                    x1={x1}
                    x2={x2}
                    y1={y1}
                    y2={y2}

                    stroke={item.stroke}
                    strokeWidth={3}
                    key={x1 + "" + x2}
                />
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
