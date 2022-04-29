import GraphVisualisation from "../Visualisation/GraphVisualisation";
import React, {useState} from "react";
import AlgorithmList from "../control/AlgorithmList/AlgorithmList";

const MainContent = () => {

    const [displayedContent, setDisplayedContent] = useState({displayed: 'algorithmlist'});

    return <React.Fragment>
        {displayedContent.displayed === 'graph' &&
            <GraphVisualisation changeDisplayedContent={setDisplayedContent} url={displayedContent.url} displayedType={displayedContent.graphType}/>}
        {displayedContent.displayed === 'algorithmlist' && <AlgorithmList changeDisplayedContent={setDisplayedContent}/>}
    </React.Fragment>
}

export default MainContent;