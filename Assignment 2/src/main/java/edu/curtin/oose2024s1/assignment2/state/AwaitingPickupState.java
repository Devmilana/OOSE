package edu.curtin.oose2024s1.assignment2.state;

import java.util.logging.Level;
import java.util.logging.Logger;

// ***** Awating pick-up state ***** //
public class AwaitingPickupState implements State 
{
    private static final Logger logger = Logger.getLogger(AwaitingPickupState.class.getName());

    // ***** Handle awaiting pick-up state ***** //
    @Override
    public void applyStateBehaviour(String email) 
    {
        System.out.println("Bike for " + email + " is awaiting pickup");
        logger.log(Level.INFO, () -> "Bike for " + email + " is awaiting pickup\n");
    }
}