import React, {useState} from "react";
import classes from "./MultidataInputWithSubmit.module.css";

const MultidataInputWithSubmit = props => {

    const [inputValue, setInputValue] = useState(props.data.map(data => data.defaultValue));

    const changeInputData = (event, data, index) => {
        let newValueInInputField = data.min;
        if (data.onChange == undefined || data.min == undefined || event.target.value >= data.min) {
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

    const input = props.data.map((data, index) => {
        if (data.isArray && index === 0) {
            const ret = data.map((dataElement, index) => {
                if (index === 0) {
                    return (
                        <React.Fragment key={dataElement.label}>
                            <label htmlFor={dataElement.label}
                                   className={`${classes.label} ${classes.labelTop}`}>{dataElement.label}</label>
                            <input id={dataElement.label}
                                   type={dataElement.type}
                                   min={dataElement.min}
                                   className={`${classes.inputBox} ${classes.inputBoxTopWithLabel}`}
                                   onChange={event => {
                                       changeInputData(event, dataElement, index)
                                   }}
                                   value={inputValue[index]}/>
                        </React.Fragment>
                    )
                }
            });


        } else if (data.isArray) {

        }
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
        <React.Fragment>
            <form onSubmit={props.onSubmit} className={`${classes.form} ${props.className}`}>
                {input}
                <button type="submit" className={classes.button}
                        disabled={props.onSubmit == null}>{props.btnLabel}</button>
            </form>
        </React.Fragment>
    )
}

export default MultidataInputWithSubmit;