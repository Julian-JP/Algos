import React, {useEffect, useState} from "react";
import useFetch from "../../hooks/useFetch.js";
import classes from "./AlgorithmInformation.module.css";
import Button from "../UI/Cardmenue/Button.jsx";

const AlgorithmInformation = props => {
    const [algorithmInformation, setAlgorithmInformation] = useState([]);
    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        fetchAlogrithmInformation();
    }, []);

    const fetchAlogrithmInformation = () => {
        const applyResponse = (response) => {
            setAlgorithmInformation({name: response.name, url: response.url, type: response.type});
        }

        sendRequest({
            url: `https://julian-laux.de:8080/algos/all${props.url}`,
            method: 'GET'
        }, applyResponse);
    }

    const handleDisplayedContentChange = () => {
        props.changeDisplayedContent({displayed: "graph", graphType: algorithmInformation.type, url: algorithmInformation.url});
    }

    return isLoading ? <div className={classes.info}>{"Loading..."}</div> :
        error ? <div className={classes.info}>{error}</div> :
            <div>
                <Button
                    onClick={handleDisplayedContentChange}
                    className={classes.info}
                    content={algorithmInformation.name} />
            </div>;
}

export default AlgorithmInformation;