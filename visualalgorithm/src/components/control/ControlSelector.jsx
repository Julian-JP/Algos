import SearchTreeControl from "./SearchTreeControl.jsx";
import GraphControl from "./GraphControl.jsx";
import React from "react";
import classes from "./ControlSelector.module.css";
import HeapControl from "./HeapControl.jsx";

const ControlSelector = props => {
    return (
        <div className={classes.control}>
            {props['type'] === "binarytree" && <SearchTreeControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                directed={false}
                weightedEdges={false}
            />
            }
            {props['type'] === "binaryheap" && <HeapControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                directed={true}
                weightedEdges={false}
            />
            }
            {props['type'] === "pathfindingGraph" && <GraphControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                weightedEdges={false}
                pathfindingGraph={true}
                startButton={true}
                endButton={true}
                directed={true}
            />
            }
            {props['type'] === "weightedNonNegativePathFindingGraph" && <GraphControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                weightedEdges={true}
                minWeight={0}
                pathfindingGraph={true}
                startButton={true}
                endButton={true}
                directed={true}
            />
            }
            {props['type'] === "weightedPathFindingGraph" && <GraphControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                weightedEdges={true}
                minWeight={undefined}
                pathfindingGraph={true}
                startButton={true}
                endButton={true}
                directed={true}
            />
            }
            {props['type'] === "minimalSpanningTree" && <GraphControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                weightedEdges={true}
                minWeight={undefined}
                startButton={true}
                endButton={false}
                directed={false}
            />
            }
            {props['type'] === "weightedAllPathFindingGraph" && <GraphControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                weightedEdges={true}
                minWeight={undefined}
                startButton={true}
                endButton={false}
                directed={true}
                verteciesRenaming={true}
            />
            }
            {props['type'] === "weightedNonNegativeAllPathFindingGraph" && <GraphControl
                setEdges={props.setEdges}
                setVertices={props.setVertices}
                type={props.url}
                weightedEdges={true}
                minWeight={undefined}
                startButton={true}
                endButton={false}
                directed={true}
                verteciesRenaming={true}
            />
            }
        </div>
    )
}

export default ControlSelector;