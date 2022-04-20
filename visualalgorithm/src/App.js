import './App.css';
import Navbar from "./components/UI/Navbar";
import React from "react";
import MainContent from "./components/UI/MainContent";

function App() {

    return (
        <div className="App">
            <Navbar/>
            <main className="maincontent">
                <MainContent/>
            </main>
        </div>
    );
}

export default App;
