import GraphVisualisation from "../Visualisation/GraphVisualisation.jsx";
import React, {useState} from "react";
import AlgorithmList from "../AlgorithmList/AlgorithmList.jsx";
import classes from "./MainConctent.module.css";

const MainContent = () => {

    const [displayedContent, setDisplayedContent] = useState({displayed: 'algorithmlist'});

    return <main className={classes.mainContent}>
        {displayedContent.displayed === 'graph' &&
            <GraphVisualisation changeDisplayedContent={setDisplayedContent} url={displayedContent.url} displayedType={displayedContent.graphType}/>}
        {displayedContent.displayed === 'algorithmlist' && <AlgorithmList changeDisplayedContent={setDisplayedContent}/>}
    </main>
}

export default MainContent;