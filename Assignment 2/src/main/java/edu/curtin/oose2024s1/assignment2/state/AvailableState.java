package edu.curtin.oose2024s1.assignment2.state;

import java.util.logging.Level;
import java.util.logging.Logger;

// ***** Available state ***** //
public class AvailableState implements State 
{
    private static final Logger logger = Logger.getLogger(AvailableState.class.getName());

    // ***** Handle available state ***** //
    @Override
    public void applyStateBehaviour(String email) 
    {
        System.out.println("\nBikes are available for purchasing");
        logger.log(Level.INFO, () -> "Bike state set to 'Available for purchasing\n");
    }
}