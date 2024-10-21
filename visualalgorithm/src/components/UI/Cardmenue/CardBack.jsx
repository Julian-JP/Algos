import React, {useState} from "react";
import classes from "./CardBack.module.css";
import NextPageButton from "./NextPageButton";
import PrevPageButton from "./PrevPageButton";
import Button from "./Button.jsx";

const CardBack = ({elementList}) => {
    const [page, setPage] = useState(0);

    const onPrev = (event) => {
        event.stopPropagation();
        if (page > 0) {
            setPage(v => v + 1)
        }
    }

    const onNext = (event) => {
        event.stopPropagation();
        if (elementList.length > page * 3 + 3) {
            setPage(v => v - 1)
        }
    }

    const algorithm = (id) => {
        let index = page * 3 + id;
        let display = elementList.length > index;
        return <div> {display && elementList[index]}</div>
    }

    return <div className={classes.container}>
        {algorithm(0)}
        {algorithm(1)}
        {algorithm(2)}
        {elementList.length > 3 &&
            <div className={classes.navigation}>
                <Button className={classes.navButton} onClick={onPrev} clickable={page > 0} content={"Previous"}/>
                <Button className={classes.navButton} onClick={onNext} clickable={page > 0} content={"Next"}/>
            </div>
        }
    </div>
}

export default CardBack;