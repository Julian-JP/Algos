import GraphVisualisation from "../Visualisation/GraphVisualisation";
import React, {useState} from "react";
import AlgorithmList from "../control/AlgorithmList/AlgorithmList";

const MainContent = () => {

    const [displayedContent, setDisplayedContent] = useState('algorithmsList');


    return <React.Fragment>
        {displayedContent === 'algorithms' && <GraphVisualisation/>}
        {displayedContent === 'algorithmsList' && <AlgorithmList />}
    </React.Fragment>
}

export default MainContent;