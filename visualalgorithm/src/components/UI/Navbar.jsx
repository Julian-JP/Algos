import React from 'react';
import classes from './Navbar.module.css'

const Navbar = () => {
    return (
        <nav className={classes.topnavbar}>
            {/* <a href= "visualalgorithm/src/components/UI/Navbar#home">Home</a> */}
            <div className={classes.algo}>
                <a href="algorithms">Algorithms</a>
            </div>
            {/* <a href= "/aboutme">About me</a> */}
        </nav>
    );
};

export default Navbar;
