import classes from "./CardMenue.module.css";
import colors from "./Colors.module.css";
import Card from "./Card.jsx";
import CardBack from "./CardBack.jsx";
import Button from "./Button.jsx";

const CardMenue = ({name, dropdownElements, id}) => {
    const colorClasses = [colors.c0, colors.c1, colors.c2, colors.c3, colors.c4, colors.c5, colors.c6, colors.c7];
    const colorClass = colorClasses[id % colorClasses.length];

    const cardFront =
        <div className={`${colorClass} ${classes.cardFront}`}>
            <span
                className={classes.cardTitle}
                title={name}
            >{name}</span>
            <Button
                className={classes.viewMore}
                content={"View more"}
                onClick={() => {}}
            />
        </div>

    const cardBack =
        <div className={`${colorClass} ${classes.cardBack}`}>
            <CardBack elementList={dropdownElements}/>
        </div>
    return <li className={classes.listElement}>
        <Card cardFront={cardFront} cardBack={cardBack}/>
    </li>
}

export default CardMenue;