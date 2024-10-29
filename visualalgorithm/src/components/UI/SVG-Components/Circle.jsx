import React, {useRef, useState} from "react";
import Draggable from "react-draggable";

const Circle = ({cx, cy, opacity, fill, value, textFill, id, handleDragStart, handleDrag, handleDragStop, draggable, onRightClick, onLeftClick}) => {
    const isDragging = useRef(false);
    const nodeRef = React.useRef(null);

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
        if (! isDragging.current) {
            handleMouseDown(event)
            return
        }

        const newX = data.x;
        const newY = data.y;

        isDragging.current = false;
        handleDragStop(id, newX, newY);
    }

    const handleOnStart = (event, data) => {
        handleDragStart()
    }

    const handleOnDrag = (event, data) => {
        const newX = data.x;
        const newY = data.y;
        isDragging.current = true;
        handleDrag(id, newX, newY);
    }

    const group = (<g ref={nodeRef}>
        <circle
            r={20}
            cx={0}
            cy={0}
            key={cx}
            fill={fill}
            onContextMenu={handleRightClick}
            opacity={opacity}
        />
        <text
            x={0}
            y={0}
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
            position={{ x: cx, y: cy}}
            onStart={handleOnStart}
            onDrag={handleOnDrag}
            onStop={handleOnStop}
            nodeRef={nodeRef}
        >
            {group}
        </Draggable>
    }
}

export default Circle;