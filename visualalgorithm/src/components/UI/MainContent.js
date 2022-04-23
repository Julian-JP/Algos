import GraphVisualisation from "../Visualisation/GraphVisualisation";
import React, {useState} from "react";
import AlgorithmList from "../control/AlgorithmList/AlgorithmList";

const MainContent = () => {

    const [displayedContent, setDisplayedContent] = useState({type: 'algorithmlist'});

    return <React.Fragment>
        {displayedContent.type === 'graphAlgorithm' &&
            <GraphVisualisation changeDisplayedContent={setDisplayedContent} url={displayedContent.url}/>}
        {displayedContent.type === 'algorithmlist' && <AlgorithmList changeDisplayedContent={setDisplayedContent}/>}
    </React.Fragment>
}

export default MainContent;