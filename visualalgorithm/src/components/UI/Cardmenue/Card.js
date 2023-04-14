import React, {useState} from "react";
import classes from "./Card.module.css";
import {CSSTransition} from "react-transition-group";
import CardBack from "./CardBack";

const Card = ({frontName, backItemList}) => {
    const [showFront, setShowFront] = useState(true);

    const flipCard = () => {
        setShowFront((value) => !value);
    }


    return <div className={classes.flippableCardContainer}>
        <CSSTransition
            in={showFront}
            timeout={300}
            classNames={{
                enter: classes.flipEnter,
                enterActive: classes.flipEnterActive,
                enterDone: classes.flipEnterDone,
                exit: classes.flipExit,
                exitActive: classes.flipExitActive,
                exitDone: classes.flipExitDone
            }}
        >
            < div className={classes.card} onClick={flipCard}>
                <div className={classes.cardfront}>
                    <div className={classes.img}/>
                    <div className={classes.textField}>
                        <div className={classes.textContent}>
                            { frontName }
                        </div>
                    </div>
                </div>

                <div className={classes.cardback}>
                    <CardBack elementList={backItemList}/>
                </div>
            </div>
        </CSSTransition>
    </div>
}

export default Card;