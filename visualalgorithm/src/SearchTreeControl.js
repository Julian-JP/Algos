import React from 'react';
import "./SearchTreeControl.css"

const SearchTreeControl = ({drawCircle, clear, width, height}) => {

    const onAdd = () => {
        drawCircle(Math.random()*Math.abs(width - 50) + 100, Math.random()*Math.abs(height -50) + 100, 50, 'blue');
    }

    const onRemove = () => {
        clear();
    }

    return (
        <div className={"control"}>
            <div className={"add"}>
                <input type="number" className={"addBox"}/>
                <br/>
                <button onClick={onAdd} className={"addButton"}>Add</button>
            </div>
            <div className={"break"}></div>
            <div className={"remove"}>
                <input type={"number"} className={"removeBox"}/>
                <br/>
                <button onClick={onRemove} className={"removeButton"}>Remove</button>
            </div>
        </div>
    );
};

export default SearchTreeControl;
