import React from "react";
import classes from "./CardMenue.module.css";
import Card from "./Card";

const CardMenue = (props) => {

    return <li className={classes.listElement}>
        <div>
            <Card frontName={props.children} backItemList={props.dropdownElements}/>
        </div>
    </li>
}

export default CardMenue;