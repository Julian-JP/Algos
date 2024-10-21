import React from "react";
import classes from "./NextPageButton.module.css";
const NextPageButton = ( {clickable, onClick} ) => {

    return <button className={`${classes.nextButton} ${clickable ? classes.active : ""}`} onClick={onClick}>{""}</button>
}

export default NextPageButton;