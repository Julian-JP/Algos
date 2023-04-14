import React, {useState} from "react";
import classes from "./CardBack.module.css";
import NextPageButton from "./NextPageButton";
import PrevPageButton from "./PrevPageButton";

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

    return <div className={classes.container}>
        <div className={classes.text}>{elementList[page * 3]}</div>
        <div className={classes.text}>{elementList[page * 3 + 1]}</div>
        <div className={classes.text}>{elementList[page * 3 + 2]}</div>
        {elementList.length > 3 &&
            <div className={classes.buttons}>
                <PrevPageButton onClick={onPrev} clickable={page > 0}/>
                <NextPageButton onClick={onNext} clickable={elementList.length > page * 3 + 3}/>
            </div>
        }
    </div>
}

export default CardBack;