import './App.css';
import Navbar from "./Navbar";
import Visualisation from "./Visualisation";
import React from "react";

function App() {

  return (
    <div className="App">
        <Navbar/>
        <Visualisation/>
        <h1 className={"test"}>App</h1>
    </div>
  );
}

export default App;
