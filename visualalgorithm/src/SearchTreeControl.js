import React, { useState } from 'react';
import "./SearchTreeControl.css"

const SearchTreeControl = ({drawCircle, drawLine, clear, canvas}) => {

    const [tree, setTree] = useState(null);
    const [addval, setAddval] = useState('');
    const [removeval, setRemoveval] = useState('');

    const onAdd = () => {
        if (addval === '' ) return;

        if (tree == null) {
            fetch('http://localhost:8080/algos/bst/new/' + addval, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(res => {return res.json()}).then(data => {
                setTree(data.root);
                printTree(data.root, 5, 'blue', 'black');
            });
        } else {
            fetch('http://localhost:8080/algos/bst/insert/' + addval, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({root: tree}),
            }).then(res => {return res.json()}).then(data => {
                setTree(data.root);
                printTree(data.root, 5, 'blue', 'black');
            });
        }
    }

    const onRemove = () => {
        if (removeval === '' || tree == null) return;

        fetch('http://localhost:8080/algos/bst/remove/' + removeval, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({root: tree}),
        }).then(res => {return res.json()}).then(data => {
            setTree(data.root);
            printTree(data.root, 5, 'blue', 'black');
        });
    }

    const printTree = (tree, depth, color, lcolor) => {
        clear();
        if (tree != null) {
            printSubTree(tree, 0, 0, canvas.current.width, canvas.current.height, depth, color, lcolor);
        }
    }

    const printSubTree = ({value, left, right}, x, y, width, height, depth, color, lcolor) => {

        if (left) {
            drawLine(x + (width/2), y + (height/8), x + width/4, y + height/4, lcolor);
            printSubTree(left, x+0, y+(height/8), (width/2), height, depth-1, color);
        }
        if (right) {
            drawLine(x + (width/2), y + (height/8), x + width/2 + width/4, y + height/4, lcolor);
            printSubTree(right, x+(width/2), y+(height/8), width/2, height, color);
        }

        drawCircle(x + (width/2), y + (height/8), 20, color);
    }

    return (
        <div className={"control"}>
            <div className={"add"}>
                <input type="number" className={"addBox"} onChange={(val) => setAddval(val.target.value)}/>
                <br/>
                <button onClick={onAdd} className={"addButton"}>Add</button>
            </div>
            <div className={"break"}></div>
            <div className={"remove"}>
                <input type={"number"} className={"removeBox"} onChange={(val) => setRemoveval(val.target.value)}/>
                <br/>
                <button onClick={onRemove} className={"removeButton"}>Remove</button>
            </div>
        </div>
    );
};

export default SearchTreeControl;
