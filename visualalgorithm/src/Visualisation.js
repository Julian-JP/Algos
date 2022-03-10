import React, {useEffect, useRef} from 'react';
import "./Visualisation.css"
import SearchTreeControl from "./SearchTreeControl";

const Visualisation = (props) => {

    const canvasRef = useRef(null);
    const contextRef = useRef(null);


    useEffect(() => {
        const canvas = canvasRef.current;
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight/2;

        const context = canvas.getContext("2d");
        contextRef.current = context;
        props.nodes.forEach((p) => drawCircle(p.x, p.y, 10, 'white'));
    })

    const drawCircle = (x, y, rad, color) => {
        const context = contextRef.current;
        context.fillStyle = color;
        context.beginPath();
        context.arc(x, y, rad, 0, Math.PI*2);
        context.fill();
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
            <canvas
            ref={canvasRef}
            className={"canvas"}
            />
            <div className={"control"}>
                <SearchTreeControl drawCircle={drawCircle} clear={clear} canvas={canvasRef}/>
            </div>
        </div>
    );
};

export default Visualisation;
