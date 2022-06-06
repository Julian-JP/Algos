import SearchTreeControl from "./SearchTreeControl";
import GraphControl from "./GraphControl";
import React from "react";
import classes from "./ControlSelector.module.css";
import HeapControl from "./HeapControl";

const ControlSelector = props => {
    return (
        <div className={classes.control}>
            {props['type'] === "binarytree" && <SearchTreeControl
                setVertices={props.setVertices}
                setEdges={props.setEdges}
                type={props.url}/>
            }
            {props['type'] === "binaryheap" && <HeapControl
                setVertices={props.setVertices}
                setEdges={props.setEdges}
                type={props.url}/>
            }
            {props['type'] === "graph" && <GraphControl
                setVertices={props.setVertices}
                setEdges={props.setEdges}
                type={props.url}/>
            }
        </div>
    )
}

export default ControlSelector;