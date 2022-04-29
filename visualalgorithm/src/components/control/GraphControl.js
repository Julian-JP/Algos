import React from "react";
import InputWithSubmit from "../UI/InputWithSubmit";
import classes from "./GraphControl.module.css";

const GraphControl = (props) => {
    return (
        <React.Fragment>
            <form>
                <InputWithSubmit type="text" onChange={(val) => null} btnLabel="Add"/>
            </form>
            <div className={classes.break}/>
            <form>
                <InputWithSubmit type="text" onChange={(val) => null} btnLabel="Remove"/>
            </form>
        </React.Fragment>
    )
}

export default GraphControl;