import Navbar from "./components/UI/Navbar.jsx";
import MainContent from "./components/UI/MainContent.jsx";
import React from "react";

const ALGORITHM_STYLES = {
    height: "100%",
    backgroundColor: "#0e0e0e"
}

const Algorithms = () => {
    return (
        <div className="App" style={ALGORITHM_STYLES}>
            <Navbar/>
            <MainContent/>
        </div>
    );
}

export default Algorithms;