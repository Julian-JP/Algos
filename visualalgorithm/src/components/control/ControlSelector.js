import SearchTreeControl from "./SearchTreeControl";
import GraphControl from "./GraphControl";
import React from "react";
import classes from "./ControlSelector.module.css";
import HeapControl from "./HeapControl";

const ControlSelector = props => {
    return (
        <div className={classes.control}>
            {props['type'] === "binarytree" && <SearchTreeControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}/>
            }
            {props['type'] === "binaryheap" && <HeapControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}/>
            }
            {props['type'] === "graph" && <GraphControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                weightedEdges={false}/>
            }
            {props['type'] === "weightedNonNegativeGraph" && <GraphControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                weightedEdges={true}
                minWeight={0}/>
            }
            {props['type'] === "weightedGraph" && <GraphControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                weightedEdges={true}
                minWeight={undefined}/>
            }
        </div>
    )
}

export default ControlSelector;