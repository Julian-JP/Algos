import React, {useState} from "react";
import Draggable from "react-draggable";

const Circle = ({cx, cy, opacity, fill, value, textFill, id, handleDrag, draggable, onRightClick, onLeftClick}) => {
    const [isDragging, setIsDragging] = useState(false);
    const baseX = cx;
    const baseY = cy;

    const handleMouseDown = (event) => {
        if (onLeftClick) {
            event.preventDefault()
            onLeftClick({...event, id});
        }
    }

    const handleRightClick = (event) => {
        if (onRightClick) {
            event.preventDefault();
            onRightClick({...event, id});
        }
    }

    const handleOnStop = (event, data) => {
        if (! isDragging) {
            handleMouseDown(event)
            return;
        }

        const newX = baseX + data.x;
        const newY = baseY + data.y;

        setIsDragging(false)
        handleDrag(id, newX, newY);
    }

    const handleOnStart = (event, data) => {
    }

    const handleOnDrag = (event, data) => {
        const newX = baseX + data.x;
        const newY = baseY + data.y;
        setIsDragging(true)
        handleDrag(id, newX, newY);
    }

    const group = (<g>
        <circle
            r={20}
            cx={cx}
            cy={cy}
            key={cx}
            fill={fill}
            onContextMenu={handleRightClick}
            opacity={opacity}
        />
        <text
            x={cx}
            y={cy}
            fill={textFill}
            textAnchor="middle"
            alignmentBaseline="central"
            key={cx + cy}
            onContextMenu={handleRightClick}
        >{value}</text>
    </g>);

    if (draggable === false) {
        return group;
    } else {
        return <Draggable
            onStart={handleOnStart}
            onDrag={handleOnDrag}
            onStop={handleOnStop}
        >
            {group}
        </Draggable>
    }
}

export default Circle;