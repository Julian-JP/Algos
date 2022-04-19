import React, { useEffect, useRef, useState} from 'react';
import classes from "./GraphVisualisation.module.css"
import SearchTreeControl from "../control/SearchTreeControl";
import Card from '../UI/Card';
import useFetch from "../../hooks/useFetch";

const GraphVisualisation = () => {

    const [explanation, setExplanation] = useState(null);
    const {isLoading, error, sendRequest} = useFetch();


    const canvasRef = useRef(null);
    const contextRef = useRef(null);

    useEffect(() => {
        const canvas = canvasRef.current;
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight / 2;

        const context = canvas.getContext("2d");
        contextRef.current = context;

        const applyResponse = (response) => {
            setExplanation(response.explanation);
        };
        sendRequest({
            url: 'http://localhost:8080/algos/bst/explanation',
            method: 'GET'
        }, applyResponse);

    }, [sendRequest]);

    const drawCircle = (x, y, rad, color, txt) => {
        const context = contextRef.current;
        context.beginPath();
        context.fillStyle = color;
        context.arc(x, y, rad, 0, Math.PI * 2);
        context.fill();

        context.fillStyle = '#ffffff';
        context.font = 'bold ' + (rad / (txt.toString().length) * 1.7) + 'px Calibri';
        context.textAlign = "center";
        context.textBaseline = "middle";
        context.fillText(txt.toString(), x, y);
        context.stroke();

    }

    const drawLine = (x1, y1, x2, y2, color) => {
        const context = contextRef.current;
        context.beginPath();
        context.moveTo(x1, y1);
        context.lineTo(x2, y2);
        context.strokeStyle = color;
        context.stroke();
    }

    const clear = () => {
        const context = contextRef.current;
        context.beginPath();
        context.clearRect(0, 0, canvasRef.current.width, canvasRef.current.height);
        context.stroke();
    }

    const explanationDiv =
        isLoading ? <div className={classes.explanation} >Loading...</div> :
            error ? <div className={classes.explanation} >Fehler beim Laden</div> :
                <div className={classes.explanation} dangerouslySetInnerHTML={{__html: explanation}}></div>

    return (
        <div className={classes.background}>
            <Card className={classes.background}>
                <div className={classes.control}>
                    <SearchTreeControl
                        drawCircle={drawCircle}
                        drawLine={drawLine}
                        clear={clear}
                        canvas={canvasRef}
                        type={'bst'}/>
                </div>
                <canvas
                    ref={canvasRef}
                    className={classes.canvas}
                />
            </Card>
            <div className={classes.break}></div>
            {explanationDiv}
        </div>
    );
};

export default GraphVisualisation;
