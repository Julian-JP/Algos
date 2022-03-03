import React from 'react';
import './Navbar.css'

const Navbar = () => {
    return (
        <div className={"topnavbar"}>
            <a href= "#home">Home</a>
            <a href="algorithms" className="active">Algorithms</a>
            <a href= "#about">About me</a>
        </div>
    );
};

export default Navbar;
