import './App.css';
import React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Algorithms from './Algorithms';
import AboutMe from "./AboutMe";
import ErrorPage from "./ErrorPage";

function App() {

    return (
        <Router>
            <Routes>
                <Route path="/algorithms" element={<Algorithms />} />
                <Route path="/aboutme" element={<AboutMe />} />
                <Route path="*" element={<ErrorPage />} />
            </Routes>
        </Router>
    );
}

export default App;
