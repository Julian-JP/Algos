import Navbar from "./components/UI/Navbar";
import MainContent from "./components/UI/MainContent";
import React from "react";

const ALGORITHM_STYLES = {
    backgroundColor: "#a3a3a3",
    height: "100vh"
}

const Algorithms = () => {
    return (
        <div className="App" style={ALGORITHM_STYLES}>
            <Navbar/>
            <main className="maincontent">
                <MainContent/>
            </main>
        </div>
    );
}

export default Algorithms;