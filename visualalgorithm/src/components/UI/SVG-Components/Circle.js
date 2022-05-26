import React, {useState} from "react";
import Draggable from "react-draggable";

const Circle = ({cx, cy, fill, value, textFill, id, handleDrag, draggable, onRightClick, onLeftClick}) => {
    const [info, setInfo] = useState({dx: 0, dy: 0, cx: cx, cy: cy})

    const handleMouseDown = (event) => {
        if (onRightClick && event.type === 'contextmenu') {
            event.preventDefault()
            onRightClick({...event, value});
        } else if (onLeftClick) {
            onLeftClick({...event, value});
        }
    }

    const group = ( <g onClick={handleMouseDown} onContextMenu={handleMouseDown}>
        <circle
            r={20}
            cx={cx}
            cy={cy}
            key={cx}
            fill={fill}
            id={cx + "" + cy + value}
        />
        <text
            x={cx}
            y={cy}
            fill={textFill}
            textAnchor="middle"
            alignmentBaseline="central"
            key={value}
            id={cx + "" + cy + value}
        >{value}</text>
    </g>);

    if (draggable === false) {
        return group;
    } else {
        return <Draggable
            onStart={event => setInfo({dx: event.clientX - info.cx, dy: event.clientY - info.cy})}
            onDrag={event => handleDrag({
                ...event,
                newX: event.clientX - info.dx,
                newY: event.clientY - info.dy,
                id: id
            })}
            onStop={event => setInfo({dx: 0, dy: 0, cx: event.clientX - info.dx, cy: event.clientY - info.dy})}
        >
            {group}
        </Draggable>
    }
}

export default Circle;