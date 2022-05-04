import classes from "./InputWithSubmit.module.css";
import React from "react";

const InputWithSubmit = (props) => {
    return (
        <React.Fragment>
            <input type={props.type} className={classes.inputBox} onChange={props.onChange}/>
            <button type="submit" className={classes.button}>{props.btnLabel}</button>
        </React.Fragment>
    );
}

export default InputWithSubmit;