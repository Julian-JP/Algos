import React, {useState} from "react";
import classes from "./Category.module.css";

const CardMenue = (props) => {
    const [expand, setExpand] = useState(false);

    const handleOnClick = () => {
        setExpand(old => !old);
    }

    const singleDropDownElement = elem => {
        return <li>
            elem
        </li>
    }

    const fetchAlogrithmInformation = () => {
        const applyResponse = (response) => {
            setSubCategories({name: response.name, url: response.url, type: response.type});
        }

        sendRequest({
            url: `https://julian-laux.de:8080/algos/all${props.url}`,
            method: 'GET'
        }, applyResponse);
    }

    const getCatgeoryDropDown = () => {
        return <ul>
            {props.dropdownElements.map(elem => singleDropDownElement(elem))}
        </ul>
    }

    return <li className={classes.listElement}>
        <button title={props.categoryName} onClick={handleOnClick} />
        {expand && getCatgeoryDropDown()}
    </li>
}

export default CardMenue;