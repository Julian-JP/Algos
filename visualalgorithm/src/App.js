import './App.css';
import Navbar from "./Navbar";
import Visualisation from "./Visualisation";

function App() {

    var nodes = [{x:40, y:40}, {x:100, y:500}];

  return (
    <div className="App">
        <Navbar/>
        <Visualisation nodes={nodes}/>
        <h1 className={"test"}>App</h1>
    </div>
  );
}

export default App;
