import React, { useEffect, useRef, useState} from 'react';
import classes from "./GraphVisualisation.module.css"
import SearchTreeControl from "../control/SearchTreeControl";
import useFetch from "../../hooks/useFetch";
import useCanvas from "../../hooks/useCanvas";

const GraphVisualisation = () => {

    const [explanation, setExplanation] = useState(null);
    const {isLoading, error, sendRequest} = useFetch();


    const canvasRef = useRef(null);
    const contextRef = useRef(null);

    const canvas = useCanvas(contextRef, canvasRef);

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

    const explanationDiv =
        isLoading ? <div className={classes.explanation} >Loading...</div> :
            error ? <div className={classes.explanation} >Fehler beim Laden</div> :
                <div className={classes.explanation} dangerouslySetInnerHTML={{__html: explanation}}></div>

    return (
        <div className={classes.background}>
            <div className={classes.card}>
                <canvas
                    ref={canvasRef}
                    className={classes.canvas}
                />
                    <SearchTreeControl
                        canvas={canvas}
                        type={'bst'}/>

            </div>
            {explanationDiv}
        </div>
    );
};

export default GraphVisualisation;
