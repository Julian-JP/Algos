import Navbar from "./components/UI/Navbar";
import MainContent from "./components/UI/MainContent";
import React from "react";

const Algorithms = () => {
    return (
        <div className="App">
            <Navbar/>
            <main className="maincontent">
                <MainContent/>
            </main>
        </div>
    );
}

export default Algorithms;