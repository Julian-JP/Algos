import React from "react";
import classes from "./MultidataInputWithSubmit.module.css";

const MultidataInputWithSubmit = props => {

    let input = [];

    input.push(
        <React.Fragment>
            <label htmlFor={props.data[0].label}
                   className={`${classes.label} ${classes.labelTop}`}>{props.data[0].label}</label>
            <input id={props.data[0].label} key={props.data[0].label} type={props.data[0].type}
                   className={`${classes.inputBox} ${props.data[0].label !== null ? classes.inputBoxRightTop : classes.inputBoxFullTop}`}
                   onChange={props.data[0].onChange}/>
        </React.Fragment>)

    for (let i = 1; i < props.data.length; i++) {
        input.push(
            <React.Fragment>
                <label htmlFor={props.data[i].label} className={classes.label}>{props.data[i].label}</label>
                <input id={props.data[i].label} key={props.data[i].label} type={props.data[i].type}
                       className={props.data[0].label !== null ? `${classes.inputBox} ${classes.inputBoxRight}` : classes.inputBox}
                       onChange={props.data[i].onChange}/>
            </React.Fragment>
        );
    }


    return (
        <form onSubmit={props.onSubmit} className={classes.form}>
            {input}
            <button type="submit" className={classes.button}>{props.btnLabel}</button>
        </form>
    )
}

export default MultidataInputWithSubmit;