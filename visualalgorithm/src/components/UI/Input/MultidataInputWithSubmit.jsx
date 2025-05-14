import React, {useState} from "react";
import classes from "./MultidataInputWithSubmit.module.css";

const MultidataInputWithSubmit = props => {

    const [inputValue, setInputValue] = useState(props.data.map(data => data.defaultValue === undefined ? "" : data.defaultValue));

    const changeInputData = (event, data, index) => {
        let newValueInInputField = data.min;
        if (data.onChange === undefined || data.min === undefined || event.target.value >= data.min) {
            newValueInInputField = event.target.value;
        }
        event.target.value = newValueInInputField;
        data.onChange(event);

        setInputValue(old => {
            let newInputValues = [...old];
            newInputValues[index] = newValueInInputField;
            return newInputValues;
        })
    }

    const validInput = () => {
        if (inputValue == null) return false;

        return inputValue.map(val => {
            return val != null && val !== "" && val !== undefined;
        }).reduce((val1, val2) => {
            return val1 && val2;
        });
    }

    const input = props.data.map((data, index) => {
        if (index === 0 && data.noLabel) {
            return (
                <input type={data.type}
                       min={data.min}
                       key={data.label}
                       className={`${classes.inputBox} ${classes.inputBoxTopNoLabel} ${classes.inputBoxNoLabel}`}
                       onChange={event => {
                           changeInputData(event, data, index)
                       }}
                       value={inputValue[index]}/>
            )
        } else if (index === 0) {
            return (
                <React.Fragment key={data.label}>
                    <label htmlFor={data.label} className={`${classes.label} ${classes.labelTop}`}>{data.label}</label>
                    <input id={data.label}
                           type={data.type}
                           min={data.min}
                           className={`${classes.inputBox} ${classes.inputBoxTopWithLabel}`}
                           onChange={event => {
                               changeInputData(event, data, index)
                           }}
                           value={inputValue[index]}/>
                </React.Fragment>
            )
        } else if (data.noLabel) {
            return <input type={data.type}
                          min={data.min}
                          key={data.label}
                          className={`${classes.inputBox} ${classes.inputBoxNoLabel}`}
                          onChange={event => {
                              changeInputData(event, data, index)
                          }}
                          value={[inputValue[index]]}/>
        } else {
            return (
                <React.Fragment key={data.label}>
                    <label htmlFor={data.label} className={classes.label}>{data.label}</label>
                    <input id={data.label}
                           type={data.type}
                           min={data.min}
                           className={classes.inputBox}
                           onChange={event => {
                               changeInputData(event, data, index)
                           }}
                           value={inputValue[index]}/>
                </React.Fragment>
            )
        }
    })

    return (
        <div className={`${classes.formDiv} ${validInput() ? classes.formValid : classes.formInvalid}`}>
            <form onSubmit={props.onSubmit} className={classes.form}>
                {input}
                <button type="submit" className={classes.button}
                        disabled={props.onSubmit == null || !validInput()}>{props.btnLabel}</button>
            </form>
        </div>
    )
}

export default MultidataInputWithSubmit;