import React, {Fragment} from "react";
import ReactDOM from "react-dom";
import classes from "./Modal.module.css";

const Modal = ({children, open, onClose}) => {
    if (!open) return null;
    return ReactDOM.createPortal(
        <Fragment>
            <div className={classes.overlay} onClick={onClose}/>
            <div className={classes.modal}>
                {children}
                <button className={classes.button} onClick={onClose}>{"Close"}</button>
            </div>
        </Fragment>,
        document.getElementById("portal")
    )
}

export default Modal;