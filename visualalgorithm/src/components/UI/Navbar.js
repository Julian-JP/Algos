import React from 'react';
import classes from './Navbar.module.css'

const Navbar = () => {
    return (
        <nav className={classes.topnavbar}>
            <a href= "visualalgorithm/src/components/UI/Navbar#home">Home</a>
            <a href="algorithms" className={classes.active}>Algorithms</a>
            <a href= "visualalgorithm/src/components/UI/Navbar#about">About me</a>
        </nav>
    );
};

export default Navbar;
