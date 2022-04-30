import SearchTreeControl from "./SearchTreeControl";
import GraphControl from "./GraphControl";
import React from "react";
import classes from "./ControlSelector.module.css";
import HeapControl from "./HeapControl";

const ControlSelector = props => {
    return (
        <div className={classes.control}>
            {props['type'] === "binarytree" && <SearchTreeControl
                canvas={props.canvas}
                type={props.url}/>
            }
            {props['type'] === "binaryheap" && <HeapControl
                canvas={props.canvas}
                type={props.url}/>
            }
            {props['type'] === "graph" && <GraphControl
                canvas={props.canvas}
                type={props.url}/>
            }
        </div>
    )
}

export default ControlSelector;