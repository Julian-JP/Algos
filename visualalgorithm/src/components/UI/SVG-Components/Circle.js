import React, {useState} from "react";
import Draggable from "react-draggable";

const Circle = ({cx, cy, opacity, fill, value, textFill, id, handleDrag, draggable, onRightClick, onLeftClick}) => {
    const [info, setInfo] = useState({dx: 0, dy: 0, cx: cx, cy: cy, dragging: false});

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
            onStart={event => {
                setInfo(old => {
                    return {...old, dx: event.clientX - info.cx, dy: event.clientY - info.cy}
                });
            }}
            onDrag={event => {
                setInfo(old => {
                    return {...old, dragging: true}
                });
                handleDrag(id, event.clientX - info.dx, event.clientY - info.dy)
            }}
            onStop={event => {
                if (!info.dragging) {
                    handleMouseDown(event)
                    return;
                }
                setInfo(old => {
                    return {dx: 0, dy: 0, cx: event.clientX - old.dx, cy: event.clientY - old.dy, dragging: false}
                });
            }}
        >
            {group}
        </Draggable>
    }
}

export default Circle;