import useFetch from "../../../hooks/useFetch";
import React, {useEffect, useState} from "react";
import CardMenue from "../../UI/Cardmenue/CardMenue";
import classes from "./AlgorithmList.module.css";
import AlgorithmInformation from "./AlgorithmInformation";

const AlgorithmList = props => {
    const [categoryList, setCategoryList] = useState([]);
    const {isLoading, error, sendRequest} = useFetch();

    useEffect(() => {
        fetchCategories();
    }, []);

    const fetchCategories = () => {
        const applyResponse = (response) => {
            setCategoryList(response);
        }

        sendRequest({
            url: 'https://julian-laux.de:8080/algos/all/AlgorithmCateogories',
            method: 'GET'
        }, applyResponse);
    }

    const convertedAlgorithmList =
        <ul className={classes.list}>
            {categoryList.map(elem =>
                <CardMenue key={elem.id} dropdownElements={
                    elem.algorithmUrls.map(algo => <AlgorithmInformation url={algo} key={algo} changeDisplayedContent={props.changeDisplayedContent} />)
                }>{elem.name}</CardMenue>)}
        </ul>;

    return <React.Fragment>
        {!isLoading && !error && <div className={classes.card}>{convertedAlgorithmList}</div>}
        {isLoading && <div>Loading...</div>}
        {!isLoading && <div>{error}</div>}
    </React.Fragment>
}

export default AlgorithmList;