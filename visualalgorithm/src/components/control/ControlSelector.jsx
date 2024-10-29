import SearchTreeControl from "./SearchTreeControl.jsx";
import GraphControl from "./GraphControl.jsx";
import React from "react";
import classes from "./ControlSelector.module.css";
import HeapControl from "./HeapControl.jsx";

const ControlSelector = props => {
    return (
        <div className={classes.control}>
            {props['type'] === "binarytree" && <SearchTreeControl
                graph={props.graph}
                graphDispatch={props.graphDispatch}
                type={props.url}
                directed={false}
                weightedEdges={false}
                svgWidth={props.svgWidth}
                svgHeight={props.svgHeight}
            />
            }
            {props['type'] === "binaryheap" && <HeapControl
                graph={props.graph}
                graphDispatch={props.graphDispatch}
                type={props.url}
                directed={true}
                weightedEdges={false}
                svgWidth={props.svgWidth}
                svgHeight={props.svgHeight}
            />
            }
            {props['type'] === "pathfindingGraph" && <GraphControl
                graph={props.graph}
                graphDispatch={props.graphDispatch}
                type={props.url}
                weightedEdges={false}
                pathfindingGraph={true}
                startButton={true}
                endButton={true}
                directed={true}
                svgWidth={props.svgWidth}
                svgHeight={props.svgHeight}
            />
            }
            {props['type'] === "weightedNonNegativePathFindingGraph" && <GraphControl
                graph={props.graph}
                graphDispatch={props.graphDispatch}
                type={props.url}
                weightedEdges={true}
                minWeight={0}
                pathfindingGraph={true}
                startButton={true}
                endButton={true}
                directed={true}
                svgWidth={props.svgWidth}
                svgHeight={props.svgHeight}
            />
            }
            {props['type'] === "weightedPathFindingGraph" && <GraphControl
                graph={props.graph}
                graphDispatch={props.graphDispatch}
                type={props.url}
                weightedEdges={true}
                minWeight={undefined}
                pathfindingGraph={true}
                startButton={true}
                endButton={true}
                directed={true}
                svgWidth={props.svgWidth}
                svgHeight={props.svgHeight}
            />
            }
            {props['type'] === "minimalSpanningTree" && <GraphControl
                graph={props.graph}
                graphDispatch={props.graphDispatch}
                type={props.url}
                weightedEdges={true}
                minWeight={undefined}
                startButton={true}
                endButton={false}
                directed={false}
                svgWidth={props.svgWidth}
                svgHeight={props.svgHeight}
            />
            }
            {props['type'] === "weightedAllPathFindingGraph" && <GraphControl
                graph={props.graph}
                graphDispatch={props.graphDispatch}
                type={props.url}
                weightedEdges={true}
                minWeight={undefined}
                startButton={true}
                endButton={false}
                directed={true}
                verteciesRenaming={true}
                svgWidth={props.svgWidth}
                svgHeight={props.svgHeight}
            />
            }
            {props['type'] === "weightedNonNegativeAllPathFindingGraph" && <GraphControl
                graph={props.graph}
                graphDispatch={props.graphDispatch}
                type={props.url}
                weightedEdges={true}
                minWeight={undefined}
                startButton={true}
                endButton={false}
                directed={true}
                verteciesRenaming={true}
                svgWidth={props.svgWidth}
                svgHeight={props.svgHeight}
            />
            }
        </div>
    )
}

export default ControlSelector;