import React from 'react';
import './Navbar.css'

const Navbar = () => {
    return (
        <nav className={"topnavbar"}>
            <a href= "visualalgorithm/src/components/control/Navbar#home">Home</a>
            <a href="algorithms" className="active">Algorithms</a>
            <a href= "visualalgorithm/src/components/control/Navbar#about">About me</a>
        </nav>
    );
};

export default Navbar;
