import React from "react";
import classes from "./MultidataInputWithSubmit.module.css";

const MultidataInputWithSubmit = props => {

    const input = props.data.map((data, index) => {
        if (index === 0 && data.noLabel) {
            return (
                <input type={data.type}
                       key={data.label}
                       className={`${classes.inputBox} ${classes.inputBoxTopNoLabel} ${classes.inputBoxNoLabel}`}
                       onChange={data.onChange}/>
            )
        } else if (index === 0) {
            return (
                <React.Fragment key={data.label}>
                    <label htmlFor={data.label} className={`${classes.label} ${classes.labelTop}`}>{data.label}</label>
                    <input id={data.label} type={data.type}
                           className={`${classes.inputBox} ${classes.inputBoxTopWithLabel}`}
                           onChange={data.onChange}/>
                </React.Fragment>
            )
        } else if (data.noLabel) {
            return <input type={data.type}
                          key={data.label}
                          className={`${classes.inputBox} ${classes.inputBoxNoLabel}`}
                          onChange={data.onChange}/>
        } else {
            return (
                <React.Fragment key={data.label}>
                    <label htmlFor={data.label} className={classes.label}>{data.label}</label>
                    <input id={data.label} type={data.type}
                           className={classes.inputBox}
                           onChange={data.onChange}/>
                </React.Fragment>
            )
        }
    })


    return (
        <React.Fragment>
            <form onSubmit={props.onSubmit} className={`${classes.form} ${props.className}`}>
                {input}
                <button type="submit" className={classes.button}>{props.btnLabel}</button>
            </form>
        </React.Fragment>
    )
}

export default MultidataInputWithSubmit;