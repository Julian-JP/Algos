const useCanvas = (contextRef, canvasRef) => {

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

    return {
        drawCircle,
        drawLine,

        clear,
        width: canvasRef.current === null ? 1 : canvasRef.current.width,
        height: canvasRef.current === null ? 1 : canvasRef.current.height
    }
}

export default useCanvas;