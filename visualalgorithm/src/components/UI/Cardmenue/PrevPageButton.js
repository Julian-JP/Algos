import React from "react";
import classes from "./PrevPageButton.module.css";
const PrevPageButton = ( {clickable, onClick} ) => {

    return <button className={`${classes.prevButton} ${clickable ? classes.active : ""}`} onClick={onClick}>{""}</button>
}

export default PrevPageButton;