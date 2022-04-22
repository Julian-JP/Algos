import React, {useState} from "react";
import classes from "./DropDownMenue.module.css";

const DropDownMenue = (props) => {

    const [displayDropdown, setDisplayDropdown] = useState(false);

    const onClick = () => {
        setDisplayDropdown(prev => !prev);
    }

    return <li className={classes.listElement}>
        <div>
            <button className={classes.header} onClick={onClick}>
                {props.children}
            </button>
            {displayDropdown &&
                <div className={classes.elements}>
                    {props.dropdownElements.map(elem => (elem))}
                </div>
            }
        </div>
    </li>
}

export default DropDownMenue;