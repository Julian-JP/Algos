import './App.css';
import React from "react";
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Algorithms from './Algorithms.jsx';
import AboutMe from "./AboutMe.jsx";
import ErrorPage from "./ErrorPage.jsx";

function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/algorithms" element={<Algorithms />} />
                <Route path="/aboutme" element={<AboutMe />} />
                <Route path="*" element={<ErrorPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;