import React, { useState, useRef } from "react";
import classes from "./Card.module.css";
import { CSSTransition } from "react-transition-group";

const Card = ({ cardFront, cardBack }) => {
    const [showFront, setShowFront] = useState(true);
    const nodeRef = useRef(null);

    const flipCard = () => {
        setShowFront((value) => !value);
    };

    return (
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
            nodeRef={nodeRef}
        >
            <div className={classes.card} onClick={flipCard} ref={nodeRef}>
                <div className={classes.cardfront}>
                    {cardFront}
                </div>
                <div className={classes.cardback}>
                    {cardBack}
                </div>
            </div>
        </CSSTransition>
    );
};

export default Card;
