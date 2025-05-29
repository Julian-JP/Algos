const LINE_COLOR = 'black';

const drawTree = (tree, edges, vertices, svgWidth, svgHeight) => {
    if (tree === null || tree === undefined) {
        return;
    }
    drawSubtree(tree.root, edges, vertices, 0, getDepth(tree.root), 0, (svgWidth - 20), svgHeight);
}

const getDepth = (root) => {
    if (root === null || root === undefined) {
        return 0;
    }
    let maxDepth = 0;
    root.children.forEach((child) => {
        maxDepth = Math.max(maxDepth, getDepth(child));
    })
    return maxDepth + 1;
}

const calcVertexX = (startX, endX) => {
    return startX + ((endX - startX) / 2) - 10;
}

const calcVertexY = (depth, maxDepth, svgHeight) => {
    return (((svgHeight - 20) / (maxDepth + 1)) * (depth + 1))  + 10;
}

const getVertexId = (x, y) => {
    return "vertex: " + x + "," + y;
}

const calcNewX = (startX, endX, numChildren, index) => {
    const perVertexWindowSize = (endX - startX) / numChildren;
    const newVertexStartX = startX + perVertexWindowSize * index;
    const newVertexEndX = newVertexStartX + perVertexWindowSize;

    return [newVertexStartX, newVertexEndX];
}

const generateEdgeId = (fromId, toId) => {
    return "edge: " + fromId + ": " + toId
}

const getAntiColor = (fillColor) => {
    if (fillColor === 'black') {
        return 'white';
    } else {
        return 'black';
    }
}

const drawSubtree = (root, edges, vertices, depth, maxDepth, startX, endX, svgHeight) => {
    if (root === undefined || root === null) {
        return;
    }

    const y = calcVertexY(depth, maxDepth, svgHeight);
    const x = calcVertexX(startX, endX);
    const curVertexId = getVertexId(x, y);
    const numChildren = root.children.length;

    root.children.forEach((child, index) => {
        if (child === null) {
            return;
        }

        const [newStartX, newEndX] = calcNewX(startX, endX, numChildren, index);
        const newX = calcVertexX(newStartX, newEndX);
        const newY = calcVertexY(depth + 1, maxDepth, svgHeight);
        const newVertexId = getVertexId(newX, newY);

        edges.push({
            type: "line",
            from: curVertexId,
            to: newVertexId,
            id: generateEdgeId(curVertexId, newVertexId),
            stroke: LINE_COLOR,
            directed: false
        });

        drawSubtree(child, edges, vertices, depth + 1, maxDepth, newStartX, newEndX, svgHeight);
    })

    const antiColor = getAntiColor(root.color);

    vertices.push({
        type: "circle",
        x: x,
        y: y,
        fill: root.color,
        stroke: antiColor,
        textFill: antiColor,
        value: root.value,
        id: curVertexId,
        draggable: false
    });
}

export default drawTree;