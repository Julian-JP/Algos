import useFetch from "../../../hooks/useFetch";
import React, {useEffect, useState} from "react";

const AlgorithmList = () => {
    const [algorithmList, setAlgorithmList] = useState([]);
    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        fetchAlogorithmList();
    }, []);

    const fetchAlogorithmList = () => {
        const applyResponse = (response) => {
            setAlgorithmList(response);
        }

        sendRequest({
            url: 'http://localhost:8080/algos/SearchTreeAlogrithms',
            method: 'GET'
        }, applyResponse);
    }

    const convertedAlgorithmList = algorithmList.map(elem => <li key={elem.id}>{elem.name}</li>);

    return <React.Fragment>
        {!isLoading && !error && <ul>{convertedAlgorithmList}</ul>}
        {isLoading && <div>Loading...</div>}
        {!isLoading && <div>{error}</div>}
    </React.Fragment>
}

export default AlgorithmList;