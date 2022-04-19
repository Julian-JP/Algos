import React from 'react';
import classes from './Navbar.module.css'

const Navbar = () => {
    return (
        <nav className={classes.topnavbar}>
            <a href= "visualalgorithm/src/components/control/Navbar#home">Home</a>
            <a href="algorithms" className={classes.active}>Algorithms</a>
            <a href= "visualalgorithm/src/components/control/Navbar#about">About me</a>
        </nav>
    );
};

export default Navbar;
