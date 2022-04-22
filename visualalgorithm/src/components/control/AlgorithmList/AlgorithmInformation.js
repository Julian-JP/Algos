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
            setAlgorithmInformation(response.name);
        }

        sendRequest({
            url: `http://localhost:8080/algos${props.url}`,
            method: 'GET'
        }, applyResponse);
    }

    const information = isLoading ? <div className={classes.card}>{"Loading..."}</div> :
        error ? <div className={classes.card}>{error}</div> :
            <div className={classes.card}>{algorithmInformation}</div>

    return information;
}

export default AlgorithmInformation;