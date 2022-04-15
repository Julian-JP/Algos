import './App.css';
import Navbar from "./components/control/Navbar";
import GraphVisualisation from "./components/Visualisation/GraphVisualisation";
import React from "react";

function App() {

  return (
    <div className="App">
        <Navbar/>
        <GraphVisualisation/>
    </div>
  );
}

export default App;
