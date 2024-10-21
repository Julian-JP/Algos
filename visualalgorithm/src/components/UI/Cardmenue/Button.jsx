import classes from "./Button.module.css";

const Button = ({onClick, content, className}) => {
    return <button
        className={`${className} ${classes.button}`}
        onClick={onClick}
    >
        {content}
    </button>
}

export default Button;