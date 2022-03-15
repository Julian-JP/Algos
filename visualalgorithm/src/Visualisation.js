import React, {useEffect, useRef, useState} from 'react';
import "./Visualisation.css"
import SearchTreeControl from "./SearchTreeControl";

const Visualisation = () => {

    const canvasRef = useRef(null);
    const contextRef = useRef(null);

    const [expl, setExpl] = useState('Loading...');


    useEffect(() => {
        const canvas = canvasRef.current;
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight/2;

        const context = canvas.getContext("2d");
        contextRef.current = context;

        loadExplanation("bst");
    })

    const loadExplanation = (type) => {
        fetch('http://localhost:8080/algos/'+ type +'/explanation', {
            method: 'GET'
        }).then((res) => {
            res.text().then(res => setExpl(res));
        })
    }

    const drawCircle = (x, y, rad, color, txt) => {
        const context = contextRef.current;
        context.beginPath();
        context.fillStyle = color;
        context.arc(x, y, rad, 0, Math.PI*2);
        context.fill();

        context.fillStyle = '#ffffff';
        context.font = 'bold ' + (rad/(txt.toString().length)*1.7) + 'px Calibri';
        context.textAlign="center";
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

    return (
        <div className={"visual"}>
            <div className={"visualisation"}>
                <div className={"control"}>
                    <SearchTreeControl drawCircle={drawCircle} drawLine={drawLine} clear={clear} canvas={canvasRef} type={'bst'}/>
                </div>
                <canvas
                ref={canvasRef}
                className={"canvas"}
                />
            </div>

            <div className={"break"}></div>
            <div className={"explanation"} dangerouslySetInnerHTML={{ __html: expl}}></div>
        </div>
    );
};

export default Visualisation;
