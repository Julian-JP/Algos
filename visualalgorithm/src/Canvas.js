import React, {useEffect, useRef} from 'react';
import "./Canvas.css"

const Canvas = (props) => {

    const canvasRef = useRef(null);
    const contextRef = useRef(null);


    useEffect(() => {
        const canvas = canvasRef.current;
        canvas.width = window.innerWidth/2;
        canvas.height = 800;

        const context = canvas.getContext("2d");
        contextRef.current = context;
        props.nodes.forEach((p) => drawCircle(p.x, p.y, 10, context, 'white'));
    })

    const drawCircle = (x, y, rad, context, color) => {
        context.fillStyle = color;
        context.beginPath();
        context.arc(x, y, rad, 0, Math.PI*2);
        context.fill();
        context.stroke();
    }

    const handleClick = () => {
        const canvas = canvasRef.current;
        const context = canvas.getContext("2d");
        drawCircle(20, 100, 10, context, 'red');
    }

    return (
        <div className={"visual"}>
            <canvas
            ref={canvasRef}
            className={"canvas"}
            />
            <button onClick={handleClick}>Clear</button>
            <h1>Toll</h1>
        </div>
    );
};

export default Canvas;
