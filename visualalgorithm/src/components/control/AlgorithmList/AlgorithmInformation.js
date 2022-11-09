import React, {useEffect, useState} from "react";
import useFetch from "../../../hooks/useFetch";
import classes from "./AlgorithmInformation.module.css";

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

    const information = isLoading ? <div className={classes.card}>{"Loading..."}</div> :
        error ? <div className={classes.card}>{error}</div> :
            <div className={classes.card} onClick={handleDisplayedContentChange}>{algorithmInformation.name}</div>

    return information;
}

export default AlgorithmInformation;