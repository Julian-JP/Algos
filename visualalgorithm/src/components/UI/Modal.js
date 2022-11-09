import React, {Fragment} from "react";
import ReactDOM from "react-dom";

const MODAL_STYLES = {
    position: "fixed",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    backgroundColor: "#FFF",
    padding: "50px",
    zIndex: 1000
}

const OVERLAY_STYLES = {
    position: "fixed",
    top: 0,
    left: 0,
    bottom: 0,
    right: 0,
    backgroundColor: "rgba(0,0,0, .6)",
    zIndex: 1000
}

const BUTTON_STYLE = {
    position: "fixed",
    left: "50%",
    border: "none"
}

const Modal = ({children, open, onClose}) => {
    if (!open) return null;
    return ReactDOM.createPortal(
        <Fragment>
            <div style={OVERLAY_STYLES} onClick={onClose}/>
            <div style={MODAL_STYLES}>
                {children}
                <button style={BUTTON_STYLE} onClick={onClose}>{"Close"}</button>
            </div>
        </Fragment>,
        document.getElementById("portal")
    )
}

export default Modal;