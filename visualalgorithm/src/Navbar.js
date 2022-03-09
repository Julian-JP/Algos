import React from 'react';
import './Navbar.css'

const Navbar = () => {
    return (
        <nav className={"topnavbar"}>
            <a href= "#home">Home</a>
            <a href="algorithms" className="active">Algorithms</a>
            <a href= "#about">About me</a>
        </nav>
    );
};

export default Navbar;
