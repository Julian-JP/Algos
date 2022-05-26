import React, {useEffect, useState} from 'react';
import classes from "./GraphVisualisation.module.css"
import useFetch from "../../hooks/useFetch";
import ControlSelector from "../control/ControlSelector";
import Circle from "../UI/SVG-Components/Circle";

const GraphVisualisation = props => {

    const [explanation, setExplanation] = useState(null);
    const [displayed, setDisplayed] = useState([]);

    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        const applyResponse = (response) => {
            setExplanation(response.explanation);
        };
        sendRequest({
            url: `http://localhost:8080/algos/${props.url}/explanation`, method: 'GET'
        }, applyResponse);

    }, [sendRequest]);

    const dragging = (event) => {
        setDisplayed(old => {
            let ret = [old.length];
            let index = old.findIndex((elem) => elem.x + "" + elem.y + elem.value === event.id);
            for (let i = 0; i < old.length; i++) {
                if (old[i].from === index) {
                    old[i].x1 = event.newX;
                    old[i].y1 = event.newY;
                }
                if (old[i].to === index) {
                    old[i].x2 = event.newX;
                    old[i].y2 = event.newY;
                }
                ret[i] = old[i];
            }
            return ret;
        })
    }

    const explanationDiv = isLoading ? <div className={classes.explanation}>Loading...</div> : error ?
        <div className={classes.explanation}>Fehler beim Laden</div> :
        <div className={classes.explanation} dangerouslySetInnerHTML={{__html: explanation}}></div>

    const convertedSVG = displayed.map(item => {
        switch (item.type) {
            case "circle": {
                return <Circle
                    handleDrag={dragging}
                    r={20}
                    cx={item.x}
                    cy={item.y}
                    key={item.x + "" + item.y + item.textFill}
                    id={item.x + "" + item.y + item.value}
                    fill={item.fill}
                    textFill={item.textFill}
                    stroke={item.stroke}
                    value={item.value}
                    draggable={item.draggable}
                    onClick={item.onClick}
                />
            }
            case "line": {
                return <line
                    x1={item.x1}
                    x2={item.x2}
                    y1={item.y1}
                    y2={item.y2}
                    stroke={item.stroke}
                    strokeWidth={3}
                    key={item.x1 + "" + item.x2}/>
            }
            default:
                return null;
        }
    })

    return (<div className={classes.background}>
        <div className={classes.card}>
            <svg className={classes.canvas} width={"100%"} height={"100%"}>
                {convertedSVG}
            </svg>
            <ControlSelector
                type={props.displayedType}
                setDisplayed={setDisplayed}
                url={props.url}
            />
        </div>
        {explanationDiv}
    </div>);
};

export default GraphVisualisation;
