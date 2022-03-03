import './App.css';
import Navbar from "./Navbar";
import Canvas from "./Canvas";

function App() {
    var nodes = [{x:40, y:40}, {x:100, y:500}];
  return (
    <div className="App">
        <Navbar/>
        <Canvas nodes={nodes}/>
        <h1>Test</h1>
    </div>
  );
}

export default App;
